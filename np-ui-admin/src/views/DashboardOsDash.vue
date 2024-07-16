<template>
  <div id="chart-partos" class="wrapper"/>
</template>
<script>
// import { getDashOs } from '@/api/dashboard'
export default {
  data() {
    return {
      count: []
    }
  },
  created() {
    this.getOrderList()
  },
  methods: {
    getOrderList() {
      // getDashOs({}, '/dashboard/getOS').then(data => {
      //   if (data.data.data) {
      //     const arr = []
      //     for (const key in data.data.data) {
      //       const obj = {
      //         name: key,
      //         value: data.data.data[key]
      //       }
      //       arr.push(obj)
      //     }
      //     this.count = arr
      //     setTimeout(() => {
      //       this.getEchartData1()
      //     }, 1)
      //   }
      // })
    },
    getEchartData1() {
      const myChart = this.$echarts.init(document.getElementById('chart-partos'))
      const _this = this

      const option = {

        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['android', 'ios']
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: _this.count,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      myChart.setOption(option)
      window.addEventListener('resize', function() {
        myChart.resize()
      })
    }
  }
}
</script>

<style scoped>
  .wrapper{
    width:100%;
    height: 290px;
  }
</style>
