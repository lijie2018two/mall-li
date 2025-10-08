<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                  <el-form-item label="商品名称" prop="productName">
                    <el-input
                        v-model="queryParams.productName"
                        placeholder="请输入商品名称，不能为空"
                        clearable
                        @keyup.enter.native="handleQuery"
                    />
                  </el-form-item>
                  
                 
                  <el-form-item label="状态 " prop="productidStatus">
                    <el-select v-model="queryParams.productidStatus" placeholder="请选择上下架状态 0 下架 1 上架" clearable>
                      <el-option
                          v-for="dict in dict.type.product_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  
                 
                
                 
                 
                  
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system/ccairbag:products:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system/ccairbag:products:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="商品唯一标识，自增主键" align="center" prop="productId" />
              <el-table-column label="商品名称，不能为空" align="center" prop="productName" />
              <el-table-column label="关联的店铺 ID，该商品所属店铺" align="center" prop="shopId" />
              <el-table-column label="商品简要描述" align="center" prop="brief" />
              <el-table-column label="关联的商品分类 ID，该商品所属分类" align="center" prop="categoryId" />
              <el-table-column label="商品详细描述" align="center" prop="description" />
              <el-table-column label="上下架状态 0 下架 1 上架" align="center" prop="productidStatus">
                <template slot-scope="scope">
                      <dict-tag :options="dict.type.product_status_ext" :value="scope.row.productidStatus"/>
                </template>
              </el-table-column>
              <el-table-column label="商品状态;1正常  0待审核 2拒绝" align="center" prop="status">
                <template slot-scope="scope">
                      <dict-tag :options="dict.type.product_status" :value="scope.row.status"/>
                </template>
              </el-table-column>
              <el-table-column label="原价" align="center" prop="oriPrice" />
              <el-table-column label="商品价格，精确到小数点后两位" align="center" prop="price" />
              <el-table-column label="商品主图" align="center" prop="pic" width="100">
                <template slot-scope="scope">
                  <image-preview :src="scope.row.pic" :width="50" :height="50"/>
                </template>
              </el-table-column>
              <el-table-column label="商品图片;商品图片，以,分割" align="center" prop="imgs" width="100">
                <template slot-scope="scope">
                  <image-preview :src="scope.row.imgs" :width="50" :height="50"/>
                </template>
              </el-table-column>
              <el-table-column label="总库存" align="center" prop="totalQuantity" />
              <el-table-column label="商品销量，默认为 0" align="center" prop="salesVolume" />
              <el-table-column label="配送方式;配送方式json见TransportModeVO" align="center" prop="deliveryMode" />
              <el-table-column label="" align="center" prop="version" />
              <el-table-column label="运费模板id" align="center" prop="deliveryTemplateId" />
              <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system/ccairbag:products:edit']"
          >修改</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system/ccairbag:products:remove']"
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="formDialog" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="商品名称，不能为空" prop="productName">
                          <el-input v-model="form.productName" placeholder="请输入商品名称，不能为空" />
                        </el-form-item>
                        <el-form-item label="关联的店铺 ID，该商品所属店铺" prop="shopId">
                          <el-input v-model="form.shopId" placeholder="请输入关联的店铺 ID，该商品所属店铺" />
                        </el-form-item>
                        <el-form-item label="商品简要描述" prop="brief">
                          <el-input v-model="form.brief" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="关联的商品分类 ID，该商品所属分类" prop="categoryId">
                          <el-input v-model="form.categoryId" placeholder="请输入关联的商品分类 ID，该商品所属分类" />
                        </el-form-item>
                        <el-form-item label="商品详细描述" prop="description">
                          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="上下架状态 0 下架 1 上架" prop="productidStatus">
                          <el-radio-group v-model="form.productidStatus">
                            <el-radio
                                v-for="dict in dict.type.product_status_ext"
                                :key="dict.value"
                                :label="parseInt(dict.value)"
                            >{{dict.label}}</el-radio>
                          </el-radio-group>
                        </el-form-item>
                        <el-form-item label="商品状态;1正常  0待审核 2拒绝" prop="status">
                          <el-radio-group v-model="form.status">
                            <el-radio
                                v-for="dict in dict.type.product_status"
                                :key="dict.value"
                                :label="parseInt(dict.value)"
                            >{{dict.label}}</el-radio>
                          </el-radio-group>
                        </el-form-item>
                        <el-form-item label="原价" prop="oriPrice">
                          <el-input v-model="form.oriPrice" placeholder="请输入原价" />
                        </el-form-item>
                        <el-form-item label="商品价格，精确到小数点后两位" prop="price">
                          <el-input v-model="form.price" placeholder="请输入商品价格，精确到小数点后两位" />
                        </el-form-item>
                        <el-form-item label="商品主图" prop="pic">
                          <image-upload v-model="form.pic"/>
                        </el-form-item>
                        <el-form-item label="商品图片;商品图片，以,分割" prop="imgs">
                          <image-upload v-model="form.imgs"/>
                        </el-form-item>
                        <el-form-item label="总库存" prop="totalQuantity">
                          <el-input v-model="form.totalQuantity" placeholder="请输入总库存" />
                        </el-form-item>
                        <el-form-item label="商品销量，默认为 0" prop="salesVolume">
                          <el-input v-model="form.salesVolume" placeholder="请输入商品销量，默认为 0" />
                        </el-form-item>
                        <el-form-item label="配送方式;配送方式json见TransportModeVO" prop="deliveryMode">
                          <el-input v-model="form.deliveryMode" placeholder="请输入配送方式;配送方式json见TransportModeVO" />
                        </el-form-item>
                        <el-form-item label="" prop="version">
                          <el-input v-model="form.version" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="运费模板id" prop="deliveryTemplateId">
                          <el-input v-model="form.deliveryTemplateId" type="textarea" placeholder="请输入内容" />
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
  import { listProducts, getProducts, delProducts, addProducts, updateProducts } from "@/api/system/ccairbag/products";
  import {getToken} from "@/utils/auth";

  export default {
    name: "Products",
        dicts: ['product_status_ext', 'product_status'],
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
        // 商品表格数据
              productsList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
                        productName: null,
                        shopId: null,
                        brief: null,
                        categoryId: null,
                        description: null,
                        productidStatus: null,
                        status: null,
                        oriPrice: null,
                        price: null,
                        pic: null,
                        imgs: null,
                        totalQuantity: null,
                        salesVolume: null,
                        deliveryMode: null,
                        version: null,
                        deliveryTemplateId: null,
                        deleted: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
                        productName: [
                    { required: true, message: "商品名称，不能为空不能为空", trigger: "blur" }
                  ],
                        shopId: [
                    { required: true, message: "关联的店铺 ID，该商品所属店铺不能为空", trigger: "blur" }
                  ],
                        price: [
                    { required: true, message: "商品价格，精确到小数点后两位不能为空", trigger: "blur" }
                  ],
                        createTime: [
                    { required: true, message: "商品信息创建时间，默认当前时间不能为空", trigger: "blur" }
                  ],
                        updateTime: [
                    { required: true, message: "商品信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
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
          url: process.env.VUE_APP_BASE_API + "/system/ccairbag/products/importData"
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询商品列表 */
      getList() {
        this.loading = true;
        listProducts(this.queryParams).then(response => {
          this.productsList = response.rows;
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
                        productId: null,
                        productName: null,
                        shopId: null,
                        brief: null,
                        categoryId: null,
                        description: null,
                        productidStatus: null,
                        status: null,
                        oriPrice: null,
                        price: null,
                        pic: null,
                        imgs: null,
                        totalQuantity: null,
                        salesVolume: null,
                        deliveryMode: null,
                        version: null,
                        deliveryTemplateId: null,
                        createBy: null,
                        createTime: null,
                        updateBy: null,
                        updateTime: null,
                        deleted: null
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
        this.ids = selection.map(item => item.productId)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加商品";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const productId = row.productId || this.ids
        getProducts(productId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改商品";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["formDialog"].validate(valid => {
          if (valid) {
            if (this.form.productId != null) {
              updateProducts(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addProducts(this.form).then(response => {
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
        const productIds = row.productId || this.ids;
        this.$modal.confirm('是否确认删除商品编号为"' + productIds + '"的数据项？').then(function() {
          return delProducts(productIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
  /** 导出按钮操作 */
  handleExport() {
    this.download('system/ccairbag/products/export', {
      ...this.queryParams
    }, `products_${new Date().getTime()}.xlsx`)
  },
  /** 下载模板操作 */
  importTemplate() {
    this.download('system/ccairbag/products/importTemplate', {
    }, `template_${new Date().getTime()}.xlsx`)
  },
  /** 导入按钮操作 */
  handleImport() {
    this.upload.title = "商品导入";
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
