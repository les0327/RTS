<template>
  <div id="lab2">
    <h1>Lab2</h1>
    <div>
      <label>
        from: <input v-model.number="from" type="number" @change="getChartData">
      </label>
      <label>
        to: <input v-model.number="to" type="number" @change="getChartData">
      </label>
      <div>
        <p>Mx: {{xEv}}</p>
        <p>Dx: {{xVariance}}</p>
        <canvas id="xChartLab2"></canvas>
      </div>
      <div>
        <p>My: {{yEv}}</p>
        <p>Dy: {{yVariance}}</p>
        <canvas id="yChartLab2"></canvas>
      </div>
    </div>
    <div>
      <div>
        <canvas id="rxxChart"></canvas>
      </div>
      <div>
        <canvas id="ryyChart"></canvas>
      </div>
      <div>
        <canvas id="rxyChart"></canvas>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import Chart from "chart.js";
  import ChartDataProvider from "./ChartUtil";

  export default {
    name: "Lab2",
    components: {Chart},
    created() {
      this.getChartData();
    },
    data() {
      return {
        from: '0',
        to: '1',
        timeFrom: '2',
        timeTo: '20',
        xEv: undefined,
        yEv: undefined,
        xVariance: undefined,
        yVariance: undefined,
        xChartData: undefined,
        yChartData: undefined,
        rxxChartData: undefined,
        ryyChartData: undefined,
        rxyChartData: undefined
      }
    },
    methods: {
      getChartData() {
        axios.get(`/api/v1/lab2/chart?from=${this.from}&to=${this.to}`)
          .then(response => {
            this.setChartData(response.data);
            this.renderChart()
          });
      },
      getTimeChart() {
        axios.get(`http://localhost:8080/api/v1/lab2/time/chart?from=${this.timeFrom}&to=${this.timeTo}`) // todo: rewrite
          .then(response => {
            if (!this.timeChart) {
              this.timeChart = ChartUtil.chart('timeChartLab1', [ChartUtil.dataset('T(N)', '#ccff63', [])]);
            }
            ChartUtil.refreshChart(this.timeChart, response.data)
          });
      },
      setChartData(data) {
        this.xEv = data.xEv;
        this.yEv = data.yEv;
        this.xVariance = data.xVariance;
        this.yVariance = data.yVariance;

        this.xChartData = data.xChart;
        this.yChartData = data.yChart;

        this.rxxChartData = data.rxx;
        this.ryyChartData = data.ryy;
        this.rxyChartData = data.rxy;
      },
      renderChart() {
        const xCtx = document.getElementById('xChartLab2');
        const yCtx = document.getElementById('yChartLab2');
        const rxxCtx = document.getElementById('rxxChart');
        const ryyCtx = document.getElementById('ryyChart');
        const rxyCtx = document.getElementById('rxyChart');

        new Chart(xCtx, ChartDataProvider.chartData('X(t)', '#6a7fdd', this.xChartData));
        new Chart(yCtx, ChartDataProvider.chartData('Y(t)', '#ce63dd', this.yChartData));
        new Chart(rxxCtx, ChartDataProvider.chartData('Rxx(t)', '#dddb97', this.rxxChartData));
        new Chart(ryyCtx, ChartDataProvider.chartData('Ryy(t)', '#dd3f4e', this.rxyChartData));
        new Chart(rxyCtx, ChartDataProvider.chartData('Rxy(t)', '#dd72af', this.rxyChartData));
      }
    }
  }
</script>

<style>

</style>
