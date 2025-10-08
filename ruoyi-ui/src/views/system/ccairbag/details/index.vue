<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                  
        <el-form-item label="产品名称" prop="prodName">
          <el-input
              v-model="queryParams.prodName"
              placeholder="商品名称"
              clearable
              @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="物流单号" prop="dvyFlowNum">
          <el-input
              v-model="queryParams.dvyFlowNum"
              placeholder="请输入物流单号"
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
      
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="id" align="center" prop="detailId" />
              <el-table-column label="商品名称" align="center" prop="prodName" />
              <el-table-column label="商品主图" align="center" prop="pic" width="100">
                <template slot-scope="scope">
                  <image-preview :src="scope.row.pic" :width="50" :height="50"/>
                </template>
              </el-table-column>
              <el-table-column label="订单状态" align="center" prop="orderStatus">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/>
                </template>
              </el-table-column>
              <el-table-column label="数量" align="center" prop="quantity" />
              <el-table-column label="单价" align="center" prop="unitPrice" />

              
             
            
              
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleRefundDetails(scope.row)"
              v-hasPermi="['system/ccairbag:details:edit']"
          >退款详情</el-button>
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

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
          ref="upload"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改订单详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="formDialog" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="关联的订单 ID" prop="orderId">
                          <el-input v-model="form.orderId" placeholder="请输入关联的订单 ID" />
                        </el-form-item>
                        <el-form-item label="订单号" prop="orderNumber">
                          <el-input v-model="form.orderNumber" placeholder="请输入订单号" />
                        </el-form-item>
                        <el-form-item label="商品id" prop="prodId">
                          <el-input v-model="form.prodId" placeholder="请输入商品id" />
                        </el-form-item>
                        <el-form-item label="产品名称" prop="prodName">
                          <el-input v-model="form.prodName" placeholder="请输入产品名称" />
                        </el-form-item>
                        <el-form-item label="产品主图片路径" prop="pic">
                          <el-input v-model="form.pic" placeholder="请输入产品主图片路径" />
                        </el-form-item>
                        <el-form-item label="用户id" prop="userId">
                          <el-input v-model="form.userId" placeholder="请输入用户id" />
                        </el-form-item>
                        <el-form-item label="数量" prop="quantity">
                          <el-input v-model="form.quantity" placeholder="请输入数量" />
                        </el-form-item>
                        <el-form-item label="单价" prop="unitPrice">
                          <el-input v-model="form.unitPrice" placeholder="请输入单价" />
                        </el-form-item>
                        <el-form-item label="评论状态;评论状态： 0 未评价  1 已评价" prop="commSts">
                          <el-input v-model="form.commSts" placeholder="请输入评论状态;评论状态： 0 未评价  1 已评价" />
                        </el-form-item>
                        <el-form-item label="该 SKU 在订单中的小计金额" prop="subtotal">
                          <el-input v-model="form.subtotal" placeholder="请输入该 SKU 在订单中的小计金额" />
                        </el-form-item>
                        <el-form-item label="是否删除" prop="deleted">
                          <el-input v-model="form.deleted" placeholder="请输入是否删除" />
                        </el-form-item>
                        <el-form-item label="物流公司编码" prop="logisticsCode">
                          <el-input v-model="form.logisticsCode" placeholder="请输入物流公司编码" />
                        </el-form-item>
                        <el-form-item label="物流公司名称" prop="logisticsName">
                          <el-input v-model="form.logisticsName" placeholder="请输入物流公司名称" />
                        </el-form-item>
                        <el-form-item label="物流单号" prop="dvyFlowNum">
                          <el-input v-model="form.dvyFlowNum" placeholder="请输入物流单号" />
                        </el-form-item>
                        <el-form-item label="物流运费" prop="freightAmount">
                          <el-input v-model="form.freightAmount" placeholder="请输入物流运费" />
                        </el-form-item>
                        <el-form-item label="发货时间" prop="dvyTime">
                          <el-date-picker clearable
                                          v-model="form.dvyTime"
                                          type="date"
                                          value-format="yyyy-MM-dd"
                                          placeholder="请选择发货时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="完成时间" prop="finallyTime">
                          <el-date-picker clearable
                                          v-model="form.finallyTime"
                                          type="date"
                                          value-format="yyyy-MM-dd"
                                          placeholder="请选择完成时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="退货物流公司编码" prop="returnLogisticsCode">
                          <el-input v-model="form.returnLogisticsCode" placeholder="请输入退货物流公司编码" />
                        </el-form-item>
                        <el-form-item label="退货物流公司名称" prop="returnLogisticsName">
                          <el-input v-model="form.returnLogisticsName" placeholder="请输入退货物流公司名称" />
                        </el-form-item>
                        <el-form-item label="退货物流单号" prop="returnDvyFlowNum">
                          <el-input v-model="form.returnDvyFlowNum" placeholder="请输入退货物流单号" />
                        </el-form-item>
                        <el-form-item label="退货发货时间" prop="returnDvyTime">
                          <el-date-picker clearable
                                          v-model="form.returnDvyTime"
                                          type="date"
                                          value-format="yyyy-MM-dd"
                                          placeholder="请选择退货发货时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="退货完成时间" prop="returnFinallyTime">
                          <el-date-picker clearable
                                          v-model="form.returnFinallyTime"
                                          type="date"
                                          value-format="yyyy-MM-dd"
                                          placeholder="请选择退货完成时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="活动id" prop="activityId">
                          <el-input v-model="form.activityId" placeholder="请输入活动id" />
                        </el-form-item>
                        <el-form-item label="广告活动抽成金额" prop="commissionMoney">
                          <el-input v-model="form.commissionMoney" placeholder="请输入广告活动抽成金额" />
                        </el-form-item>
                        <el-form-item label="广告活动抽成比例" prop="commissionRate">
                          <el-input v-model="form.commissionRate" placeholder="请输入广告活动抽成比例" />
                        </el-form-item>
                        <el-form-item label="议价单价" prop="negotiablePrice">
                          <el-input v-model="form.negotiablePrice" placeholder="请输入议价单价" />
                        </el-form-item>
                        <el-form-item label=" 销售税比例" prop="scale">
                          <el-input v-model="form.scale" placeholder="请输入 销售税比例" />
                        </el-form-item>
                        <el-form-item label="交易费用比例" prop="dealScale">
                          <el-input v-model="form.dealScale" placeholder="请输入交易费用比例" />
                        </el-form-item>
                        <el-form-item label="销售税金额" prop="scaleMoney">
                          <el-input v-model="form.scaleMoney" placeholder="请输入销售税金额" />
                        </el-form-item>
                        <el-form-item label="交易费用金额" prop="dealMoney">
                          <el-input v-model="form.dealMoney" placeholder="请输入交易费用金额" />
                        </el-form-item>
                        <el-form-item label="收益金额" prop="incomeMoney">
                          <el-input v-model="form.incomeMoney" placeholder="请输入收益金额" />
                        </el-form-item>
                        <el-form-item label="买家删除标记" prop="userDelete">
                          <el-input v-model="form.userDelete" placeholder="请输入买家删除标记" />
                        </el-form-item>
                        <el-form-item label="卖家删除标记" prop="shopDelete">
                          <el-input v-model="form.shopDelete" placeholder="请输入卖家删除标记" />
                        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="RefundTitle" :visible.sync="Refundopen" width="1200px" append-to-body destroy-on-close="true">
      <Refund :orderItemId="orderItemId"></Refund>
    </el-dialog>
  </div>
