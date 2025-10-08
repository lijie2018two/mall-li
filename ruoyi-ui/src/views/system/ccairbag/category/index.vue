<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
      <el-form-item label="类目名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入产品类目名称"
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
          v-hasPermi="['ccairbag:category:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="categoryList"
      row-key="categoryId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="产品类目名称" align="center" prop="categoryName" />
      <el-table-column label="英文名"" align="center" prop="categoryNameEn" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.user_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="seq" />
      <el-table-column label="记录时间" align="center" prop="recTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分类层级" align="center" prop="grade" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:category:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system/ccairbag:category:add']"
          >新增品类</el-button>
         
         
          <el-button
            type="text"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleViewParameters(scope.row)"
            v-hasPermi="['ccairbag:category:view']"
          >查看参数</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:category:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改商品类目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="父节点" prop="parentId">
              <treeselect v-model="form.parentId" :options="categoryOptions" :normalizer="normalizer" placeholder="请选择父节点" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类目名称" prop="categoryName">
              <el-input v-model="form.categoryName" placeholder="请输入产品类目名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="类目英文" prop="categoryNameEn">
              <el-input v-model="form.categoryNameEn" placeholder="请输入产品类目英文" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.user_status"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{dict.label}}</el-radio>
              </el-radio-group>
        </el-form-item>

           
          </el-col>
        </el-row>
     
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序" prop="seq">
              <el-input v-model="form.seq" placeholder="请输入排序" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
 
    
    <!-- 查看参数对话框 -->
    <el-dialog :title="'查看参数 - ' + currentCategoryName" :visible.sync="parametersDialogVisible" width="800px" append-to-body>
      <parameters-index ref="parametersIndex" :categoryId="currentCategoryId" />
    </el-dialog>
  </div>
</template>

<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/system/ccairbag/category";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import ParametersIndex from "@/views/system/ccairbag/parameters/index"; // 引入parameters组件

export default {
  name: "Category",
  dicts: ['user_status'],
  components: {
    Treeselect,
    ParametersIndex
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 商品类目表格数据
      categoryList: [],
      // 商品类目树选项
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      ParametersIndex:false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        parentId: null,
        categoryName: null,
        seq: null,
        status: null,
        recTime: null,
        grade: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "父节点不能为空", trigger: "blur" }
        ],
        categoryName: [
          { required: true, message: "产品类目名称不能为空", trigger: "blur" }
        ],
        seq: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
      
       
        grade: [
          { required: true, message: "分类层级不能为空", trigger: "blur" }
        ]
       
      },
      parametersDialogVisible: false,
      currentCategoryId: null,
      currentCategoryName: ''
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品类目列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = this.handleTree(response.data, "categoryId", "parentId");
        this.loading = false;
      });
    },
    /** 转换商品类目数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.categoryId,
        label: node.categoryName,
        children: node.children
      };
    },
	/** 查询商品类目下拉树结构 */
    getTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = [];
        const data = { categoryId: 0, categoryName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "categoryId", "parentId");
        this.categoryOptions.push(data);
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
        categoryId: null,
        parentId: null,
        categoryName: null,
        seq: null,
        status: null,
        recTime: null,
        grade: null,
        deleted: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.categoryId) {
        this.form.parentId = row.categoryId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加商品类目";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.categoryId;
      }
      getCategory(row.categoryId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品类目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.categoryId != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品类目编号为"' + row.categoryId + '"的数据项？').then(function() {
        return delCategory(row.categoryId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 新增分组按钮操作 */
    handleAddParameters() {
      this.open = true;
      this.title = "添加商品类目";
      this.$refs.parametersIndex.getList();
    },
    /** 查看参数按钮操作 */
    handleViewParameters(row) {
      this.currentCategoryId = row.categoryId;
      this.currentCategoryName = row.categoryName;
      this.parametersDialogVisible = true;
      this.$nextTick(() => {
        this.$refs.parametersIndex.getList();
      });
    }
  }
};
</script>
