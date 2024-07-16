<template>
  <a-drawer
    width="85%"
    title="详情"
    placement="right"
    :visible="visible"
    @close="onClose"
  >
    <div class="wrapper">
      <!-- table -->

      <div class="table-content">
        <!--        <a-divider orientation="left">产品信息</a-divider>-->

        <a-form-model
          style="margin-left: 2px"
          layout="inline"
          
        >
         
          <a-form-model-item label="产品名称">
            <span style="font-weight: bold;">{{ record.name }}</span> 
          </a-form-model-item>
          <a-form-model-item label="物料号（MNR）">
            <span style="font-weight: bold;">{{ record.mnr }}</span> 
          </a-form-model-item>
          <a-form-model-item label="订单号（FD）">

            <span style="font-weight: bold;">{{ record.fd }}</span> 
          </a-form-model-item>
          <a-form-model-item label="序列号（SN）">
            <span style="font-weight: bold;">{{ record.sn }}</span> 
          </a-form-model-item>
          <a-form-model-item label="产品状态">
            <a-tag :color="statusColorMap[record.status]">
              {{ statusTextMap[record.status] }}
            </a-tag>
          </a-form-model-item>
          <a-form-model-item label="校验状态">
            <a-tag :color="verifyStatusColorMap[record.verifyStatus]">
              {{ verifyStatusTextMap[record.verifyStatus] }}
            </a-tag>
          </a-form-model-item>
           
        </a-form-model>

        <!--        <p style="font-size: 16px;margin: 4px 0 0 0;">校验记录：</p>-->
        <a-divider orientation="left">校验记录</a-divider>
        <!--        <div class="action-content">-->
        <!--          <a-button type="primary" :loading="exportLoading" @click="handleDownload"><a-icon type="download" />导出结果</a-button>-->
        <!--        </div>-->
        <a-table
          :columns="columns"
          :data-source="list"
          :loading="tableLoading"
          rowKey="id"
          :pagination="false"
          size="middle"
          :scroll="tableScroll"
        >
          <template slot="checkedResult" slot-scope="text">
            <a-tag :color="checkedResultStatusColorMap[text]">
              {{ checkedResultStatusTextMap[text] }}
            </a-tag>
          </template>
        </a-table>

        <div class="pagination-con">
          <a-pagination
            show-size-changer
            :page-size-options="pageSizeOptions||[10,20,30,40,100,150]"
            show-less-items
            :current="queryForm.pageNum"
            :page-size.sync="queryForm.pageSize"
            :total="paginationTotal"
            @showSizeChange="loadTableList"
            @change="changePagination"
          />
        </div>
      </div>

    </div>
  </a-drawer>
</template>

<script>
import { mixinTableList } from '@/utils/mixin/index'
import { download } from '@/utils/file'


const columns = [
  // {
  //   title: '物料号（MNR）',
  //   key: 'mnr',
  //   dataIndex: 'mnr',
  //   width:140,
  // },
  // {
  //   title: '订单号（FD）',
  //   key: 'fd',
  //   dataIndex: 'fd',
  // },
  // {
  //   title: '序列号（SN）',
  //   key: 'sn',
  //   dataIndex: 'sn',
  // },
  {
    title: '省份',
    key: 'province',
    dataIndex: 'province',
  },
  {
    title: '城市',
    key: 'city',
    dataIndex: 'city',
  },
  // {
  //   title: '手机号码',
  //   key: 'telephone',
  //   dataIndex: 'telephone',
  // },
  {
    title: '手机型号',
    key: 'userPhoneOS',
    dataIndex: 'userPhoneOS',
  },
  // {
  //   title: '微信用户名',
  //   key: 'userName',
  //   dataIndex: 'userName',
  // },
  {
    title: '微信用户ID',
    key: 'appId',
    dataIndex: 'appId',
  },
  {
    title: '校验结果',
    key: 'checkedResult',
    dataIndex: 'checkedResult',
    scopedSlots: { customRender: 'checkedResult' },
  },
  
  {
    title: '校验时间',
    key: 'checkedTime',
    dataIndex: 'checkedTime',
  },

  {
    title: '备注',
    key: 'remark',
    dataIndex: 'remark',
  },
]

const checkedResultStatusTextMap = {
  0:'正常',
  1:'风险',
}

const checkedResultStatusColorMap = {
  0:'#87d068',
  1:'#f50',
}

const statusTextMap = {
  0:'启用',
  1:'停用',
}

const statusColorMap = {
  0:'#87d068',
  1:'#f50',
}

const verifyStatusTextMap = {
  0:'正常',
  1:'风险',
  2:'未校验'
}

const verifyStatusColorMap = {
  0:'#87d068',
  1:'#f50',
  2:'#002B49'
}


const queryFormAttr = () => {
  return {
    mnr:'',
    fd:'',
    sn:'',
    checkedResult:'',
    productId:'',
    params:{}
  }
}

export default {
  name: 'Area',
  mixins: [mixinTableList],
  data () {
    return {
      tableLoading: false,
      uploadLoading: false,
      exportLoading: false,
      visible:false,
      queryForm: {
        pageSize: 20,
        pageNum: 1,
        ...queryFormAttr()
      },
      columns,
      list: [],
      record:{}
    }
  },
  computed:{
    checkedResultStatusTextMap:()=>checkedResultStatusTextMap,
    checkedResultStatusColorMap:()=>checkedResultStatusColorMap,
    statusTextMap:()=>statusTextMap,
    statusColorMap:()=>statusColorMap,
    verifyStatusTextMap:()=>verifyStatusTextMap,
    verifyStatusColorMap:()=>verifyStatusColorMap
  },
  methods: {
    onClose(){
      this.visible = false
    },
    async onOpen(id){
      const {data} = await this.$store.dispatch('product/getProductInfo',id)
      this.record = data
      this.visible = true
      this.queryForm.productId = id

      this.loadTableList()
    },
    async handleDownload () {
      try {
        this.exportLoading = true
        this.queryForm.pageSize = 0

        this.download('product/verify/export', {
            ...this.queryForm
          }, `标签打印记录.xlsx`)
      } catch (error) {
        console.log(error)
        this.$message.error(error.message)
      } finally {
        this.exportLoading = false
      }
    },
    handleResetQuery () {
      this.dateRange = []
      this.queryForm = { ...this.queryForm, ...queryFormAttr() }
      this.handleSearch()
    },
    async loadTableList () {
      try {
        this.tableLoading = true

        const {
           rows, total 
        } = await this.$store.dispatch('product/getVerifyRecordList', this.queryForm)
        this.list = rows
        this.paginationTotal = total
      } catch (error) {
        this.$message.error(error.message)
      } finally {
        this.tableLoading = false
      }
    },
    async loadData () {
      this.loadTableList()
    }
  },
  mounted () {
  }
}
</script>

<style lang="less" scoped>
.ant-divider-horizontal.ant-divider-with-text-left::before{
  width: 0px !important;
}

/deep/.ant-divider-inner-text{
  padding: 0px 10px 0px 0px !important;
  font-size: 16px;
}
</style>
