<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联的商品 ID，该 SKU 所属商品" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入关联的商品 ID，该 SKU 所属商品"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU 名称;如颜色、尺寸等组合" prop="skuName">
        <el-input
          v-model="queryParams.skuName"
          placeholder="请输入SKU 名称;如颜色、尺寸等组合"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原价;原价" prop="oriPrice">
        <el-input
          v-model="queryParams.oriPrice"
          placeholder="请输入原价;原价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU 价格，精确到小数点后两位" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入SKU 价格，精确到小数点后两位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU 库存数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入SKU 库存数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称;商品名称" prop="prodName">
        <el-input
          v-model="queryParams.prodName"
          placeholder="请输入商品名称;商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重量" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入重量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="体积" prop="volume">
        <el-input
          v-model="queryParams.volume"
          placeholder="请输入体积"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除;1：删除  0：默认" prop="deleted">
        <el-input
          v-model="queryParams.deleted"
          placeholder="请输入是否删除;1：删除  0：默认"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="乐观锁版本号，用于并发更新控制，初始为 0" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入乐观锁版本号，用于并发更新控制，初始为 0"
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
          v-hasPermi="['system/ccairbag:skus:add']"
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
          v-hasPermi="['system/ccairbag:skus:edit']"
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
          v-hasPermi="['system/ccairbag:skus:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system/ccairbag:skus:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="skusList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="SKU 唯一标识，自增主键" align="center" prop="skuId" />
      <el-table-column label="关联的商品 ID，该 SKU 所属商品" align="center" prop="productId" />
      <el-table-column label="SKU 名称;如颜色、尺寸等组合" align="center" prop="skuName" />
      <el-table-column label="原价;原价" align="center" prop="oriPrice" />
      <el-table-column label="SKU 价格，精确到小数点后两位" align="center" prop="price" />
      <el-table-column label="SKU 库存数量" align="center" prop="quantity" />
      <el-table-column label="商品名称;商品名称" align="center" prop="prodName" />
      <el-table-column label="sku图片;sku图片" align="center" prop="pic" />
      <el-table-column label="销售属性组合字符串 格式是p1:v1;p2:v2;销售属性组合字符串 格式是p1:v1;p2:v2" align="center" prop="properties" />
      <el-table-column label="重量" align="center" prop="weight" />
      <el-table-column label="体积" align="center" prop="volume" />
      <el-table-column label="状态;0 禁用 1启用" align="center" prop="status" />
      <el-table-column label="是否删除;1：删除  0：默认" align="center" prop="deleted" />
      <el-table-column label="乐观锁版本号，用于并发更新控制，初始为 0" align="center" prop="version" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:skus:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system/ccairbag:skus:remove']"
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

    <!-- 添加或修改sku对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联的商品 ID，该 SKU 所属商品" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入关联的商品 ID，该 SKU 所属商品" />
        </el-form-item>
        <el-form-item label="SKU 名称;如颜色、尺寸等组合" prop="skuName">
          <el-input v-model="form.skuName" placeholder="请输入SKU 名称;如颜色、尺寸等组合" />
        </el-form-item>
        <el-form-item label="原价;原价" prop="oriPrice">
          <el-input v-model="form.oriPrice" placeholder="请输入原价;原价" />
        </el-form-item>
        <el-form-item label="SKU 价格，精确到小数点后两位" prop="price">
          <el-input v-model="form.price" placeholder="请输入SKU 价格，精确到小数点后两位" />
        </el-form-item>
        <el-form-item label="SKU 库存数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入SKU 库存数量" />
        </el-form-item>
        <el-form-item label="商品名称;商品名称" prop="prodName">
          <el-input v-model="form.prodName" placeholder="请输入商品名称;商品名称" />
        </el-form-item>
        <el-form-item label="sku图片;sku图片" prop="pic">
          <el-input v-model="form.pic" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="销售属性组合字符串 格式是p1:v1;p2:v2;销售属性组合字符串 格式是p1:v1;p2:v2" prop="properties">
          <el-input v-model="form.properties" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量" />
        </el-form-item>
        <el-form-item label="体积" prop="volume">
          <el-input v-model="form.volume" placeholder="请输入体积" />
        </el-form-item>
        <el-form-item label="是否删除;1：删除  0：默认" prop="deleted">
          <el-input v-model="form.deleted" placeholder="请输入是否删除;1：删除  0：默认" />
        </el-form-item>
        <el-form-item label="乐观锁版本号，用于并发更新控制，初始为 0" prop="version">
          <el-input v-model="form.version" placeholder="请输入乐观锁版本号，用于并发更新控制，初始为 0" />
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
import { listSkus, getSkus, delSkus, addSkus, updateSkus } from "@/api/system/ccairbag/skus";

export default {
  name: "Skus",
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
      // sku表格数据
      skusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        skuName: null,
        oriPrice: null,
        price: null,
        quantity: null,
        prodName: null,
        pic: null,
        properties: null,
        weight: null,
        volume: null,
        status: null,
        deleted: null,
        version: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "关联的商品 ID，该 SKU 所属商品不能为空", trigger: "blur" }
        ],
        skuName: [
          { required: true, message: "SKU 名称;如颜色、尺寸等组合不能为空", trigger: "blur" }
        ],
        oriPrice: [
          { required: true, message: "原价;原价不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "SKU 价格，精确到小数点后两位不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "SKU 库存数量不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "SKU 信息创建时间，默认当前时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "SKU 信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询sku列表 */
    getList() {
      this.loading = true;
      listSkus(this.queryParams).then(response => {
        this.skusList = response.rows;
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
        skuId: null,
        productId: null,
        skuName: null,
        oriPrice: null,
        price: null,
        quantity: null,
        prodName: null,
        pic: null,
        properties: null,
        weight: null,
        volume: null,
        status: null,
        deleted: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        version: null
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
      this.ids = selection.map(item => item.skuId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加sku";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const skuId = row.skuId || this.ids
      getSkus(skuId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改sku";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.skuId != null) {
            updateSkus(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSkus(this.form).then(response => {
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
      const skuIds = row.skuId || this.ids;
      this.$modal.confirm('是否确认删除sku编号为"' + skuIds + '"的数据项？').then(function() {
        return delSkus(skuIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ccairbag/skus/export', {
        ...this.queryParams
      }, `skus_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
