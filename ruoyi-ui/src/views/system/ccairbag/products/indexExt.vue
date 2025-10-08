<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      

      <el-form-item label="商品状态" prop="productStatus">
        <el-select v-model="queryParams.productStatus" placeholder="请选择上下架状态 0 下架 1 上架" clearable>
          <el-option
            v-for="dict in dict.type.product_status_ext"
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

    

    <el-table v-loading="loading" :data="productsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="店铺名" align="center" prop="shopName" />

      <el-table-column label="邮寄类型" align="center" prop="deliveryFeeMode">

        <template slot-scope="scope">
          <dict-tag :options="dict.type.delivery_type" :value="scope.row.deliveryFeeMode"/>
        </template>
      </el-table-column>

      
      <el-table-column label="退换政策" align="center" prop="returnPolicy">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.return_policy" :value="scope.row.returnPolicy"/>
        </template>
      </el-table-column>

      <el-table-column label="所属分类" align="center" prop="fullCategoryName" />
      
      <el-table-column label="上下架状态" align="center" prop="productidStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_status_ext" :value="scope.row.productStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="商品状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="原价" align="center" prop="oriPrice" />
      <el-table-column label="商品价格" align="center" prop="price" />
      <el-table-column label="商品主图" align="center" prop="pic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.pic" :width="50" :height="50"/>
        </template>
      </el-table-column>
     
      <el-table-column label="库存" align="center" prop="totalQuantity" />
      <el-table-column label="商品销量" align="center" prop="salesVolume" />
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system/ccairbag:products:edit']"
          >详情</el-button>
          
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="商品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入商品名称，不能为空" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品简要描述" prop="brief">
              <el-input v-model="form.brief" type="textarea" placeholder="请输入内容" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属分类" prop="fullCategoryName">
              <el-input v-model="form.fullCategoryName" placeholder="所属分类" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="商品详细描述" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入内容" :disabled="true" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="总库存" prop="totalQuantity">
              <el-input v-model="form.totalQuantity" placeholder="请输入总库存" :disabled="true" />
            </el-form-item>
          </el-col>

         
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="商品状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.product_status"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          

          <el-col :span="12">
            <el-form-item label="原价" prop="oriPrice">
              <el-input v-model="form.oriPrice" placeholder="请输入原价" :disabled="true" />
            </el-form-item>
          </el-col>
          
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="售价" prop="price">
              <el-input v-model="form.price" placeholder="请输入商品价格，精确到小数点后两位" :disabled="true" />
            </el-form-item>
          </el-col>
          
        </el-row>
        <el-row :gutter="20">

          <el-col :span="12">
            <el-form-item label="商品主图" prop="pic">
              <image-upload v-model="form.pic" :disabled="true" />
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="商品图片;商品图片，以,分割" prop="imgs">
              <image-upload v-model="form.imgs" :disabled="true" />
            </el-form-item>
          </el-col>
          
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="商品销量" prop="salesVolume">
              <el-input v-model="form.salesVolume" placeholder="请输入商品销量，默认为 0" :disabled="true" />
            </el-form-item>
          </el-col>
         
          <el-col :span="12">
            
            <el-form-item label="邮寄类型" prop="deliveryFeeMode">
              <dict-tag :options="dict.type.delivery_type" :value="form.deliveryFeeMode"/>
            </el-form-item>
          



          </el-col>

       
        </el-row>

        <el-row :gutter="20">
          
         
          <el-col :span="12">
            <el-form-item label="退换政策" prop="returnPolicy">
              <dict-tag :options="dict.type.return_policy" :value="form.returnPolicy"/>
            </el-form-item>
          </el-col>
        </el-row>

        
        
        
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

export default {
  name: "Products",
  dicts: ['product_status_ext', 'product_status','delivery_type'],
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
        productStatus: null,
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
      this.queryParams.status = 0
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
        productStatus: null,
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
      this.$refs["form"].validate(valid => {
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
    }
  }
};
</script>
