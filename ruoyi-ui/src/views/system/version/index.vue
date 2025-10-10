<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                  <el-form-item label="版本名称" prop="versionName">
                    <el-input
                        v-model="queryParams.versionName"
                        placeholder="请输入版本名称"
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
            v-hasPermi="['system:version:add']"
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
            v-hasPermi="['system:version:edit']"
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
            v-hasPermi="['system:version:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:version:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system:version:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="主键ID" align="center" prop="id" />
              <el-table-column label="版本号" align="center" prop="versionCode" />
              <el-table-column label="版本名称" align="center" prop="versionName" />
              <el-table-column label="APK文件存储路径" align="center" prop="apkFilePath" />
              <el-table-column label="是否强制升级" align="center" prop="isForceUpdate">
                <template slot-scope="scope">
                      <dict-tag :options="dict.type.is_force_update" :value="scope.row.isForceUpdate"/>
                </template>
              </el-table-column>
              <el-table-column label="更新内容描述" align="center" prop="updateContent" />
              <el-table-column label="下载次数" align="center" prop="downloadCount" />
              <el-table-column label="状态" align="center" prop="status">
                <template slot-scope="scope">
                      <dict-tag :options="dict.type.app_status" :value="scope.row.status"/>
                </template>
              </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:version:edit']"
          >修改</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:version:remove']"
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

    <!-- 添加或修改App版本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="formDialog" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="版本名称" prop="versionName">
                          <el-input v-model="form.versionName" placeholder="请输入版本名称" />
                        </el-form-item>
                        <el-form-item label="APK文件存储路径" prop="apkFilePath">
                          <file-upload v-model="form.apkFilePath"/>
                        </el-form-item>
                        <el-form-item label="是否强制升级" prop="isForceUpdate">
                          <el-select v-model="form.isForceUpdate" placeholder="请选择是否强制升级">
                            <el-option
                                v-for="dict in dict.type.is_force_update"
                                :key="dict.value"
                                :label="dict.label"
                                :value="parseInt(dict.value)"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="更新内容描述">
                          <editor v-model="form.updateContent" :min-height="192"/>
                        </el-form-item>
                       
                        <el-form-item label="状态" prop="status">
                          <el-select v-model="form.status" placeholder="请选择状态">
                            <el-option
                                v-for="dict in dict.type.app_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="parseInt(dict.value)"
                            ></el-option>
                          </el-select>
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
  import { listVersion, getVersion, delVersion, addVersion, updateVersion } from "@/api/system/version";
  import {getToken} from "@/utils/auth";

  export default {
    name: "Version",
        dicts: ['app_status', 'is_force_update'],
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
        // App版本信息表格数据
              versionList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
                        versionName: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
                        versionName: [
                    { required: true, message: "版本名称不能为空", trigger: "blur" }
                  ],
                        apkFilePath: [
                    { required: true, message: "APK文件存储路径不能为空", trigger: "blur" }
                  ],
                        isForceUpdate: [
                    { required: true, message: "是否强制升级不能为空", trigger: "change" }
                  ],
                        status: [
                    { required: true, message: "状态不能为空", trigger: "change" }
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
          url: process.env.VUE_APP_BASE_API + "/system/version/importData"
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询App版本信息列表 */
      getList() {
        this.loading = true;
        listVersion(this.queryParams).then(response => {
          this.versionList = response.rows;
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
                        versionCode: null,
                        versionName: null,
                        apkFilePath: null,
                        fileSize: null,
                        isForceUpdate: null,
                        updateContent: null,
                        downloadCount: null,
                        status: null,
                        createTime: null,
                        updateTime: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加App版本信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getVersion(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改App版本信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["formDialog"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateVersion(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addVersion(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除App版本信息编号为"' + ids + '"的数据项？').then(function() {
          return delVersion(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
  /** 导出按钮操作 */
  handleExport() {
    this.download('system/version/export', {
      ...this.queryParams
    }, `version_${new Date().getTime()}.xlsx`)
  },
  /** 下载模板操作 */
  importTemplate() {
    this.download('system/version/importTemplate', {
    }, `template_${new Date().getTime()}.xlsx`)
  },
  /** 导入按钮操作 */
  handleImport() {
    this.upload.title = "App版本信息导入";
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
