<template>
  <div class="tabList">
    <div class="tabChange">
      <a-input
        style="width: 200px"
        v-model="search"
        size="mini"
        placeholder="输入城市名称搜索"/>
    </div>
    <a-table
      :pagination="false"
      rowKey="province"
      :scroll="{ y: 200 }"
      size="small"
      :data-source="tableData"
      :columns="columns">
    </a-table>
  </div>
</template>

<script>
  // import { getOrderAddress } from '@/api/dashboard'
  import _ from 'lodash'

  export default {
    data() {
      return {
        search:'',
        tableData: [],
        list:[],
        columns: [
          {
            title: '省份',
            dataIndex: 'province',
            key: 'province',
          },
          {
            title: '城市',
            dataIndex: 'city',
            key: 'city',
          },
          {
            title: '校验数量',
            dataIndex: 'number',
            key: 'number',
          },
        ],
        }
    },

    watch:{
      search(val){
        if(!val){
          this.tableData = this.list
        }else{
          this.tableData = _.filter(this.list,x=>x.city.indexOf(val)>-1)
        }
      },  
    },
    created(){
      this.getList()
    },
    methods:{
      handleSearch(selectedKeys,) {
      
      this.searchText = selectedKeys[0]
    },

    handleReset(clearFilters) {
      clearFilters()
      this.searchText = ''
    },
      async getList(){
        const {data} = await this.$store.dispatch('dashboard/getAddressData')
        this.tableData = data
        this.list = data
      }
    }
  }
</script>

<style scoped lang="less">
  .tabList{
    width: 100%;
    height: 330px;
  }
  .tabrt{
    display: flex;
    p{
      font-size: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-left: 10px;
      height: 20px;
      color: rgba(0, 0, 0, 0.45);
      cursor: pointer;
    }
    span{
      display: inline-block;
      cursor: pointer;
      height: 13px;
      width: 15px;
      -webkit-border-radius: 4px;
      -moz-border-radius: 4px;
      border-radius: 4px;
      background: #ccc;
      margin-left: 2px;
      &.act{
        background: rgb(54,163,247);
      }
    }
  }
  .tabChange{
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 50px;
    width: 100%;
    border-bottom: 1px solid #dcdfe6;

  }
  .tabContent{
    min-height: 350px;
    max-height: 350px;
    background: #fff;
    overflow-y: auto;
    &::-webkit-scrollbar{ //先改变body的滚动条宽度
      width: 3px;
    }

    &::-webkit-scrollbar-track{//再改变body的滚动条轨道颜色
      background: rgb(200, 200, 200);
      border-radius: 5px;
    }
    &::-webkit-scrollbar-thumb{//最后改变body的滚动条滑轨相关的样式
      background: rgb(54,163,247);
      border-radius: 5px;
    }
  }
</style>
