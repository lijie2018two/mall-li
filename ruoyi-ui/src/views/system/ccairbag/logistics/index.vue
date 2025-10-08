<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品id" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入商品id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流服务id" prop="serviceId">
        <el-input
          v-model="queryParams.serviceId"
          placeholder="请输入物流服务id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="该物流服务的预计送达时间" prop="estimatedDeliveryDays">
        <el-input
          v-model="queryParams.estimatedDeliveryDays"
          placeholder="请输入该物流服务的预计送达时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="deleted">
        <el-input
          v-model="queryParams.deleted"
          placeholder="请输入是否删除"
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
          v-hasPermi="['system/ccairbag:logistics:add']"
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
          v-hasPermi="['system/ccairbag:logistics:edit']"
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
          v-hasPermi="['system/ccairbag:logistics:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:logistics:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="logisticsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="商品id" align="center" prop="productId" />
      <el-table-column label="物流服务id" align="center" prop="serviceId" />
      <el-table-column label="该物流服务对于此商品的运费计算规则" align="center" prop="shippingFeeCalculation" />
      <el-table-column label="该物流服务的预计送达时间" align="center" prop="estimatedDeliveryDays" />
      <el-table-column label="记录不同物流类型的额外限制或规则" align="center" prop="shippingTypeRules" />
      <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:logistics:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:logistics:remove']"
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

    <!-- 添加或修改物流商品关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品id" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入商品id" />
        </el-form-item>
        <el-form-item label="物流服务id" prop="serviceId">
          <el-input v-model="form.serviceId" placeholder="请输入物流服务id" />
        </el-form-item>
        <el-form-item label="该物流服务对于此商品的运费计算规则" prop="shippingFeeCalculation">
          <el-input v-model="form.shippingFeeCalculation" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="该物流服务的预计送达时间" prop="estimatedDeliveryDays">
          <el-input v-model="form.estimatedDeliveryDays" placeholder="请输入该物流服务的预计送达时间" />
        </el-form-item>
        <el-form-item label="记录不同物流类型的额外限制或规则" prop="shippingTypeRules">
          <el-input v-model="form.shippingTypeRules" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否删除" prop="deleted">
          <el-input v-model="form.deleted" placeholder="请输入是否删除" />
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
import { listLogistics, getLogistics, delLogistics, addLogistics, updateLogistics } from "@/api/system/ccairbag/logistics";

export default {
  name: "Logistics",
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
      // 物流商品关联表格数据
      logisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        serviceId: null,
        shippingFeeCalculation: null,
        estimatedDeliveryDays: null,
        shippingTypeRules: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "商品id不能为空", trigger: "blur" }
        ],
        serviceId: [
          { required: true, message: "物流服务id不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "订单详情信息创建时间，默认当前时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "订单详情信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物流商品关联列表 */
    getList() {
      this.loading = true;
      listLogistics(this.queryParams).then(response => {
        this.logisticsList = response.rows;
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
        id: null,
        productId: null,
        serviceId: null,
        shippingFeeCalculation: null,
        estimatedDeliveryDays: null,
        shippingTypeRules: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deleted: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物流商品关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLogistics(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物流商品关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLogistics(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLogistics(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除物流商品关联编号为"' + ids + '"的数据项？').then(function() {
        return delLogistics(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/logistics/export', {
        ...this.queryParams
      }, `logistics_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
