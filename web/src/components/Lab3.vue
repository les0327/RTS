<template>
  <div id="lab3">
    <label>
      N: <input v-model.number="N" type="number" @change="getChart">
    </label>
    <div>
      <canvas id="real"></canvas>
    </div>
    <div>
      <canvas id="imagine"></canvas>
    </div>
    <div id="amplitude" style="width:100%; min-height:400px;"></div>
  </div>
</template>

<style>
</style>

<script>
  import axios from 'axios';
  import echarts from 'echarts'
  import 'echarts/lib/chart/bar'
  import ChartUtil from "./ChartUtil";

  export default {
    name: "Lab3",
    created() {
      this.getChart();
    },
    data() {
      return {
        N: 4,
        realChart: undefined,
        imagineChart: undefined,
        amplitudeChart: undefined,
      }
    },
    methods: {
      getChart() {
        axios.get(`http://localhost:8080/api/v1/lab3/dft/chart?N=${this.N}`)
          .then(response => {
            if (!this.realChart) {
              this.realChart = ChartUtil.chart('real', [ChartUtil.dataset('R(p)', '#FF06FF', [])]);
            }
            if (!this.imagineChart) {
              this.imagineChart = ChartUtil.chart('imagine', [ChartUtil.dataset('Im(p)', '#3afcff', [])]);
            }

            ChartUtil.refreshChart(this.realChart, response.data.map((p, i) => ({x: i, y: p.real})));
            ChartUtil.refreshChart(this.imagineChart, response.data.map((p, i) => ({x: i, y: p.imagine})));

            if (!this.amplitudeChart) {
              this.amplitudeChart = echarts.init(document.getElementById('amplitude'));
            }

            this.amplitudeChart.setOption({
              legend: {
                data: ['|F(p)|'],
                align: 'left'
              },
              xAxis: {
                data: response.data.map((p, i) => i)
              },
              yAxis: {
              },
              series: [{
                name: 'bar',
                type: 'bar',
                data: response.data.map(p => Math.sqrt(p.real * p.real + p.imagine * p.imagine))
              }]
            })
          })
      }
    }
  }
</script>
