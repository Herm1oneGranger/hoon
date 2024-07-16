<template>
  <div class="wrapper">
    <!-- table -->
    <div class="table-content">
      <a-form layout="inline" class="search-content">
        <a-row :gutter="16">
        
          <a-col :span="6">
            <a-form-model-item label="物料号（MNR）">
              <a-input v-model="queryForm.mnr" placeholder="物料号（MNR）" allow-clear/>
            </a-form-model-item>
          </a-col>
          <a-col :span="6">
            <a-form-model-item label="订单号（FD）">
              <a-input v-model="queryForm.fd" placeholder="订单号（FD）" allow-clear/>
            </a-form-model-item>
          </a-col>
          <a-col :span="6">
            <a-form-model-item label="序列号（SN）">
              <a-input v-model="queryForm.sn" placeholder="序列号（SN）" allow-clear/>
            </a-form-model-item>
          </a-col>

          <template v-if="advanced">
            <a-col :span="6">
              <a-form-model-item label="产品名称">
                <a-input v-model="queryForm.name" placeholder="产品名称" allow-clear/>
              </a-form-model-item>
            </a-col>
            <a-col :span="6">
              <a-form-model-item label="打印编号">
                <a-input v-model="queryForm.printId" placeholder="打印编号" allow-clear/>
              </a-form-model-item>
            </a-col>
            <a-col :span="6">
              <a-form-item label="产品状态">
                <a-select
                  allowClear
                  show-search
                  placeholder=""
                  style="width:100%"
                  v-model="queryForm.status"
                >
                  <a-select-option v-for="(item,key) in statusTextMap" :key="key" :value="key">{{ item }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item label="校验状态">
                <a-select
                  show-search
                  placeholder=""
                  style="width:100%"
                  v-model="queryForm.verifyStatus"
                >
                  <a-select-option v-for="(item,key) in verifyStatusTextMap" :key="key" :value="key">{{ item }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item label="创建时间" >
                <a-range-picker
                  format="YYYY-MM-DD"
                  v-model="dateRange"
                  @change="onDateRangeChange"
                />
              </a-form-item>
            </a-col>
          </template>
    
          <a-col span="4">
            <span class="table-page-search-submitButtons" >
              <a-button type="primary" @click="handleSearch" :loading="searchLoading"><a-icon type="search" />查询</a-button>
              <a-button style="margin-left: 8px" @click="handleResetQuery"><a-icon type="redo" />重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <div class="action-content">
        <!-- v-hasPermi="['ecn:sample:rule:add']"  -->
        <a-button type="primary" class="m-r-8" icon="plus" @click="handleAdd">
          新建
        </a-button>

        <a-button type="primary" :loading="exportLoading" @click="handleDownload"><a-icon type="download" />导出结果</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="list"
        :loading="tableLoading"
        rowKey="id"
        :pagination="false"
        size="middle"
        :scroll="tableScroll"
      >
        <template slot="status" slot-scope="text">
          <a-tag :color="statusColorMap[text]">
            {{ statusTextMap[text] }}
          </a-tag>
        </template>

        <template slot="printId" slot-scope="text">
          {{ text?text:'手动添加' }}
        </template>
        
        <template slot="verifyStatus" slot-scope="text">
          <a-tag :color="verifyStatusColorMap[text]">
            {{ verifyStatusTextMap[text] }}
          </a-tag>
        </template>

        <template slot="action" slot-scope="text, record">
          <div class="action-con">
            <!-- v-hasPermi="['ecn:sample:rule:edit']" -->
            <a @click="handleEdit(record)"><a-icon class="m-r-4" type="edit" />编辑</a>
            <a-divider type="vertical" />
            <a @click="handleOpenDetail(record)"><a-icon class="m-r-4" type="search" />详情</a>
            
            <!-- <a-divider type="vertical" />
            <a-popconfirm
              title="确认要删除吗?"
              ok-text="确认"
              cancel-text="取消"
              @confirm="handleDelete(record)"
            >
              <a v-hasPermi="['ecn:sample:rule:delete']" class="danger-color"><a-icon class="m-r-4" type="delete" />删除</a>
            </a-popconfirm> -->
          </div>
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

    <ProductUpdateDrawer :verifyStatusList="verifyStatusTextMap" :statusList="statusTextMap" ref="updateDrawer" @on-ok="loadTableList"></ProductUpdateDrawer>

    <ProductDetail 
      :statusTextMap="statusTextMap" 
      :statusColorMap="statusColorMap" 
      :verifyStatusTextMap="verifyStatusTextMap"
      :verifyStatusColorMap="verifyStatusColorMap"
      ref="productDetail"></ProductDetail>
    
  </div>
</template>

<script>
import { mixinTableList } from '@/utils/mixin/index'
import { download } from '@/utils/file'
import ProductUpdateDrawer from './ProductUpdateDrawer'
import ProductDetail from './ProductDetail'

const columns = [
{
    title: '打印编号',
    key: 'printId',
    dataIndex: 'printId',
    scopedSlots: { customRender: 'printId' },
    width:80
  },
  // {
  //   title: 'ID',
  //   key: 'id',
  //   dataIndex: 'id',
  //   width:40,
  // },
  {
    title: '产品名称',
    key: 'name',
    dataIndex: 'name',
  },
  {
    title: '物料号（MNR）',
    key: 'mnr',
    dataIndex: 'mnr',
    width:140
  },
  {
    title: '订单号（FD）',
    key: 'fd',
    dataIndex: 'fd',
  },
  {
    title: '序列号（SN）',
    key: 'sn',
    dataIndex: 'sn',
  },
  {
    title: '产品状态',
    key: 'status',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' },
  },
  {
    title: '扫码状态',
    key: 'verifyStatus',
    dataIndex: 'verifyStatus',
    scopedSlots: { customRender: 'verifyStatus' },
  },

  {
    title: '备注',
    key: 'remark',
    dataIndex: 'remark',
  },
  {
    title: '创建人',
    key: 'createBy',
    dataIndex: 'createBy',
  },
  {
    title: '创建时间',
    key: 'createTime',
    dataIndex: 'createTime',
  },
  {
    title: '更新人',
    key: 'updateBy',
    dataIndex: 'updateBy',
  },
  {
    title: '更新时间',
    key: 'updateTime',
    dataIndex: 'updateTime',
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 140,
    scopedSlots: { customRender: 'action' }
  }
]

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
    status:'',
    name:'',
    mnr:'',
    fd:'',
    sn:'',
    printId:'',
    verifyStatus:'',
    params:{}
  }
}

