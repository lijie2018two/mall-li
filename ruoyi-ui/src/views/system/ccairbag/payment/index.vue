<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="支付单号" prop="payNo">
        <el-input
          v-model="queryParams.payNo"
          placeholder="请输入支付单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部订单流水号" prop="bizPayNo">
        <el-input
          v-model="queryParams.bizPayNo"
          placeholder="请输入外部订单流水号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="order表中的订单号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入order表中的订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付方式名称" prop="payTypeName">
        <el-input
          v-model="queryParams.payTypeName"
          placeholder="请输入支付方式名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付金额" prop="payAmount">
        <el-input
          v-model="queryParams.payAmount"
          placeholder="请输入支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否清算 0:否 1:是" prop="isClearing">
        <el-input
          v-model="queryParams.isClearing"
          placeholder="请输入是否清算 0:否 1:是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="清算时间" prop="clearingTime">
        <el-date-picker clearable
          v-model="queryParams.clearingTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择清算时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system/ccairbag:payment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system/ccairbag:payment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system/ccairbag:payment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:payment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="支付结算单据ID" align="center" prop="paymentId" />
      <el-table-column label="支付单号" align="center" prop="payNo" />
      <el-table-column label="外部订单流水号" align="center" prop="bizPayNo" />
      <el-table-column label="order表中的订单号" align="center" prop="orderNumber" />
      <el-table-column label="支付方式 1 微信支付 2 支付宝" align="center" prop="payType" />
      <el-table-column label="支付方式名称" align="center" prop="payTypeName" />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <el-table-column label="是否清算 0:否 1:是" align="center" prop="isClearing" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="清算时间" align="center" prop="clearingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.clearingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="支付状态" align="center" prop="payStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:payment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:payment:remove']"
          >删除</el-button>
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

    <!-- 添加或修改订单支付对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="支付单号" prop="payNo">
          <el-input v-model="form.payNo" placeholder="请输入支付单号" />
        </el-form-item>
        <el-form-item label="外部订单流水号" prop="bizPayNo">
          <el-input v-model="form.bizPayNo" placeholder="请输入外部订单流水号" />
        </el-form-item>
        <el-form-item label="order表中的订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入order表中的订单号" />
        </el-form-item>
        <el-form-item label="支付方式名称" prop="payTypeName">
          <el-input v-model="form.payTypeName" placeholder="请输入支付方式名称" />
        </el-form-item>
        <el-form-item label="支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="是否清算 0:否 1:是" prop="isClearing">
          <el-input v-model="form.isClearing" placeholder="请输入是否清算 0:否 1:是" />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="清算时间" prop="clearingTime">
          <el-date-picker clearable
            v-model="form.clearingTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择清算时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPayment, getPayment, delPayment, addPayment, updatePayment } from "@/api/system/ccairbag/payment";

export default {
  name: "Payment",
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
      // 订单支付表格数据
      paymentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payNo: null,
        bizPayNo: null,
        orderNumber: null,
        payType: null,
        payTypeName: null,
        payAmount: null,
        isClearing: null,
        userId: null,
        clearingTime: null,
        version: null,
        payStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单支付列表 */
    getList() {
      this.loading = true;
      listPayment(this.queryParams).then(response => {
        this.paymentList = response.rows;
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
        paymentId: null,
        payNo: null,
        bizPayNo: null,
        orderNumber: null,
        payType: null,
        payTypeName: null,
        payAmount: null,
        isClearing: null,
        userId: null,
        createTime: null,
        clearingTime: null,
        version: null,
        payStatus: null
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
      this.ids = selection.map(item => item.paymentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单支付";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paymentId = row.paymentId || this.ids
      getPayment(paymentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单支付";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paymentId != null) {
            updatePayment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPayment(this.form).then(response => {
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
      const paymentIds = row.paymentId || this.ids;
      this.$modal.confirm('是否确认删除订单支付编号为"' + paymentIds + '"的数据项？').then(function() {
        return delPayment(paymentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/payment/export', {
        ...this.queryParams
      }, `payment_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
