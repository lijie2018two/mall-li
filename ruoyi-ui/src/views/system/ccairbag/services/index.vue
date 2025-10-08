<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物流服务名称" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入物流服务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基本费用" prop="baseFee">
        <el-input
          v-model="queryParams.baseFee"
          placeholder="请输入基本费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流类型" prop="shippingType">
        <el-select v-model="queryParams.shippingType" placeholder="请选择物流类型" clearable>
          <el-option
            v-for="dict in dict.type.shipping_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system/ccairbag:services:add']"
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
          v-hasPermi="['system/ccairbag:services:edit']"
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
          v-hasPermi="['system/ccairbag:services:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:services:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="servicesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="serviceId" />
      <el-table-column label="物流服务名称" align="center" prop="serviceName" />
      <el-table-column label="提供商名称" align="center" prop="providerName" />
      <el-table-column label="详细描述" align="center" prop="description" />
      <el-table-column label="基本费用" align="center" prop="baseFee" />
      <el-table-column label="额外费用" align="center" prop="additionalFeeRules" />
      <el-table-column label="物流类型" align="center" prop="shippingType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.shipping_type" :value="scope.row.shippingType"/>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:services:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:services:remove']"
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

    <!-- 添加或修改物流服务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物流服务名称" prop="serviceName">
          <el-input v-model="form.serviceName" placeholder="请输入物流服务名称" />
        </el-form-item>
        <el-form-item label="提供商名称" prop="providerName">
          <el-input v-model="form.providerName" placeholder="请输入提供商名称" />
        </el-form-item>
        <el-form-item label="详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="基本费用" prop="baseFee">
          <el-input v-model="form.baseFee" placeholder="请输入基本费用" />
        </el-form-item>
        <el-form-item label="额外费用" prop="additionalFeeRules">
          <el-input v-model="form.additionalFeeRules" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="物流类型" prop="shippingType">
          <el-select v-model="form.shippingType" placeholder="请选择物流类型">
            <el-option
              v-for="dict in dict.type.shipping_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listServices, getServices, delServices, addServices, updateServices } from "@/api/system/ccairbag/services";

export default {
  name: "Services",
  dicts: ['shipping_type'],
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
      // 物流服务表格数据
      servicesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serviceName: null,
        baseFee: null,
        additionalFeeRules: null,
        shippingType: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serviceName: [
          { required: true, message: "物流服务名称不能为空", trigger: "blur" }
        ],
        providerName: [
          { required: true, message: "提供商名称不能为空", trigger: "blur" }
        ],
        shippingType: [
          { required: true, message: "物流类型不能为空", trigger: "change" }
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
    /** 查询物流服务列表 */
    getList() {
      this.loading = true;
      listServices(this.queryParams).then(response => {
        this.servicesList = response.rows;
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
        serviceId: null,
        serviceName: null,
        providerName: null,
        description: null,
        baseFee: null,
        additionalFeeRules: null,
        shippingType: null,
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
      this.ids = selection.map(item => item.serviceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物流服务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const serviceId = row.serviceId || this.ids
      getServices(serviceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物流服务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.serviceId != null) {
            updateServices(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServices(this.form).then(response => {
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
      const serviceIds = row.serviceId || this.ids;
      this.$modal.confirm('是否确认删除物流服务编号为"' + serviceIds + '"的数据项？').then(function() {
        return delServices(serviceIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/services/export', {
        ...this.queryParams
      }, `services_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
