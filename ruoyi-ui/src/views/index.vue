<template>
  <div class="app-container home">
    <el-row gutter="70">
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            总收入:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            总支出:<span>200</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            总订单量:<span>100</span>
          </div>
        </el-row>
      </el-col>
    </el-row>
    <el-row gutter="15">
      <el-col :span="8">
        <el-row gutter="15">
          <h1>当日数据</h1>
        </el-row>
      </el-col>
    </el-row>
    <el-row gutter="70">
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日收入:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日支出:<span>200</span>
          </div>
        </el-row>
      </el-col>

      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日新增代理商:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日新增达人:<span>200</span>
          </div>
        </el-row>
      </el-col>
    </el-row>
    <el-row gutter="70"> </el-row>
    <el-row gutter="60">
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日进行中订单:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日已完成订单:<span>200</span>
          </div>
        </el-row>
      </el-col>

      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日未接订单:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="6">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            今日投诉订单:<span>200</span>
          </div>
        </el-row>
      </el-col>
    </el-row>
    <el-row gutter="15">
      <h1>当日提现记录</h1>
    </el-row>
    <el-row gutter="60">
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            用户申请提现:<span>200</span>
          </div>
        </el-row>
      </el-col>

      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            达人申请提现:<span>300</span>
          </div>
        </el-row>
      </el-col>
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            代理商申请提现:<span>200</span>
          </div>
        </el-row>
      </el-col>
    </el-row>

    <el-row gutter="15">
      <el-col :span="8">
        <el-row gutter="15">
          <div class="grid-content ep-bg-purple content-center">
            当日平台反馈量:<span>200</span>
          </div>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>


<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        field107: undefined,
        field108: undefined,
        field112: undefined,
        field114: undefined,
        field120: undefined,
        field121: undefined,
        field125: undefined,
        field127: undefined,
        field130: undefined,
        field132: undefined,
        field134: undefined,
        field136: undefined,
        field139: undefined,
        field142: undefined,
        field144: undefined,
        field146: undefined,
        field149: undefined,
      },
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
  .el-row {
    margin-bottom: 20px;
  }
  .el-row:last-child {
    margin-bottom: 0;
  }
  .el-col {
    border-radius: 4px;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 50%;

    font-size: 25px;
  }
  .content-center {
    display: flex; /* 使用Flexbox布局 */
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    height: 100px;
  }
}
</style>
