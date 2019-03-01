<template>
  <div id="lab1">
    <h1>Lab1</h1>
    <div>
      <p>M: {{ev}}</p>
      <p>D: {{variance}}</p>
    </div>
    <div>
      <label>
        from: <input v-model.number="from" type="number" @change="getXChart">
      </label>
      <label>
        to: <input v-model.number="to" type="number" @change="getXChart">
      </label>
      <canvas id="xChartLab1"></canvas>
    </div>
    <div>
      <label>
        count: <input v-model.number="count" type="number" @change="getTimeChart">
      </label>
      <label>
        step: <input v-model.number="step" type="number" @change="getTimeChart">
      </label>
      <canvas id="timeChartLab1"></canvas>
    </div>
  </div>
</template>

<style scoped>
</style>

<script>
  import axios from 'axios';
  import Chart from "chart.js";
  import ChartDataProvider from "./ChartDataProvider";

  export default {
    name: "Lab1",
    components: {Chart},
    created() {
      this.getXChart();
      this.getTimeChart();
    },
    data() {
      return {
        from: '0',
        to: '1',
        count: '10',
        step: '50000',
        ev: undefined,
        variance: undefined,
        xChartData: undefined,
        timeChartData: undefined
      }
    },
    methods: {
      getXChart() {
        axios.get(`http://localhost:8080/api/v1/lab1/x/chart?from=${this.from}&to=${this.to}`)
          .then(response => {
            this.setXChartData(response.data);
            this.renderChart()
          });
      },
      getTimeChart() {
        axios.get(`http://localhost:8080/api/v1/lab1/time/chart?count=${this.count}&step=${this.step}`)
          .then(response => {
            this.setTimeChartData(response.data);
            this.renderChart()
          });
      },
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
