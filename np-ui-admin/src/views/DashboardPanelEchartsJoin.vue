<template>
  <div id="chart-part5" class="chart-panel"/>
</template>

<script>
export default {
  props: {
    dataList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
    }
  },
  mounted() {
    this.getEchartData1()
  },
  methods: {
    getEchartData1() {
      const dateList = []
      const valueList = []
      this.dataList.forEach(item => {
        dateList.push(item.checkedDate)
        valueList.push(item.number)
      })
      const myChart = this.$echarts.init(document.getElementById('chart-part5'))
      const option = {
        tooltip: {
          trigger: 'axis',
          position: function(pos, params, dom, rect, size) {
            // 鼠标在左侧时 tooltip 显示到右侧，鼠标在右侧时 tooltip 显示到左侧。
            var obj = { top: 55 }
            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 5
            return obj
          },
          padding: [3, 3]
        },
        xAxis: [{
          data: dateList,
          show: false
        }],
        yAxis: [{
          show: false
        }],
        series: {
          type: 'line',
          showSymbol: true,
          data: valueList,
          itemStyle: {
            normal: {
              color: '#a1b9c7',
              lineStyle: {
                width: 2// 折线宽度
              }
            }
          }
        }

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
  .chart-panel{
    width: 100%;
    height: 90px;
  }

</style>
