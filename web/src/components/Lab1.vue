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
    <div>
      <label>
        from: <input v-model.number="dFrom" type="number" @change="getDChart">
      </label>
      <label>
        to: <input v-model.number="dTo" type="number" @change="getDChart">
      </label>
      <canvas id="DChartLab1"></canvas>
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
      this.getDChart();
    },
    data() {
      return {
        from: '0',
        to: '1',
        count: '10',
        step: '500',
        dFrom: '0',
        dTo: '1024',
        ev: undefined,
        variance: undefined,
        xChart: undefined,
        timeChart: undefined,
        dChart: undefined
      }
    },
    methods: {
      getXChart() {
        axios.get(`/api/v1/lab1/x/chart?from=${this.from}&to=${this.to}`)
          .then(response => {
            this.ev = response.data.ev;
            this.variance = response.data.variance;
            this.renderChart('xChartLab1', 'X(t)', response.data.chart)
          });
      },
      getTimeChart() {
        axios.get(`/api/v1/lab1/time/chart?count=${this.count}&step=${this.step}`)
          .then(response => {
            this.renderChart('timeChartLab1', 'T(N)', response.data)
          });
      },
      getDChart() {
        axios.get(`/api/v1/lab1/D/chart?from=${this.dFrom}&to=${this.dTo}`)
          .then(response => {
            this.renderChart('DChartLab1', 'D(N)', response.data)
          });
      },
      renderChart(chart, id, title, data) { // todo: try to update chart instead recreating
        new Chart(document.getElementById(id),
          ChartDataProvider.chartData(title, '#'+Math.floor(Math.random()*16777215).toString(16), data));
      }
    }
  }
</script>
