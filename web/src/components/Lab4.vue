<template>
  <div id="lab4">
    <h1>Lab4</h1>
    <label>
      N: <input v-model.number="N" type="number" @change="getChart">
    </label>
    <div id="amplitude" style="width:100%; min-height:500px;"></div>
  </div>
</template>

<style>
</style>

<script>
  import axios from 'axios';
  import echarts from 'echarts'
  import 'echarts/lib/chart/bar'

  export default {
    name: "Lab4",
    created() {
      this.getChart();
    },
    data() {
      return {
        N: 1024,
        amplitudeChart: undefined,
      }
    },
    methods: {
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
