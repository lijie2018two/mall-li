<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
      <el-form-item label="店铺名称" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
     
     
     
     
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

   

    <el-table v-loading="loading" :data="shopsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="店铺logo" align="center" prop="shopLogo" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.shopLogo" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="店铺名称" align="center" prop="shopName" />
     
      
      <el-table-column label="店铺联系电话" align="center" prop="shopPhone" />
      <el-table-column label="店铺入驻申请状态" align="center" prop="applicationStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.process_status" :value="scope.row.applicationStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:shops:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:shops:remove']"
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

    <!-- 添加或修改店铺对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="店铺名称" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="店铺简介" prop="intro">
          <el-input v-model="form.intro" placeholder="请输入店铺简介" />
        </el-form-item>
        <el-form-item label="店铺公告" prop="shopNotice">
          <el-input v-model="form.shopNotice" placeholder="请输入店铺公告" />
        </el-form-item>
        <el-form-item label="是否删除" prop="deleted">
          <el-input v-model="form.deleted" placeholder="请输入是否删除" />
        </el-form-item>
        <el-form-item label="店铺logo" prop="shopLogo">
          <image-upload v-model="form.shopLogo"/>
        </el-form-item>
        <el-form-item label="店铺联系电话" prop="shopPhone">
          <el-input v-model="form.shopPhone" placeholder="请输入店铺联系电话" />
        </el-form-item>

        <el-form-item label="店铺入驻申请状态" prop="applicationStatus">
          <el-radio-group v-model="form.applicationStatus">
            <el-radio
              v-for="dict in dict.type.process_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item label="记录拒绝原因" prop="applicationReason">
          <el-input v-model="form.applicationReason" type="textarea" placeholder="请输入内容" />
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
import { listShops, getShops, delShops, addShops, updateShops } from "@/api/system/ccairbag/shops";

export default {
  name: "Shops",
  dicts: ['process_status'],
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
      // 店铺表格数据
      shopsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        shopName: null,
        intro: null,
        shopNotice: null,
        shopLogo: null,
        shopPhone: null,
        applicationStatus: null,
        applicationReason: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        shopName: [
          { required: true, message: "店铺名称不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "店铺信息创建时间，默认当前时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "店铺信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询店铺列表 */
    getList() {
      this.loading = true;
      this.queryParams.applicationStatus = 0
      listShops(this.queryParams).then(response => {
        this.shopsList = response.rows;
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
        shopId: null,
        userId: null,
        shopName: null,
        intro: null,
        shopNotice: null,
        deleted: null,
        shopLogo: null,
        shopPhone: null,
        applicationStatus: null,
        applicationReason: null,
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
      this.ids = selection.map(item => item.shopId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加店铺";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const shopId = row.shopId || this.ids
      getShops(shopId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.shopId != null) {
            updateShops(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShops(this.form).then(response => {
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
      const shopIds = row.shopId || this.ids;
      this.$modal.confirm('是否确认删除店铺编号为"' + shopIds + '"的数据项？').then(function() {
        return delShops(shopIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/shops/export', {
        ...this.queryParams
      }, `shops_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
