<template>
  <div id="chart-part63" class="chart-panel"></div>
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
  watch:{
    dataList(){
      this.$nextTick(()=>{
        this.initEchats()
      })
    }
  },
  methods: {

    initEchats() {
      const dateList = []
      const valueList = []
      this.dataList.forEach(item => {
        dateList.push(item.checkedDate)
        valueList.push(item.number)
      })
      const myChart = this.$echarts.init(document.getElementById('chart-part63'))
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
          show: false,
          axisLabel: {
            color: '#666'
          }
        }],
        yAxis: [{
          name: '近七天趋势',
          show: false,
          axisLabel: {
            color: '#666'
          }
        }],
        series: {
          type: 'line',
          showSymbol: true,
          data: valueList,
          itemStyle: {
            normal: {
              color: '#01ccfe',
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
