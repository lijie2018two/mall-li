<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="被收藏人名称" prop="intelligentName">
        <el-input
          v-model="queryParams.intelligentName"
          placeholder="请输入被点赞人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="enjoyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        label="达人头像"
        align="center"
        prop="intelligentHeadimgurl"
      >
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.intelligentHeadimgurl"
            :width="50"
            :height="50"
          />
        </template>
      </el-table-column>
      <el-table-column label="达人名称" align="center" prop="intelligentName" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  listEnjoy,
  getEnjoy,
  delEnjoy,
  addEnjoy,
  updateEnjoy,
} from "@/api/system/enjoy";

export default {
  name: "Enjoy",
  props: {
    uId: {
      type: String,
      default: "",
    },
  },

  watch: {
    uId: {
      handler(newId, oldId) {
        console.log("newId:" + newId);
        this.uId = newId;
      },
      immediate: true,
      deep: true,
    },
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
      // 点赞表格数据
      enjoyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        intelligentId: null,
        intelligentName: null,
        uId: null,
        uName: null,
        status: null,
        deleted: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        intelligentId: [
          { required: true, message: "达人id不能为空", trigger: "blur" },
        ],
        intelligentName: [
          { required: true, message: "被点赞人名称不能为空", trigger: "blur" },
        ],
        uId: [{ required: true, message: "客户id不能为空", trigger: "blur" }],
        deleted: [
          { required: true, message: "是否删除不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询点赞列表 */
    getList() {
      this.loading = true;
      this.queryParams.status = 1;
      if (this.uId != null) {
        this.$set(this.queryParams, "uId", this.uId);
      }
      listEnjoy(this.queryParams).then((response) => {
        this.enjoyList = response.rows;
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
        intelligentId: null,
        intelligentName: null,
        uId: null,
        uName: null,
        status: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        deleted: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加点赞";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getEnjoy(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改点赞";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateEnjoy(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEnjoy(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除点赞编号为"' + ids + '"的数据项？')
        .then(function () {
          return delEnjoy(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/enjoy/export",
        {
          ...this.queryParams,
        },
        `enjoy_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
