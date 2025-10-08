<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
     
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态，初始为付款;0: 待付款, 1: 待发货, 2: 待收货, 3: 售后中, 4: 已完成" clearable>
          <el-option
            v-for="dict in dict.type.order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流水号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单流水号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      
      
      
    
     
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      
      
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:orders:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ordersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="最终金额" align="center" prop="finalAmount" />
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="订单流水号" align="center" prop="orderNumber" />
      <el-table-column label="支付方式" align="center" prop="payType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_type" :value="scope.row.payType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单商品总数" align="center" prop="productNums" />
      <el-table-column label="支付时间" align="center" prop="payTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getDetails(scope.row)"
          >详情 </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:orders:remove']"
          >删除</el-button> -->
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

    
    <el-dialog :title="title" :visible.sync="open" width="1400px" append-to-body destroy-on-close="true">
      <Details :orderId="orderId"></Details>
    </el-dialog>

  </div>
</template>

<script>
import { listOrders, getOrders, delOrders, addOrders, updateOrders } from "@/api/system/ccairbag/orders";
import Details from '@/views/system/ccairbag/details/index.vue';

export default {
  name: "Orders",
  dicts: ['order_status','pay_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      orderId: null,
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
      // 订单表格数据
      ordersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        totalAmount: null,
        reduceAmount: null,
        finalAmount: null,
        orderStatus: null,
        orderNumber: null,
        payType: null,
        remarks: null,
        dvyType: null,
        dvyId: null,
        dvyFlowId: null,
        freightAmount: null,
        addrId: null,
        productNums: null,
        payTime: null,
        dvyTime: null,
        finallyTime: null,
        cancelTime: null,
        deleteStatus: null,
        refundSts: null,
        closeType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "下单用户的 ID不能为空", trigger: "blur" }
        ],
        totalAmount: [
          { required: true, message: "订单总金额不能为空", trigger: "blur" }
        ],
        finalAmount: [
          { required: true, message: "订单最终金额，扣除折扣后的金额不能为空", trigger: "blur" }
        ],
        orderNumber: [
          { required: true, message: "订单流水号不能为空", trigger: "blur" }
        ],
        payType: [
          { required: true, message: "支付方式不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "订单信息创建时间，默认当前时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "订单信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  components: {
    Details
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOrders(this.queryParams).then(response => {
        this.ordersList = response.rows;
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
        orderId: null,
        userId: null,
        totalAmount: null,
        reduceAmount: null,
        finalAmount: null,
        orderStatus: null,
        orderNumber: null,
        payType: null,
        remarks: null,
        dvyType: null,
        dvyId: null,
        dvyFlowId: null,
        freightAmount: null,
        addrId: null,
        productNums: null,
        payTime: null,
        dvyTime: null,
        finallyTime: null,
        cancelTime: null,
        deleteStatus: null,
        refundSts: null,
        closeType: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 订单详情 */
    getDetails(row) {
      
      console.log("xxxx");
      console.log(row.orderId);
      this.orderId = row.orderId;
      this.title = "订单详情";
      this.open = true;
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrders(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrders(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrders(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/orders/export', {
        ...this.queryParams
      }, `orders_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