</template>

<script>
  import { listDetails, getDetails, delDetails, addDetails, updateDetails } from "@/api/system/ccairbag/details";
  import {getToken} from "@/utils/auth";
  import Refund from '@/views/system/ccairbag/refund/index.vue';

  export default {
    name: "Details",
    dicts: ['order_status','pay_type'],
    props: {
      orderId: {
        type: [String, Number],
        required: false,
      }
    },
    watch: {
      orderId: {
        handler(newOrderId, oldOrderId) {
          console.log("监听orderId变化:", newOrderId);
          if (newOrderId) {
            this.queryParams.orderId = newOrderId;
            this.getList();
          } 
        }
      }
    },
    components: {
      Refund
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
        // 订单详情表格数据
              detailsList: [],
        // 弹出层标题
        title: "",
        orderItemId: null,
        // 是否显示弹出层
        open: false,
        Refundopen: false,
        RefundTitle: "",
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
              orderId: null,
              orderNumber: null,
              prodId: null,
              prodName: null,
              pic: null,
              orderStatus: null,
              userId: null,
              quantity: null,
              unitPrice: null,
              commSts: null,
              subtotal: null,
              deleted: null,
              logisticsCode: null,
              logisticsName: null,
              dvyFlowNum: null,
              freightAmount: null,
              dvyTime: null,
              finallyTime: null,
              returnLogisticsCode: null,
              returnLogisticsName: null,
              returnDvyFlowNum: null,
              returnDvyTime: null,
              returnFinallyTime: null,
              refundType: null,
              activityId: null,
              commissionMoney: null,
              commissionRate: null,
              negotiablePrice: null,
              scale: null,
              dealScale: null,
              scaleMoney: null,
              dealMoney: null,
              incomeMoney: null,
              userDelete: null,
              shopDelete: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
                        orderId: [
                    { required: true, message: "关联的订单 ID不能为空", trigger: "blur" }
                  ],
                        quantity: [
                    { required: true, message: "数量不能为空", trigger: "blur" }
                  ],
                        unitPrice: [
                    { required: true, message: "单价不能为空", trigger: "blur" }
                  ],
                        commSts: [
                    { required: true, message: "评论状态;评论状态： 0 未评价  1 已评价不能为空", trigger: "blur" }
                  ],
                        subtotal: [
                    { required: true, message: "该 SKU 在订单中的小计金额不能为空", trigger: "blur" }
                  ],
                        createTime: [
                    { required: true, message: "订单详情信息创建时间，默认当前时间不能为空", trigger: "blur" }
                  ],
                        updateTime: [
                    { required: true, message: "订单详情信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
                  ],
        },
        // 导入参数
        upload: {
          // 是否显示弹出层（导入）
          open: false,
          // 弹出层标题（导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 设置上传的请求头部
          headers: { Authorization: "Bearer " + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/ccairbag/details/importData"
        }
      };
    },
    created() {
      console.log("Details组件创建，接收到的orderId:", this.orderId);
      this.getList();
    },
    methods: {
      /** 查询订单详情列表 */
      getList() {
        this.loading = true;
        this.queryParams.orderId = this.orderId;
        listDetails(this.queryParams).then(response => {
          this.detailsList = response.rows;
          console.log("查询订单详情列表:", this.detailsList);
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
                        detailId: null,
                        orderId: null,
                        orderNumber: null,
                        prodId: null,
                        prodName: null,
                        pic: null,
                        orderStatus: null,
                        userId: null,
                        quantity: null,
                        unitPrice: null,
                        commSts: null,
                        subtotal: null,
                        createBy: null,
                        createTime: null,
                        updateBy: null,
                        updateTime: null,
                        deleted: null,
                        logisticsCode: null,
                        logisticsName: null,
                        dvyFlowNum: null,
                        freightAmount: null,
                        dvyTime: null,
                        finallyTime: null,
                        returnLogisticsCode: null,
                        returnLogisticsName: null,
                        returnDvyFlowNum: null,
                        returnDvyTime: null,
                        returnFinallyTime: null,
                        refundType: null,
                        activityId: null,
                        commissionMoney: null,
                        commissionRate: null,
                        negotiablePrice: null,
                        scale: null,
                        dealScale: null,
                        scaleMoney: null,
                        dealMoney: null,
                        incomeMoney: null,
                        userDelete: null,
                        shopDelete: null
        };
        this.resetForm("formDialog");
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
        this.ids = selection.map(item => item.detailId)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加订单详情";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const detailId = row.detailId || this.ids
        getDetails(detailId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改订单详情";
        });
      },
      
      /** 退款详情按钮操作 */
      handleRefundDetails(row) {
        console.log("打开退款详情，row:", row);
        console.log("打开退款详情，detailId:", row.detailId);
        
        // 先设置标题
        this.RefundTitle = "退款详情";
        
        // 然后设置orderItemId
        console.log("设置orderItemId前的值:", this.orderItemId);
        this.orderItemId = row.detailId;
        console.log("设置orderItemId后的值:", this.orderItemId);
        
        // 最后打开对话框
        this.Refundopen = true;
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["formDialog"].validate(valid => {
          if (valid) {
            if (this.form.detailId != null) {
              updateDetails(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDetails(this.form).then(response => {
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
        const detailIds = row.detailId || this.ids;
        this.$modal.confirm('是否确认删除订单详情编号为"' + detailIds + '"的数据项？').then(function() {
          return delDetails(detailIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
  /** 导出按钮操作 */
  handleExport() {
    this.download('system/ccairbag/details/export', {
      ...this.queryParams
    }, `details_${new Date().getTime()}.xlsx`)
  },
  /** 下载模板操作 */
  importTemplate() {
    this.download('system/ccairbag/details/importTemplate', {
    }, `template_${new Date().getTime()}.xlsx`)
  },
  /** 导入按钮操作 */
  handleImport() {
    this.upload.title = "订单详情导入";
    this.upload.open = true;
  },
  // 文件上传中处理
  handleFileUploadProgress(event, file, fileList) {
    this.upload.isUploading = true;
  },
  // 文件上传成功处理
  handleFileSuccess(response, file, fileList) {
    this.upload.open = false;
    this.upload.isUploading = false;
    this.$refs.upload.clearFiles();
    this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
    this.getList();
  },
  // 提交上传文件
  submitFileForm() {
    this.$refs.upload.submit();
  }
  }
  };
</script>
