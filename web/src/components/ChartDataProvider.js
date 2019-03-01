export default {
  chartData(label, color, data) {
    return {
      type: 'line',
      data: {
        datasets: [{
          label: label,
          data: data,
          borderColor: color,
          fill: false
        }]
      },
      options: {
        animation: {
          duration: 200, // general animation time
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
  }
}
