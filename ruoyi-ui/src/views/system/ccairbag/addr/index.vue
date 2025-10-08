<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货人" prop="receiver">
        <el-input
          v-model="queryParams.receiver"
          placeholder="请输入收货人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国家名" prop="country">
        <el-input
          v-model="queryParams.country"
          placeholder="请输入国家名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="州 / 省 / 大区名称;州 / 省 / 大区名称" prop="stateProvince">
        <el-input
          v-model="queryParams.stateProvince"
          placeholder="请输入州 / 省 / 大区名称;州 / 省 / 大区名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="城市" prop="city">
        <el-input
          v-model="queryParams.city"
          placeholder="请输入城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区" prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="街道" prop="street">
        <el-input
          v-model="queryParams.street"
          placeholder="请输入街道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="门牌号" prop="houseNumber">
        <el-input
          v-model="queryParams.houseNumber"
          placeholder="请输入门牌号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮编" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入邮编"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否默认地址 1是" prop="commonAddr">
        <el-input
          v-model="queryParams.commonAddr"
          placeholder="请输入是否默认地址 1是"
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
          v-hasPermi="['system/ccairbag:addr:add']"
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
          v-hasPermi="['system/ccairbag:addr:edit']"
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
          v-hasPermi="['system/ccairbag:addr:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:addr:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addrList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="addrId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="收货人" align="center" prop="receiver" />
      <el-table-column label="国家名" align="center" prop="country" />
      <el-table-column label="州 / 省 / 大区名称;州 / 省 / 大区名称" align="center" prop="stateProvince" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="区" align="center" prop="area" />
      <el-table-column label="街道" align="center" prop="street" />
      <el-table-column label="门牌号" align="center" prop="houseNumber" />
      <el-table-column label="邮编" align="center" prop="postCode" />
      <el-table-column label="状态,1正常，0无效" align="center" prop="status" />
      <el-table-column label="是否默认地址 1是" align="center" prop="commonAddr" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:addr:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:addr:remove']"
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

    <!-- 添加或修改用户地址管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="收货人" prop="receiver">
          <el-input v-model="form.receiver" placeholder="请输入收货人" />
        </el-form-item>
        <el-form-item label="国家名" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家名" />
        </el-form-item>
        <el-form-item label="州 / 省 / 大区名称;州 / 省 / 大区名称" prop="stateProvince">
          <el-input v-model="form.stateProvince" placeholder="请输入州 / 省 / 大区名称;州 / 省 / 大区名称" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="区" prop="area">
          <el-input v-model="form.area" placeholder="请输入区" />
        </el-form-item>
        <el-form-item label="街道" prop="street">
          <el-input v-model="form.street" placeholder="请输入街道" />
        </el-form-item>
        <el-form-item label="门牌号" prop="houseNumber">
          <el-input v-model="form.houseNumber" placeholder="请输入门牌号" />
        </el-form-item>
        <el-form-item label="邮编" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="是否默认地址 1是" prop="commonAddr">
          <el-input v-model="form.commonAddr" placeholder="请输入是否默认地址 1是" />
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
import { listAddr, getAddr, delAddr, addAddr, updateAddr } from "@/api/system/ccairbag/addr";

export default {
  name: "Addr",
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
      // 用户地址管理表格数据
      addrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        receiver: null,
        country: null,
        stateProvince: null,
        city: null,
        area: null,
        street: null,
        houseNumber: null,
        postCode: null,
        status: null,
        commonAddr: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态,1正常，0无效不能为空", trigger: "change" }
        ],
        commonAddr: [
          { required: true, message: "是否默认地址 1是不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "建立时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户地址管理列表 */
    getList() {
      this.loading = true;
      listAddr(this.queryParams).then(response => {
        this.addrList = response.rows;
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
        addrId: null,
        userId: null,
        receiver: null,
        country: null,
        stateProvince: null,
        city: null,
        area: null,
        street: null,
        houseNumber: null,
        postCode: null,
        status: null,
        commonAddr: null,
        createTime: null,
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
      this.ids = selection.map(item => item.addrId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户地址管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const addrId = row.addrId || this.ids
      getAddr(addrId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户地址管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addrId != null) {
            updateAddr(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAddr(this.form).then(response => {
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
      const addrIds = row.addrId || this.ids;
      this.$modal.confirm('是否确认删除用户地址管理编号为"' + addrIds + '"的数据项？').then(function() {
        return delAddr(addrIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/addr/export', {
        ...this.queryParams
      }, `addr_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
