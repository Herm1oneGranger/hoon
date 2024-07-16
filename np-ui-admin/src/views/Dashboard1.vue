<template>
  <div class="wrapper">
    <main class="box_main">
      <div class="left_box">
        <div class="left_main_box">
          <a-row :gutter="10" class="panel-group">
            <a-col :xs="24" :sm="24" :lg="24" class="card-pana-col">
              <div class="card-panel">
                <div class="card-panel-description">
                  <count-to v-if="pause" :start-val="0" :end-val="allCheckedToken" :duration="duration" class="card-panel-num"/>
                  <p v-else class="card-panel-num">{{ allCheckedToken }}</p>
                  <div class="card-panel-text">激活个数</div>
                </div>
                <div class="banEcharts">
                  <panel-echart v-if="panelData.length>0" :data-list="panelData"/>
                  <p class="mess_echart">近七天趋势</p>
                </div>
              </div>
            </a-col>
            <a-col :xs="24" :sm="24" :lg="24" class="card-pana-col">
              <div class="card-panel">
                <div class="card-panel-description">
                  <count-to v-if="pause" :start-val="0" :end-val="allCheckedPeople" :duration="duration" class="card-panel-num"/>
                  <p v-else class="card-panel-num">{{ allCheckedPeople }}</p>
                  <div class="card-panel-text">参与用户</div>
                </div>
                <div class="banEcharts">
                  <panel-echart-join v-if="joinData.length>0" :data-list="joinData"/>
                  <p class="mess_echart">近七天趋势</p>
                </div>
              </div>
            </a-col>
            <a-col :xs="24" :sm="24" :lg="24" class="card-pana-col">
              <div class="card-panel">
                <div class="card-panel-description">
                  <count-to v-if="pause" :start-val="0" :end-val="allCheckedCount" :duration="duration" class="card-panel-num"/>
                  <p v-else class="card-panel-num">{{ allCheckedCount }}</p>
                  <div class="card-panel-text">校验次数</div>
                </div>
                <div class="banEcharts">
                  <panel-echart-check v-if="checkData.length>0" :data-list="checkData"/>
                  <p class="mess_echart">近七天趋势</p>
                </div>
              </div>
            </a-col>
            <a-col :xs="24" :sm="24" :lg="24" class="card-pana-col">
              <div class="card-panel">
                <div class="card-panel-description">
                  <count-to v-if="pause" :start-val="0" :end-val="allFake" :duration="duration" class="card-panel-num"/>
                  <p v-else class="card-panel-num">{{ allFake }}</p>
                  <div class="card-panel-text">报假次数</div>
                </div>
                <div class="banEcharts">
                  <panel-echart-fake v-if="fakeData.length>0" :data-list="fakeData"/>
                  <p class="mess_echart">近七天趋势</p>
                </div>
              </div>
            </a-col>
          </a-row>
        </div>
        <div class="left_right_main_box">
          <a-row>
            <a-col :xs="24" :sm="24" :lg="24">
              <div class="tabs">
                <div class="tabChange">
                  <p class="center_mess"> 扫码次数，地理位置分布信息图（表）</p>
                  <p class="che" @click="changeAddress('address')">图<span
                    :class="addressFlag==='address'?'act':''"/></p>
                  <p class="che" @click="changeAddress('tabList')">表<span
                    :class="addressFlag==='tabList'?'addAct':''"/></p>
                  <i class="refreach" @click="refreachVal">
                    <svg
                      t="1565836788732"
                      class="icon"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3918"
                      width="32"
                      height="32">
                      <path
                        d="M849.2 637.1c-12.7-5.8-27.7-0.1-33.5 12.6-54 118.9-173.2 195.7-303.7 195.7-160.5 0-294.8-114-326.4-265.3l56.1 56.1c4.9 4.9 11.4 7.4 17.9 7.4 6.5 0 13-2.5 17.9-7.4 9.9-9.9 9.9-25.9 0-35.8L171.2 494.1c-1.2-1.2-2.5-2.2-3.9-3.2-0.6-0.4-1.3-0.7-1.9-1.1-0.8-0.4-1.6-0.9-2.4-1.3-0.8-0.3-1.7-0.5-2.5-0.8-0.7-0.2-1.4-0.5-2.2-0.7-1.6-0.3-3.1-0.5-4.7-0.5h-0.2-0.2c-1.6 0-3.2 0.2-4.7 0.5-0.8 0.2-1.5 0.4-2.2 0.7-0.8 0.2-1.7 0.4-2.5 0.8-0.9 0.4-1.6 0.8-2.4 1.3-0.6 0.4-1.3 0.6-1.9 1.1-2.8 1.9-5.2 4.2-7 7-0.4 0.6-0.7 1.3-1 1.9-0.4 0.8-0.9 1.6-1.3 2.4-0.3 0.8-0.5 1.7-0.8 2.6-0.2 0.7-0.5 1.4-0.6 2.2-0.3 1.6-0.5 3.3-0.5 5 0 211.7 172.3 384 384 384 150.3 0 287.6-88.5 349.8-225.4 5.5-12.7-0.1-27.7-12.9-33.5zM880.4 535.4c0.8-0.4 1.6-0.8 2.4-1.3 0.6-0.4 1.3-0.6 1.9-1.1 2.8-1.9 5.2-4.2 7-7 0.4-0.6 0.7-1.3 1-1.9 0.4-0.8 0.9-1.6 1.3-2.4 0.3-0.8 0.5-1.7 0.8-2.6 0.2-0.7 0.5-1.4 0.6-2.2 0.3-1.6 0.5-3.3 0.5-5 0-211.7-172.3-384-384-384-152.1 0-290.1 89.9-351.5 229.1-5.6 12.8 0.1 27.7 12.9 33.4 12.8 5.7 27.7-0.2 33.4-12.9C260.1 256.7 379.9 178.6 512 178.6c160.5 0 294.8 114 326.4 265.3l-56.1-56.1c-9.9-9.9-25.9-9.9-35.8 0-9.9 9.9-9.9 25.9 0 35.8L852.9 530c1.2 1.2 2.5 2.2 3.9 3.2 0.6 0.4 1.3 0.7 1.9 1.1 0.8 0.4 1.6 0.9 2.4 1.3 0.8 0.3 1.7 0.5 2.5 0.8 0.7 0.2 1.4 0.5 2.2 0.6 1.6 0.3 3.3 0.5 5 0.5 1.7 0 3.3-0.2 5-0.5 0.8-0.1 1.5-0.4 2.2-0.6 0.7-0.5 1.5-0.7 2.4-1z"
                        p-id="3919"
                        fill="#cccccc"/>
                    </svg>
                  </i>
                </div>
                <div v-loading="loading" class="tabContent">
                  <add-ress v-if="addressFlag==='address'" ref="addRess" @getLen="getLen"/>
                  <tab-list v-if="addressFlag==='tabList'" ref="tabL"/>
                </div>
              </div>
            </a-col>
          </a-row>
        </div>
      </div>
      <div class="right_box">
        <div class="right_left_box">
          <a-row :gutter="10">
            <a-col :xs="24" :sm="24" :lg="24">
              <div class="tabs">
                <div class="tabChange">
                  <p class="center_mess"> 校验频率趋势图</p>
                  <p class="che" @click="changeMonth('day')">近7天<span
                    :class="dayMonthFlag==='day'?'act':''"/></p>
                  <p class="che" @click="changeMonth('dayMonth')">近一年<span
                    :class="dayMonthFlag==='dayMonth'?'act':''"/></p>
                  <i class="refreach refreach1" @click="zhexianRefreachVal">
                    <svg
                      t="1565836788732"
                      class="icon"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="3918"
                      width="32"
                      height="32">
                      <path
                        d="M849.2 637.1c-12.7-5.8-27.7-0.1-33.5 12.6-54 118.9-173.2 195.7-303.7 195.7-160.5 0-294.8-114-326.4-265.3l56.1 56.1c4.9 4.9 11.4 7.4 17.9 7.4 6.5 0 13-2.5 17.9-7.4 9.9-9.9 9.9-25.9 0-35.8L171.2 494.1c-1.2-1.2-2.5-2.2-3.9-3.2-0.6-0.4-1.3-0.7-1.9-1.1-0.8-0.4-1.6-0.9-2.4-1.3-0.8-0.3-1.7-0.5-2.5-0.8-0.7-0.2-1.4-0.5-2.2-0.7-1.6-0.3-3.1-0.5-4.7-0.5h-0.2-0.2c-1.6 0-3.2 0.2-4.7 0.5-0.8 0.2-1.5 0.4-2.2 0.7-0.8 0.2-1.7 0.4-2.5 0.8-0.9 0.4-1.6 0.8-2.4 1.3-0.6 0.4-1.3 0.6-1.9 1.1-2.8 1.9-5.2 4.2-7 7-0.4 0.6-0.7 1.3-1 1.9-0.4 0.8-0.9 1.6-1.3 2.4-0.3 0.8-0.5 1.7-0.8 2.6-0.2 0.7-0.5 1.4-0.6 2.2-0.3 1.6-0.5 3.3-0.5 5 0 211.7 172.3 384 384 384 150.3 0 287.6-88.5 349.8-225.4 5.5-12.7-0.1-27.7-12.9-33.5zM880.4 535.4c0.8-0.4 1.6-0.8 2.4-1.3 0.6-0.4 1.3-0.6 1.9-1.1 2.8-1.9 5.2-4.2 7-7 0.4-0.6 0.7-1.3 1-1.9 0.4-0.8 0.9-1.6 1.3-2.4 0.3-0.8 0.5-1.7 0.8-2.6 0.2-0.7 0.5-1.4 0.6-2.2 0.3-1.6 0.5-3.3 0.5-5 0-211.7-172.3-384-384-384-152.1 0-290.1 89.9-351.5 229.1-5.6 12.8 0.1 27.7 12.9 33.4 12.8 5.7 27.7-0.2 33.4-12.9C260.1 256.7 379.9 178.6 512 178.6c160.5 0 294.8 114 326.4 265.3l-56.1-56.1c-9.9-9.9-25.9-9.9-35.8 0-9.9 9.9-9.9 25.9 0 35.8L852.9 530c1.2 1.2 2.5 2.2 3.9 3.2 0.6 0.4 1.3 0.7 1.9 1.1 0.8 0.4 1.6 0.9 2.4 1.3 0.8 0.3 1.7 0.5 2.5 0.8 0.7 0.2 1.4 0.5 2.2 0.6 1.6 0.3 3.3 0.5 5 0.5 1.7 0 3.3-0.2 5-0.5 0.8-0.1 1.5-0.4 2.2-0.6 0.7-0.5 1.5-0.7 2.4-1z"
                        p-id="3919"
                        fill="#cccccc"/>
                    </svg>
                  </i>
                </div>
                <div v-loading="zhexianLoading" class="tabContent">
                  <data-trend v-if="zhexianDataList.length>0" ref="zhexian" :data-list="zhexianDataList"/>
                </div>
              </div>
            </a-col>
          </a-row>
        </div>
      </div>
    </main>

  </div>
