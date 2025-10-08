<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="录轮播图点击后跳转的链接" prop="linkUrl">
        <el-input
          v-model="queryParams.linkUrl"
          placeholder="请输入录轮播图点击后跳转的链接"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="简短说明文字" prop="caption">
        <el-input
          v-model="queryParams.caption"
          placeholder="请输入简短说明文字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="轮播图的展示顺序" prop="displayOrder">
        <el-input
          v-model="queryParams.displayOrder"
          placeholder="请输入轮播图的展示顺序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.carousel_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="对应的商品 ID" prop="associatedProductId">
        <el-input
          v-model="queryParams.associatedProductId"
          placeholder="请输入对应的商品 ID"
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
          v-hasPermi="['system:carousel:add']"
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
          v-hasPermi="['system:carousel:edit']"
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
          v-hasPermi="['system:carousel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:carousel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carouselList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="carouselId" />
      <el-table-column label="图片地址" align="center" prop="imageUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="轮播图按钮位置" align="center" prop="type" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carouse_type" :value="scope.row.type"/>
        </template>
      </el-table-column>

     
      <el-table-column label="轮播图类型" align="center" prop="carouselType" >
        
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carousel_type" :value="scope.row.carouselType"/>
        </template>
      </el-table-column>

      <el-table-column label="轮播图点击后跳转的链接" align="center" prop="linkUrl" />
      <el-table-column label="app轮播图点击后跳转的链接" align="center" prop="appLinkUrl" />

      <el-table-column label="简短说明文字" align="center" prop="caption" />
      <el-table-column label="轮播图的展示顺序" align="center" prop="displayOrder" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carousel_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="对应的商品 ID" align="center" prop="associatedProductId" />
      <el-table-column label="是否删除" align="center" prop="deleted" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:carousel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:carousel:remove']"
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

    <!-- 添加或修改轮播图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="图片地址" prop="imageUrl">
          <image-upload v-model="form.imageUrl" limit="1"/>
        </el-form-item>
        <el-form-item label="录轮播图点击后跳转的链接" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入录轮播图点击后跳转的链接" />
        </el-form-item>

        <el-form-item label="APP轮轮播图点击后跳转的链接" prop="appLinkUrl">
          <el-input v-model="form.appLinkUrl" placeholder="APP轮轮播图点击后跳转的链接" />
        </el-form-item>
        
        <el-form-item label="简短说明文字" prop="caption">
          <el-input v-model="form.caption" placeholder="请输入简短说明文字" />
        </el-form-item>
        <el-form-item label="轮播图的展示顺序" prop="displayOrder">
          <el-input v-model="form.displayOrder" placeholder="请输入轮播图的展示顺序" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.carousel_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="按钮位置" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in dict.type.carouse_type"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="轮播图类型" prop="carouselType">
          <el-radio-group v-model="form.carouselType">
            <el-radio
              v-for="dict in dict.type.carousel_type"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="对应的商品 ID" prop="associatedProductId">
          <el-input v-model="form.associatedProductId" placeholder="请输入对应的商品 ID" />
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
import { listCarousel, getCarousel, delCarousel, addCarousel, updateCarousel } from "@/api/system/ccairbag/carousel";

export default {
  name: "Carousel",
  dicts: ['carousel_status','carouse_type','carousel_type'],
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
      // 轮播图表格数据
      carouselList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        imageUrl: null,
        linkUrl: null,
        caption: null,
        displayOrder: null,
        status: null,
        associatedProductId: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        imageUrl: [
          { required: true, message: "图片地址不能为空", trigger: "blur" }
        ],
        displayOrder: [
          { required: true, message: "轮播图的展示顺序不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "用户信息创建时间，默认当前时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "用户信息最后一次更新时间，自动更新为当前时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询轮播图列表 */
    getList() {
      this.loading = true;
      listCarousel(this.queryParams).then(response => {
        this.carouselList = response.rows;
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
        carouselId: null,
        imageUrl: null,
        linkUrl: null,
        appLinkUrl:null,
        caption: null,
        displayOrder: null,
        status: null,
        associatedProductId: null,
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
      this.ids = selection.map(item => item.carouselId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加轮播图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const carouselId = row.carouselId || this.ids
      getCarousel(carouselId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改轮播图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.carouselId != null) {
            updateCarousel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCarousel(this.form).then(response => {
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
      const carouselIds = row.carouselId || this.ids;
      this.$modal.confirm('是否确认删除轮播图编号为"' + carouselIds + '"的数据项？').then(function() {
        return delCarousel(carouselIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/carousel/export', {
        ...this.queryParams
      }, `carousel_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
