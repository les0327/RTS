import Chart from "chart.js";

export default {
  chart(id, datasets) {
    return new Chart(document.getElementById(id), this.chartOptions(datasets))
  },
  chartOptions(datasets) {
    return {
      type: 'line',
      data: {
        datasets: datasets
      },
      options: {
        animation: {
          duration: 0,
        },
        scales: {
          yAxes: [{
            type: 'linear',
            position: 'left'
          }],
          xAxes: [{
            type: 'linear',
            position: 'bottom'
          }]
        }
      }
    }
  },
  dataset(label, color, data) {
    return {label: label, borderColor: color, data: data, fill: false}
  }
  ,
  refreshChart(chart, data) {
    chart.data.datasets.forEach((dataset) => {
      dataset.data = data ? data : [];
    });
    chart.update();
  }
}
