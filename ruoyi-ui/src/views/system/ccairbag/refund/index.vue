<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
      
    </el-form>

    

    <el-table v-loading="loading" :data="refundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="refundId" />
      <el-table-column label="支付方式" align="center" prop="payType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_type" :value="scope.row.payType"/>
        </template>
      </el-table-column>
      <el-table-column label="退款金额" align="center" prop="refundAmount" />
      <el-table-column label="处理状态" align="center" prop="refundSts">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.refund_sts" :value="scope.row.refundSts"/>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="申请原因" align="center" prop="buyerMsg" />
      <el-table-column label="介入原因" align="center" prop="buyerMsgExt" />
      <el-table-column label="介入图片" align="center" prop="photoFilesExt" width="100">
                <template slot-scope="scope">
                  <image-preview :src="scope.row.photoFilesExt" :width="50" :height="50"/>
                </template>
      </el-table-column>

     
      
     
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleBuyerProblem(scope.row)"
            v-if="scope.row.refundSts === 8"
          >买家问题(状态退回)</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleSellerProblem(scope.row)"
            v-if="scope.row.refundSts === 8"
          >卖家问题(退款)</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  
    
  </div>
</template>

<script>
import { listRefund, getRefund, delRefund, addRefund, updateRefund, handleSellerProblem, handleBuyerProblem } from "@/api/system/ccairbag/refund";

export default {
  name: "Refund",
  dicts: ['pay_type','refund_sts'],

  props: {
    orderItemId: {
      type: [String, Number],
      required: false,
    }
  },
  watch: {
    orderItemId: {
      handler(newOrderItemId, oldOrderItemId) {
        console.log("监听orderItemId变化:", newOrderItemId);
        if (newOrderItemId) {
          this.queryParams.orderItemId = newOrderItemId;
          this.getList();
        } 
      }
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单退款表格数据
      refundList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shopId: null,
        orderId: null,
        orderNumber: null,
        orderAmount: null,
        orderItemId: null,
        refundSn: null,
        flowTradeNo: null,
        outRefundNo: null,
        payType: null,
        payTypeName: null,
        userId: null,
        goodsNum: null,
        refundAmount: null,
        applyType: null,
        refundSts: null,
        returnMoneySts: null,
        applyTime: null,
        handelTime: null,
        refundTime: null,
        photoFiles: null,
        buyerMsg: null,
        sellerMsg: null,
        expressName: null,
        shipTime: null,
        receiveTime: null,
        receiveMessage: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shopId: [
          { required: true, message: "店铺ID不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "订单ID不能为空", trigger: "blur" }
        ],
        orderNumber: [
          { required: true, message: "订单流水号不能为空", trigger: "blur" }
        ],
        orderAmount: [
          { required: true, message: "订单总金额不能为空", trigger: "blur" }
        ],
        orderItemId: [
          { required: true, message: "订单项ID 全部退款是0不能为空", trigger: "blur" }
        ],
        refundSn: [
          { required: true, message: "退款编号不能为空", trigger: "blur" }
        ],
        flowTradeNo: [
          { required: true, message: "订单支付流水号不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "买家ID不能为空", trigger: "blur" }
        ],
        refundAmount: [
          { required: true, message: "退款金额不能为空", trigger: "blur" }
        ],
        applyType: [
          { required: true, message: "申请类型:1,仅退款,2退款退货不能为空", trigger: "change" }
        ],
        refundSts: [
          { required: true, message: "处理状态:1为待审核,2为同意,3为不同意不能为空", trigger: "blur" }
        ],
        returnMoneySts: [
          { required: true, message: "处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败不能为空", trigger: "blur" }
        ],
        applyTime: [
          { required: true, message: "申请时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    // 在组件创建时检查orderItemId是否存在
    if (this.orderItemId) {
      console.log("组件创建时设置orderItemId参数:", this.orderItemId);
      this.queryParams.orderItemId = this.orderItemId;
    }
    this.getList();
  },
  methods: {
    /** 查询订单退款列表 */
    getList() {
      this.loading = true;
      listRefund(this.queryParams).then(response => {
        this.refundList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        refundId: null,
        shopId: null,
        orderId: null,
        orderNumber: null,
        orderAmount: null,
        orderItemId: null,
        refundSn: null,
        flowTradeNo: null,
        outRefundNo: null,
        payType: null,
        payTypeName: null,
        userId: null,
        goodsNum: null,
        refundAmount: null,
        applyType: null,
        refundSts: null,
        returnMoneySts: null,
        applyTime: null,
        handelTime: null,
        refundTime: null,
        photoFiles: null,
        buyerMsg: null,
        sellerMsg: null,
        expressName: null,
        expressNo: null,
        shipTime: null,
        receiveTime: null,
        receiveMessage: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.refundId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单退款";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const refundId = row.refundId || this.ids
      getRefund(refundId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单退款";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.refundId != null) {
            updateRefund(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRefund(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const refundIds = row.refundId || this.ids;
      this.$modal.confirm('是否确认删除订单退款编号为"' + refundIds + '"的数据项？').then(function() {
        return delRefund(refundIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 买家问题按钮操作 */
    handleBuyerProblem(row) {
      const refundId = row.refundId;
      this.$modal.confirm('是否确认处理买家问题？').then(() => {
        // 调用处理买家问题的API接口
        handleBuyerProblem(refundId).then(response => {
          this.$modal.msgSuccess("买家问题已处理");
          this.getList(); // 刷新列表
        }).catch(() => {
          this.$modal.msgError("处理失败，请稍后重试");
        });
      }).catch(() => {});
    },
    /** 卖家问题按钮操作 */
    handleSellerProblem(row) {
      const refundId = row.refundId;
      this.$modal.confirm('是否确认处理卖家问题？').then(() => {
        // 调用处理卖家问题的API接口
        handleSellerProblem(refundId).then(response => {
          this.$modal.msgSuccess("卖家问题已处理");
          this.getList(); // 刷新列表
        }).catch(() => {
          this.$modal.msgError("处理失败，请稍后重试");
        });
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/refund/export', {
        ...this.queryParams
      }, `refund_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