</template>

<script>
// import { getReportData, getDashLabel } from '@/api/dashboard'
import CountTo from 'vue-count-to'
import addRess from './DashboardAddress'
import tabList from './DashboardTabList'
import dataTrend from './DashboardDataTrend.vue'
import panelEchart from './DashboardPanelEcharts'
import panelEchartCheck from './DashboardPanelEchartsCheck'
import panelEchartJoin from './DashboardPanelEchartsJoin'
import panelEchartFake from './DashboardPanelEchartsFake'
import osDash from './DashboardOsDash.vue'

export default {
  components: {
    CountTo,
    addRess,
    tabList,
    dataTrend,
    panelEchart,
    panelEchartCheck,
    panelEchartJoin,
    panelEchartFake,
    osDash
  },
  data() {
    return {
      path: 'ws://122.112.218.145:8181/websocket2warn',
      allCheckedCount: 0,
      allCheckedPeople: 0,
      allCheckedToken: 0,
      pause: true,
      duration: 3000,
      // allActivatedToken: 100,
      addressFlag: 'address',
      loading: false,
      zhexianLoading: false,
      dayMonthFlag: 'day',
      // 周激活个数
      zhexianDataList: [],
      allFake: 0,
      addLen: 0,
      panelData: [],
      // 周校验次数
      checkData: [],
      //  周参与用户
      joinData: [],
      // 报假趋势
      fakeData: [],
      osLoading: false
    }
  },
  created() {
    // getReportData().then(response => {
    //   this.allCheckedCount = response.data.data.allCheckedCount
    //   this.allCheckedPeople = response.data.data.allCheckedPeople
    //   this.allCheckedToken = response.data.data.allActivatedToken
    //   this.allFake = response.data.data.allFake
    // }).catch(res => {

    // })

    // 周激活个数
    this.getDashLa('/dashboard/getWeekActivatedToken', 'jihuo')
    // 周校验次数
    this.getDashLa('/dashboard/getWeekCheckedCount', 'jiaoyan')
    // 周参与用户
    this.getDashLa('/dashboard/getWeekCheckedPeople', 'canyu')
    // 周报假趋势
    this.getDashLa('/dashboard/getWeekFake', 'fake')
    this.getDayList()
  },
  mounted() {
    // this.init()
  },

  methods: {
    // 初始化webSocket
    init: function() {
      if (typeof (WebSocket) === 'undefined') {
        alert('您的浏览器不支持socket')
      } else {
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        // 监听socket消息
        this.socket.onmessage = this.getMessage
      }
    },
    open: function() {
      this.socket.send(this.isClick + '')
    },
    error: function() {
    },
    getMessage: function(msg) {
      this.pause = false
      const val = JSON.parse(msg.data)
      this.allCheckedCount = val.sumData.allCheckedCount
      this.allCheckedPeople = val.sumData.allCheckedPeople
      this.allFake = val.sumData.allFake
      this.checkData = val.weekCheckedCount
      this.joinData = val.weekCheckedPeople
      this.fakeData = val.weekFake
    },
    send: function() {
    },
    close: function() {
    },
    getLen(len) {
      // this.getLen = len;
    },
    refreachOsVal() {
      this.osLoading = true

      this.$refs.os.getOrderList()
      setTimeout(() => {
        this.osLoading = false
      }, 1500)
    },
    // 获取7天
    getDayList() {
      // getDashLabel({}, '/dashboard/getWeekCheckedCount').then(data => {
      //   if (data.data.data.length > 0) {
      //     this.zhexianDataList = data.data.data
      //   }
      // })
    },
    getMonthOrderList() {
      // getDashLabel({}, '/dashboard/getYearCheckedCount').then(data => {
      //   if (data.data.data.length > 0) {
      //     this.zhexianDataList = data.data.data
      //   }
      // })
    },
    changeAddress(val) {
      this.addressFlag = val
    },
    changeMonth(val) {
      this.dayMonthFlag = val
      this.zhexianLoading = true
      if (this.dayMonthFlag === 'day') {
        this.getDayList()
      } else {
        this.getMonthOrderList()
      }
      setTimeout(() => {
        this.$refs.zhexian.getEchartData1()
        this.zhexianLoading = false
      }, 1500)
    },
    zhexianRefreachVal() {
      this.zhexianLoading = true
      if (this.dayMonthFlag === 'day') {
        this.getDayList()
      } else {
        this.getMonthOrderList()
      }
      setTimeout(() => {
        this.$refs.zhexian.getEchartData1()
        this.zhexianLoading = false
      }, 1500)
    },
    refreachVal() {
      this.loading = true
      if (this.addressFlag === 'address') {
        this.$refs.addRess.getOrderList()
        setTimeout(() => {
          this.$refs.addRess.getOrderList()
          this.loading = false
        }, 1500)
      } else {
        this.$refs.tabL.getOrderList()
        setTimeout(() => {
          this.loading = false
        }, 1500)
      }
    },
    getDashLa(url, type) {
      const _this = this
      // getDashLabel({}, url).then(data => {
      //   if (data.data.data.length > 0) {
      //     if (type === 'jihuo') {
      //       _this.panelData = data.data.data
      //     } else if (type === 'jiaoyan') {
      //       _this.checkData = data.data.data
      //     } else if (type === 'canyu') { // 参与
      //       _this.joinData = data.data.data
      //     } else {
      //       _this.fakeData = data.data.data
      //     }
      //   }
      // })
    }
  }
}
</script>
<style lang="less" scoped>
.wrapper{
  height: calc(100vh - 200px);
  background: #fff;
  padding: 16px;
  border-radius: 4px;
}


