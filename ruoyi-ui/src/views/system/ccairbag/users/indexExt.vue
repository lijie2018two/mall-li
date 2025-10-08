<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
      
     
      
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

   

    <el-table v-loading="loading" :data="usersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户名字" align="center" prop="firstName" />
      
      <el-table-column label="头像" align="center" prop="headimgurl" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="用户邮箱" align="center" prop="email" />
      <el-table-column label="资料审核状态" align="center" prop="qualificationStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qualification_status" :value="scope.row.qualificationStatus"/>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:users:edit']"
          >审核</el-button>
          
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

    <!-- 添加或修改用户管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
       
       
        <el-form-item label="头像" prop="headimgurl">
          <el-input v-model="form.headimgurl" placeholder="请输入头像" :disabled="true" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" :disabled="true"/>
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入用户邮箱" :disabled="true" />
        </el-form-item>
       
        <el-form-item label="申请状态" prop="qualificationStatus">
          <el-radio-group v-model="form.qualificationStatus">
            <el-radio
              v-for="dict in dict.type.qualification_status"
              v-if="parseInt(dict.value) !== 0"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
import { listUsers, getUsersExt, delUsers, addUsers, updateUsers } from "@/api/system/ccairbag/users";

export default {
  name: "Users",
  dicts: ['user_type','qualification_status'],
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
      // 用户管理表格数据
      usersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        firstName: null,
        lastName: null,
        companyName: null,
        companyAddress: null,
        headimgurl: null,
        username: null,
        email: null,
        passwordHash: null,
        deleted: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
      
       
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.qualificationStatus = 1
      listUsers(this.queryParams).then(response => {
        this.usersList = response.rows;
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
        userId: null,
        userType: null,
        firstName: null,
        lastName: null,
        companyName: null,
        companyAddress: null,
        headimgurl: null,
        username: null,
        email: null,
        passwordHash: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        deleted: null,
        businessQualificationImages: null,
        qualificationStatus: null,
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getUsersExt(userId).then(response => {
        console.log("ssss"+response.data);
        if (response.data) {
          this.form = response.data.users;
          this.form.businessQualificationImages = response.data.shop.businessQualificationImages;
          this.open = true;
          this.title = "审核用户资质";
        }else{
          this.$modal.msgError("商店信息不存在");
          return;
        }
      
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateUsers(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } 
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除用户管理编号为"' + userIds + '"的数据项？').then(function() {
        return delUsers(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/users/export', {
        ...this.queryParams
      }, `users_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
