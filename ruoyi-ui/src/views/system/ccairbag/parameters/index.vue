<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="参数名" prop="parameterName">
        <el-input
          v-model="queryParams.parameterName"
          placeholder="请输入参数名"
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
          v-hasPermi="['system/ccairbag:parameters:add']"
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
          v-hasPermi="['system/ccairbag:parameters:edit']"
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
          v-hasPermi="['system/ccairbag:parameters:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:parameters:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="parametersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参数名" align="center" prop="parameterName" />
    
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:parameters:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:parameters:remove']"
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

    <!-- 添加或修改参数表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="参数名" prop="parameterName">
              <el-input v-model="form.parameterName" placeholder="请输入参数名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否必填" prop="isMust">
              <el-radio-group v-model="form.isMust">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="品类id" prop="categoryId">
              <el-input v-model="form.categoryId" placeholder="请输入品类id" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="逻辑删除标志，0 表示未删除，1 表示已删除" prop="deleted">
              <el-input v-model="form.deleted" placeholder="请输入逻辑删除标志，0 表示未删除，1 表示已删除" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listParameters, getParameters, delParameters, addParameters, updateParameters } from "@/api/system/ccairbag/parameters";

export default {
  name: "Parameters",
  props: {
    categoryId: {
      type: Number,
      required: true
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
      // 参数表表格数据
      parametersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parameterName: null,
        groupId: null,
        categoryId: null,
        deleted: null,
      },
      // 表单参数
      form: {
        parameterId: null,
        parameterName: null,
        isMust: 0, // 默认值为否
        categoryId: this.categoryId, // 赋值品类id
        deleted: null,
        updateTime: null,
        updateBy: null,
        createTime: null,
        createBy: null
      },
      // 表单校验
      rules: {
        parameterName: [
          { required: true, message: "参数名不能为空", trigger: "blur" }
        ],
        isMust: [
          { required: true, message: "是否必填项不能为空", trigger: "blur" }
        ]
        

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询参数表列表 */
    getList() {
      this.loading = true;
      this.queryParams.categoryId = this.categoryId
      listParameters(this.queryParams).then(response => {
        this.parametersList = response.rows;
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
        parameterId: null,
        parameterName: null,
        isMust: 0, // 默认值为否
        categoryId: this.categoryId, // 赋值品类id
        deleted: null,
        updateTime: null,
        updateBy: null,
        createTime: null,
        createBy: null
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
      this.ids = selection.map(item => item.parameterId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加参数表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const parameterId = row.parameterId || this.ids
      getParameters(parameterId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改参数表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.parameterId != null) {
            updateParameters(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParameters(this.form).then(response => {
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
      const parameterIds = row.parameterId || this.ids;
      this.$modal.confirm('是否确认删除参数表编号为"' + parameterIds + '"的数据项？').then(function() {
        return delParameters(parameterIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/parameters/export', {
        ...this.queryParams
      }, `parameters_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
