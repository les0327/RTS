<template>
  <div id="lab4">
    <h1>Lab4</h1>
    <label>
      N: <input v-model.number="N" type="number" @change="getChart">
    </label>
    <div id="amplitude" style="width:100%; min-height:500px;"></div>
    <label>
      step: <input v-model.number="count" type="number" @change="getTime">
    </label>
    <div>
      <canvas id="time"></canvas>
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
    name: "Lab4",
    created() {
      this.getChart();
      this.getTime();
    },
    data() {
      return {
        N: 1024,
        count: 8,
        amplitudeChart: undefined,
        timeChart: undefined
      }
    },
    methods: {
      getTime() {
        axios.get(`/api/v1/lab4/time/chart?count=${this.count}`)
          .then(response => {
            if (!this.timeChart) {
              this.timeChart = ChartUtil.chart('time', [ChartUtil.dataset('P(N)', '#FF06FF', [])]);
            }
            ChartUtil.refreshChart(this.timeChart, response.data)
          })
      },
      getChart() {
        axios.get(`/api/v1/lab4/fft/chart?N=${this.N}`)
          .then(response => {
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
      }
    }
  }
</script>
