<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

const animationDuration = 6000;

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chartExt",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
    todayWithdrawalTypeNum: {
      type: Array,
      default: () => [],
    },
  },
  watch: {
    todayWithdrawalTypeNum: {
      handler(newId, oldId) {
        console.log("newId:" + newId);
        this.todayWithdrawalTypeNum = newId;
        this.initChart();
      },
      immediate: true,
      deep: true,
    },
  },
  data() {
    return {
      chart: null,
      todayWithdrawalTypeNum: [],
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        grid: {
          top: 10,
          left: "2%",
          right: "2%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: [
              "用户申请提现",
              "达人申请提现",
              "代理商申请提现",
              "当日平台反馈量",
            ],
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: "数量",
            type: "bar",
            stack: "vistors",
            barWidth: "60%",
            data: this.todayWithdrawalTypeNum,
            animationDuration,
          },
        ],
      });
    },
  },
};
</script>
