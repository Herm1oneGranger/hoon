<template>
  <div id="chart-part1" style="height:100%;width: 100%;"/>
</template>
<script>
// import { getAddress } from '@/api/dashboard'
export default {
  data() {
    return {
      provinceName: [],
      count: []
    }
  },
  mounted() {
    this.$nextTick(()=>{
      this.getList()
    })
  },
  methods: {
    async getList(){
      const {data} = await this.$store.dispatch('dashboard/getAddressByProvince')
              
      const countArr = []
      const address = []
      data.forEach(item => {
        countArr.push(item.number)
        address.push(item.province)
      })
      this.provinceName = address
      this.count = countArr
      setTimeout(() => {
        this.getEchartData1()
      }, 1)
    },
    getOrderList() {
      // getAddress({}).then(data => {
      //   if (data.data.data.length > 0) {
      //     const countArr = []
      //     const address = []
      //     data.data.data.forEach(item => {
      //       countArr.push(item.number)
      //       address.push(item.province)
      //     })
      //     this.provinceName = address
      //     this.count = countArr
      //     this.$emit('getLen', data.data.data.length)
      //   } else {
      //     this.provinceName = []
      //     this.count = []
      //   }
      //   setTimeout(() => {
      //     this.getEchartData1()
      //   }, 1)
      // })
    },
    getEchartData1() {
      const myChart = this.$echarts.init(document.getElementById('chart-part1'))
      const _this = this
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: _this.provinceName,
          axisLabel: {
            interval: 0,
            color: '#666',
            formatter: function(value) {
              return value.substring(0, 4)
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            color: '#666'
          }
        },
        series: [{
          name: '总数',
          data: _this.count,
          type: 'bar',
          barWidth: 40,
          itemStyle: {
            normal: {
              color: '#92efff'
            }
          }
        }]
      }
      myChart.setOption(option)
      setTimeout(() => {
        myChart.resize()
      }, 11)
      window.addEventListener('resize', function() {
        setTimeout(() => {
          myChart.resize()
        }, 11)
      })
    }
  }
}
</script>

<style scoped>
  
</style>
