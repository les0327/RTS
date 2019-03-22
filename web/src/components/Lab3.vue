<template>
  <div id="lab3">
    <h1>Lab3</h1>
    <label>
      N: <input v-model.number="N" type="number" @change="getChart">
    </label>
    <div>
      <!--<canvas id="real"></canvas>-->
    </div>
    <div>
      <!--<canvas id="imagine"></canvas>-->
    </div>
    <div id="amplitude" style="width:100%; min-height:500px;"></div>

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
      <canvas id="timeChart"></canvas>
    </div>

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
      this.getTimeChart();
    },
    data() {
      return {
        N: 1024,
        timeFrom: '100',
        timeTo: '110',
        multiplier: '10',
        realChart: undefined,
        imagineChart: undefined,
        amplitudeChart: undefined,
        timeChart: undefined
      }
    },
    methods: {
      getChart() {
        axios.get(`/api/v1/lab3/dft/chart?N=${this.N}`)
          .then(response => {
            // if (!this.realChart) {
            //   this.realChart = ChartUtil.chart('real', [ChartUtil.dataset('R(p)', '#FF06FF', [])]);
            // }
            // if (!this.imagineChart) {
            //   this.imagineChart = ChartUtil.chart('imagine', [ChartUtil.dataset('Im(p)', '#3afcff', [])]);
            // }
            //
            // ChartUtil.refreshChart(this.realChart, response.data.map((p, i) => ({x: i, y: p.real})));
            // ChartUtil.refreshChart(this.imagineChart, response.data.map((p, i) => ({x: i, y: p.imagine})));

            if (!this.amplitudeChart) {
              this.amplitudeChart = echarts.init(document.getElementById('amplitude'));
            }

            this.amplitudeChart.setOption({
              legend: {
                data: ['|F(p)|'],
                align: 'left'
              },
              dataZoom: [{
                type: 'inside'
              }, {
                type: 'slider'
              }],
              xAxis: {
                data: response.data.map((p, i) => i),
                silent: false,
                splitLine: {
                  show: false
                },
                splitArea: {
                  show: false
                }
              },
              yAxis: {
                splitArea: {
                  show: false
                }
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'shadow'
                }
              },
              series: [{
                type: 'bar',
                data: response.data.map(p => Math.sqrt(p.real * p.real + p.imagine * p.imagine)),
                large: true
              }]
            })
          })
      },
      getTimeChart() {
        axios.get(`/api/v1/lab3/time/chart?from=${this.timeFrom}&to=${this.timeTo}&multiplier=${this.multiplier}`)
          .then(response => {
            if (!this.timeChart) {
              this.timeChart = ChartUtil.chart('timeChart', [
                ChartUtil.dataset('With(N)', '#44ff24', []),
                ChartUtil.dataset('Without(N)', '#7131ff', [])
              ]);
            }
            ChartUtil.refreshChart(this.timeChart, response.data.with, 'With(N)');
            ChartUtil.refreshChart(this.timeChart, response.data.without, 'Without(N)');
          });
      }
    }
  }
</script>
