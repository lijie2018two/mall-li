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
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
    todayOrderTypeNum: {
      type: Array,
      default: () => [],
    },
  },
  watch: {
    todayOrderTypeNum: {
      handler(newId, oldId) {
        console.log("newId:" + newId);
        this.todayOrderTypeNum = newId;
        this.initChart();
      },
      immediate: true,
      deep: true,
    },
  },
  data() {
    return {
      chart: null,
      todayOrderTypeNum: [],
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
      console.log("this.todayOrderTypeNum:" + this.todayOrderTypeNum);
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
              "今日进行中",
              "今日已完成订单",
              "今日未接订单",
              "今日投诉订单",
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
            data: this.todayOrderTypeNum,
            animationDuration,
          },
        ],
      });
    },
  },
};
</script>
