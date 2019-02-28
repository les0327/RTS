<template>
  <div id="lab1">
    <h1>Lab1</h1>
    <div>
      <p>M: {{ev}}</p>
      <p>D: {{variance}}</p>
    </div>
    <canvas id="xChart"></canvas>
  </div>
</template>

<script>
  import Chart from 'chart.js';
  import axios from 'axios';

  export default {
    name: "Lab1",
    created() {
      axios.get('http://localhost:8080/api/v1/lab1/x/chart?from=0&to=2')
        .then(response => {
          this.setChartData(response.data);
          this.refreshChart();
        })
        .catch(error => {
          console.log(error);
        })
    },
    data() {
      return {
        ev: undefined,
        variance: undefined,
        chart: undefined
      }
    },
    methods: {
      setChartData(data) {
        this.ev = data.ev;
        this.variance = data.variance;
        this.chart = data.chart;
      },
      refreshChart() {
        new Chart('xChart', {
          type: 'line',
          data: {
            datasets: [{
              label: 'X(t)',
              data: this.chart,
              borderColor: "#6a7fdd",
              fill: false
            }]
          },
          options: {
            animation: {
              duration: 500, // general animation time
            },
            scales: {
              yAxes: [{
                id: 'X',
                type: 'linear',
                position: 'left'
              }],
              xAxes: [{
                id: 't',
                type: 'linear',
                position: 'bottom'
              }]
            }
          }
        });
      }
    }
  }
</script>

<style>

</style>
