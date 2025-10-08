<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="短信平台账号" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="请输入短信平台账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接受短信的手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入接受短信的手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="添加记录ip" prop="addIp">
        <el-input
          v-model="queryParams.addIp"
          placeholder="请输入添加记录ip"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="短信模板ID" prop="template">
        <el-input
          v-model="queryParams.template"
          placeholder="请输入短信模板ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态" prop="resultcode">
        <el-input
          v-model="queryParams.resultcode"
          placeholder="请输入状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送记录id" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入发送记录id"
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
          v-hasPermi="['system:record:add']"
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
          v-hasPermi="['system:record:edit']"
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
          v-hasPermi="['system:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:record:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="短信发送记录编号" align="center" prop="id" />
      <el-table-column label="短信平台账号" align="center" prop="uid" />
      <el-table-column label="接受短信的手机号" align="center" prop="phone" />
      <el-table-column label="短信内容" align="center" prop="content" />
      <el-table-column label="添加记录ip" align="center" prop="addIp" />
      <el-table-column label="短信模板ID" align="center" prop="template" />
      <el-table-column label="状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态" align="center" prop="resultcode" />
      <el-table-column label="发送记录id" align="center" prop="recordId" />
      <el-table-column label="短信平台返回信息" align="center" prop="memo" />
      <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:record:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:record:remove']"
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

    <!-- 添加或修改短信发送记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="短信平台账号" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入短信平台账号" />
        </el-form-item>
        <el-form-item label="接受短信的手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入接受短信的手机号" />
        </el-form-item>
        <el-form-item label="短信内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="添加记录ip" prop="addIp">
          <el-input v-model="form.addIp" placeholder="请输入添加记录ip" />
        </el-form-item>
        <el-form-item label="短信模板ID" prop="template">
          <el-input v-model="form.template" placeholder="请输入短信模板ID" />
        </el-form-item>
        <el-form-item label="状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态" prop="resultcode">
          <el-input v-model="form.resultcode" placeholder="请输入状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态" />
        </el-form-item>
        <el-form-item label="发送记录id" prop="recordId">
          <el-input v-model="form.recordId" placeholder="请输入发送记录id" />
        </el-form-item>
        <el-form-item label="短信平台返回信息" prop="memo">
          <el-input v-model="form.memo" type="textarea" placeholder="请输入内容" />
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
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/system/record";

export default {
  name: "Record",
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
      // 短信发送记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uid: null,
        phone: null,
        content: null,
        addIp: null,
        template: null,
        resultcode: null,
        recordId: null,
        memo: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        uid: [
          { required: true, message: "短信平台账号不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "接受短信的手机号不能为空", trigger: "blur" }
        ],
        deleted: [
          { required: true, message: "是否删除不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询短信发送记录列表 */
    getList() {
      this.loading = true;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        uid: null,
        phone: null,
        content: null,
        addIp: null,
        createTime: null,
        template: null,
        resultcode: null,
        recordId: null,
        memo: null,
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
      this.title = "添加短信发送记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短信发送记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除短信发送记录编号为"' + ids + '"的数据项？').then(function() {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
