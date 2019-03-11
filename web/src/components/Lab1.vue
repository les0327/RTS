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

<style>
</style>

<script>
  import axios from 'axios';
  import ChartUtil from "./ChartUtil";

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
        dFrom: '2',
        dTo: '500',
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
            if (!this.xChart) {
              this.xChart = ChartUtil.chart('xChartLab1', [ChartUtil.dataset('X(t)', '#FF06FF', [])]);
            }
            ChartUtil.refreshChart(this.xChart, response.data.chart)
          });
      },
      getTimeChart() {
        axios.get(`/api/v1/lab1/time/chart?count=${this.count}&step=${this.step}`)
          .then(response => {
            if (!this.timeChart) {
              this.timeChart = ChartUtil.chart('timeChartLab1', [ChartUtil.dataset('T(N)', '#ccff63', [])]);
            }
            ChartUtil.refreshChart(this.timeChart, response.data)
          });
      },
      getDChart() {
        axios.get(`/api/v1/lab1/D/chart?from=${this.dFrom}&to=${this.dTo}`)
          .then(response => {
            if (!this.dChart) {
              this.dChart = ChartUtil.chart('DChartLab1', [ChartUtil.dataset('D(N)', '#FF06FF',[])]);
            }
            ChartUtil.refreshChart(this.dChart, response.data)
          });
      }
    }
  }
</script>
