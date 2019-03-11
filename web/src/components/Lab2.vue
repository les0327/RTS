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
    <div>
      <label>
        from: <input v-model.number="timeFrom" type="number" @change="getTimeChart">
      </label>
      <label>
        to: <input v-model.number="timeTo" type="number" @change="getTimeChart">
      </label>
      <label>
        step: <input v-model.number="multiplier" type="number" @change="getTimeChart">
      </label>
      <div>
        <canvas id="timeChartLab2"></canvas>
      </div>
    </div>
  </div>
</template>

<style>
</style>

<script>
  import axios from 'axios';
  import ChartUtil from "./ChartUtil";

  export default {
    name: "Lab2",
    components: {Chart},
    created() {
      this.getCharts();
      this.getTimeChart();
    },
    data() {
      return {
        from: '0',
        to: '1',
        timeFrom: '100',
        timeTo: '200',
        multiplier: '50',
        xEv: undefined,
        yEv: undefined,
        xVariance: undefined,
        yVariance: undefined,
        xChart: undefined,
        yChart: undefined,
        rxxChart: undefined,
        ryyChart: undefined,
        rxyChart: undefined,
        timeChartL: undefined
      }
    },
    methods: {
      getCharts() {
        axios.get(`/api/v1/lab2/chart?from=${this.from}&to=${this.to}`)
          .then(response => {
            if (!this.xChart) {
              this.xChart = ChartUtil.chart('xChartLab2', [ChartUtil.dataset('X(t)', '#FF06FF', [])]);
            }
            if (!this.yChart) {
              this.yChart = ChartUtil.chart('yChartLab2', [ChartUtil.dataset('Y(t)', '#ce63dd', [])]);
            }
            if (!this.rxxChart) {
              this.rxxChart = ChartUtil.chart('rxxChart', [ChartUtil.dataset('Rxx(t)', '#ce63dd', [])]);
            }
            if (!this.ryyChart) {
              this.ryyChart = ChartUtil.chart('ryyChart', [ChartUtil.dataset('Ryy(t)', '#dd3f4e', [])]);
            }
            if (!this.rxyChart) {
              this.rxyChart = ChartUtil.chart('rxyChart', [ChartUtil.dataset('Rxy(t)', '#dd72af', [])]);
            }
            const data = response.data;

            this.xEv = data.xEv;
            this.yEv = data.yEv;

            this.xVariance = data.xVariance;
            this.yVariance = data.yVariance;

            ChartUtil.refreshChart(this.xChart, data.xChart);
            ChartUtil.refreshChart(this.yChart, data.yChart);
            ChartUtil.refreshChart(this.rxxChart, data.rxx);
            ChartUtil.refreshChart(this.ryyChart, data.ryy);
            ChartUtil.refreshChart(this.rxyChart, data.rxy);
          });
      },
      getTimeChart() {
        axios.get(`/api/v1/lab2/time/chart?from=${this.timeFrom}&to=${this.timeTo}&multiplier=${this.multiplier}`)
          .then(response => {
            if (!this.timeChart) {
              this.timeChart = ChartUtil.chart('timeChartLab2', [
                ChartUtil.dataset('Trxx(N)', '#44ff24', []),
                ChartUtil.dataset('Trxy(N)', '#7131ff', [])
              ]);
            }
            ChartUtil.refreshChart(this.timeChart, response.data.rxx, 'Trxx(N)');
            ChartUtil.refreshChart(this.timeChart, response.data.rxy, 'Trxy(N)');
          });
      }
    }
  }
</script>