export default {
  name: 'Area',
  components:{
    ProductUpdateDrawer,
    ProductDetail
  },
  mixins: [mixinTableList],
  data () {
    return {
      tableLoading: false,
      exportLoading: false,
      queryForm: {
        pageSize: 20,
        pageNum: 1,
        ...queryFormAttr()
      },
      columns,
      list: [],
    }
  },
  computed:{
    statusTextMap:()=>statusTextMap,
    statusColorMap:()=>statusColorMap,
    verifyStatusTextMap:()=>verifyStatusTextMap,
    verifyStatusColorMap:()=>verifyStatusColorMap
  },
  methods: {
    handleAdd(){
      this.$refs.updateDrawer.onOpen()
    },
    handleOpenDetail(record){
      this.$refs.productDetail.onOpen(record.id)
    },
    handleEdit(record){
      this.$refs.updateDrawer.onOpen('edit',record)
    },
    async handleDownload () {
      try {
        // this.exportLoading = true
        // // this.queryForm.pageSize = 0
        // const blobData = await this.$store.dispatch('product/exportProduct', this.queryForm)
        // download(blobData, '产品列表.xlsx')

        this.exportLoading = true
        this.queryForm.pageSize = 0

        this.download('product/export', {
            ...this.queryForm
        }, `产品列表.xlsx`)

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
        } = await this.$store.dispatch('product/getProductList', this.queryForm)
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
    this.loadData()
  }
}
</script>

<style lang="less" scoped>
</style>