.box_main{
  width: 100%;

  .left_box{
    width: 100%;
    height: 460px;
    display: flex;
    justify-content: space-between;
    .left_main_box{
      width: 400px;
      height: 100%;
    }
    .left_right_main_box{
      flex: 1;
      height: 100%;
      margin-left: 10px;
    }
  }
  .right_box{
    width: 100%;
    height: 330px;
    display: flex;
    justify-content: space-between;
    .right_left_main_box{
      width: 400px;
      height: 100%;
    }
    .right_left_box{
      flex: 1;
      width: 100%;
      margin-left: 10px;
    }
  }
}
.dashboard-editor-container {
  padding: 15px;
  background-color: rgb(240, 242, 245);

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

.panel-group {
  /*margin-top: 18px;*/

  .card-pana-col {
    margin-bottom: 10px;
  }

  .card-panel {
    height: 105px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    display: flex;
    justify-content: space-between;
    align-items: center;

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      font-weight: bold;
      margin-left: 16px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-top: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }


}

.tabChange {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: 40px;
  width: 100%;
  border-bottom: 1px solid #dcdfe6;

  .che {
    color: rgba(0, 0, 0, 0.45);
    transform: translateZ(1px);
    margin-left: 10px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 14px;
  }

  span {
    font-size: 14px;
    float: right;
    cursor: pointer;
    margin-left: 2px;
    height: 16px;
    width: 19px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    background: #ccc;

    &.act {
      background: #002B49;
    }

    &.addAct {
      background: rgb(54, 163, 247);
    }
  }
}

.tabs {
  width: 100%;

  background: #fff;
  padding: 0px 10px;
  box-sizing: border-box;
  position: relative;
}

.refreach {
  position: absolute;
  top: 10px;
  left: 10px;
  cursor: pointer;
}

.refreach1 {
  left: 13px;
}

.banEcharts {
  height: 100%;
  flex: 1;
  max-width: 60%;
  min-width: 60%;
}

.center_mess {
  /* margin: auto; */
  color: rgba(0, 0, 0, 0.45);
  position: absolute;
  left: 50%;
  right: 50%;
  top: 0px;
  width: 100%;
  text-align: center;
  margin-right: -50%;
  margin-left: -50%;
  transform: translateZ(-1px);
  font-size: 16px;
}

.mess_echart {
  position: absolute;
  bottom: 0px;
  left: 50%;
}

.tabContent {
  /*min-height: 400px;*/
  height: 100%;
  margin-bottom: 21px;
}
.tabContent_os{
  height: 290px;
}


</style>
