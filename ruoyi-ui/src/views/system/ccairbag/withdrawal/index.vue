<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="店铺id" prop="shopId">
        <el-input
          v-model="queryParams.shopId"
          placeholder="请输入店铺id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请时间" prop="applyTime">
        <el-date-picker clearable
          v-model="queryParams.applyTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择申请时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="抽佣金额" prop="drawMoney">
        <el-input
          v-model="queryParams.drawMoney"
          placeholder="请输入抽佣金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提现金额" prop="money">
        <el-input
          v-model="queryParams.money"
          placeholder="请输入提现金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行卡ext" prop="bankCardExt">
        <el-input
          v-model="queryParams.bankCardExt"
          placeholder="请输入银行卡ext"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行卡" prop="bankCard">
        <el-input
          v-model="queryParams.bankCard"
          placeholder="请输入银行卡"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际到账金额" prop="realMoney">
        <el-input
          v-model="queryParams.realMoney"
          placeholder="请输入实际到账金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="真实身份id" prop="idCard">
        <el-input
          v-model="queryParams.idCard"
          placeholder="请输入真实身份id"
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
          v-hasPermi="['ccairbag/system:withdrawal:add']"
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
          v-hasPermi="['ccairbag/system:withdrawal:edit']"
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
          v-hasPermi="['ccairbag/system:withdrawal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ccairbag/system:withdrawal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="withdrawalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="店铺id" align="center" prop="shopId" />
      <el-table-column label="店铺名" align="center" prop="shopName" />
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="抽佣金额" align="center" prop="drawMoney" />
      <el-table-column label="提现金额" align="center" prop="money" />
     
      <el-table-column label="实际到账金额" align="center" prop="realMoney" />
      <el-table-column label="手机号" align="center" prop="phone" />
    

      <el-table-column label="状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.withdrawal_status" :value="scope.row.status"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ccairbag/system:withdrawal:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ccairbag/system:withdrawal:remove']"
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

    <!-- 添加或修改提现记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店铺id" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="店铺名" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入店铺名" />
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime">
          <el-date-picker clearable
            v-model="form.applyTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择申请时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="抽佣金额" prop="drawMoney">
          <el-input v-model="form.drawMoney" placeholder="请输入抽佣金额" />
        </el-form-item>
        <el-form-item label="提现金额" prop="money">
          <el-input v-model="form.money" placeholder="请输入提现金额" />
        </el-form-item>
        <el-form-item label="银行卡ext" prop="bankCardExt">
          <el-input v-model="form.bankCardExt" placeholder="请输入银行卡ext" />
        </el-form-item>
        <el-form-item label="银行卡" prop="bankCard">
          <el-input v-model="form.bankCard" placeholder="请输入银行卡" />
        </el-form-item>
        <el-form-item label="实际到账金额" prop="realMoney">
          <el-input v-model="form.realMoney" placeholder="请输入实际到账金额" />
        </el-form-item>
        

        
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="item in dict.type.withdrawal_status"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
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
import { listWithdrawal, getWithdrawal, delWithdrawal, addWithdrawal, updateWithdrawal } from "@/api/system/ccairbag/withdrawal";

export default {
  name: "Withdrawal",
  dicts: ['withdrawal_status'],

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
      // 提现记录表格数据
      withdrawalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shopId: null,
        shopName: null,
        applyTime: null,
        drawMoney: null,
        money: null,
        bankCardExt: null,
        bankCard: null,
        realMoney: null,
        phone: null,
        idCard: null,
        status: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        
        money: [
          { required: true, message: "提现金额不能为空", trigger: "blur" }
        ]
       
       
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询提现记录列表 */
    getList() {
      this.loading = true;
      listWithdrawal(this.queryParams).then(response => {
        this.withdrawalList = response.rows;
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
        shopId: null,
        shopName: null,
        applyTime: null,
        drawMoney: null,
        money: null,
        bankCardExt: null,
        bankCard: null,
        realMoney: null,
        phone: null,
        idCard: null,
        status: null,
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
      this.title = "添加提现记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWithdrawal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改提现记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWithdrawal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWithdrawal(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除提现记录编号为"' + ids + '"的数据项？').then(function() {
        return delWithdrawal(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ccairbag/system/withdrawal/export', {
        ...this.queryParams
      }, `withdrawal_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
