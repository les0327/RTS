<template>
  <div id="lab1">
    <h1>Lab1</h1>
    <div>
      <p>M: {{ev}}</p>
      <p>D: {{variance}}</p>
    </div>
    <canvas id="xChartLab1"></canvas>
    <canvas id="timeChartLab1"></canvas>
  </div>
</template>

<script>
  import axios from 'axios';
  import Chart from "chart.js";
  import ChartDataProvider from "./ChartDataProvider";

  export default {
    name: "Lab1",
    components: {Chart},
    created() {
      axios.get('http://localhost:8080/api/v1/lab1/x/chart?from=0&to=1')
        .then(response => {
          this.setXChartData(response.data);
          this.renderChart()
        });
      axios.get('http://localhost:8080/api/v1/lab1/time/chart?count=20&step=50000')
        .then(response => {
          this.setTimeChartData(response.data);
          this.renderChart()
        });
    },
    data() {
      return {
        ev: undefined,
        variance: undefined,
        xChartData: undefined,
        timeChartData: undefined
      }
    },
    methods: {
      setXChartData(data) {
        this.ev = data.ev;
        this.variance = data.variance;
        this.xChartData = data.chart;
      },
      setTimeChartData(data) {
        this.timeChartData = data;
      },
      renderChart() {
        const xCtx = document.getElementById('xChartLab1');
        const timeCtx = document.getElementById('timeChartLab1');

        const xChart = new Chart(xCtx, ChartDataProvider.chartData('X(t)', '#6a7fdd', this.xChartData));
        const timeChart = new Chart(timeCtx, ChartDataProvider.chartData('T(N)', '#50dd95', this.timeChartData));
      }
    }
  }
</script>

<style>

</style>
