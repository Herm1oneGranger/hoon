<template>
  <div id="chart-part2" style="height:100%;width: 100%;" />
</template>

<script>
// import { getDashLabel } from '@/api/dashboard'
export default {
  props: {
    dataList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      indexs: 0
    }
  },
  mounted() {
    this.getEchartData1()
  },
  methods: {
    getDayList() {
      // getDashLabel({}, '/dashboard/getWeekFake').then(data => {
      //   this.dataList = data.data.data
      //   this.getEchartData1()
      // })
    },
    getEchartData1() {
      const myChart = this.$echarts.init(document.getElementById('chart-part2'))
      const _this = this
      const dateList = []
      const valueList = []
      this.dataList.forEach(item => {
        dateList.push(item.checkedDate)
        valueList.push(item.number)
      })
      const option = {
        visualMap: [{
          show: false,
          type: 'continuous'
        }],
        title: [{
          left: 'center',
          color: '#ccc'
        }],
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {

          data: dateList,
          nameLocation: 'start',
          offset: 3,
          nameTextStyle: {
            fontSize: 14,
            padding: [37, 0, 0, 0],
            color: '#000'
          },
          axisLabel: {
            interval: 0,
            color: '#666',
            formatter: function(value) {
              _this.indexs++
              if (value.length > 7) {
                return value
              } else {
                if (!_this.indexs === 1 || !_this.indexs === 13) {
                  value = ''
                }
                return value
              }
            }
          }

        },
        yAxis: {
          splitLine: { show: false }
        },

        series: {
          type: 'line',
          showSymbol: false,
          data: valueList,
          lineStyle: {
            normal: {
              color: '#002b48',
              lineStyle: {
                width: 2
              }
            }
          }
        }
      }
      myChart.setOption(option)
      _this.indexs = 0
      window.addEventListener('resize', function() {
        _this.indexs = 0
        myChart.resize()
      })
    }
  }
}
</script>

<style scoped lang="less">
</style>
