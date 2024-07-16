<template>
  <div class="wrapper">
    <!-- table -->
    <div class="table-content">
      <a-form layout="inline" class="search-content">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-model-item label="物料号（MNR）">
              <a-input v-model="queryForm.mnr" placeholder="物料号（MNR）" allow-clear />
            </a-form-model-item>
          </a-col>
          <a-col :span="6">
            <a-form-model-item label="订单号（FD）">
              <a-input v-model="queryForm.fd" placeholder="订单号（FD）" allow-clear />
            </a-form-model-item>
          </a-col>
          <a-col :span="6">
            <a-form-model-item label="序列号（SN）">
              <a-input v-model="queryForm.sn" placeholder="序列号（SN）" allow-clear />
            </a-form-model-item>
          </a-col>

          <template v-if="advanced">
            <a-col :span="6">
              <a-form-item label="校验状态">
                <a-select allowClear show-search placeholder="" style="width:100%" v-model="queryForm.checkedResult">
                  <a-select-option v-for="(item, key) in statusTextMap" :key="key" :value="key">{{ item
                  }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-model-item label="省份">
                <a-input v-model="queryForm.province" placeholder="省份" allow-clear />
              </a-form-model-item>
            </a-col>
            <a-col :span="6">
              <a-form-model-item label="城市">
                <a-input v-model="queryForm.city" placeholder="城市" allow-clear />
              </a-form-model-item>
            </a-col>
            <!-- <a-col :span="6">
              <a-form-model-item label="手机号码">
                <a-input v-model="queryForm.telephone" placeholder="手机号码" allow-clear/>
              </a-form-model-item>
            </a-col> -->
            <a-col :span="6">
              <a-form-item label="校验时间">
                <a-range-picker format="YYYY-MM-DD" v-model="dateRange" @change="onDateRangeChange" />
              </a-form-item>
            </a-col>
          </template>

          <a-col span="4">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="handleSearch" :loading="searchLoading"><a-icon
                type="search" />查询</a-button>
              <a-button style="margin-left: 8px" @click="handleResetQuery"><a-icon type="redo" />重置</a-button>

              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <div class="action-content">
        <a-button type="primary" :loading="exportLoading" @click="handleDownload"><a-icon
          type="download" />导出结果</a-button>
      </div>
      <a-table
        :columns="columns"
        :data-source="list"
        :loading="tableLoading"
        rowKey="id"
        :pagination="false"
        size="middle"
        :scroll="tableScroll">
        <template slot="checkedResult" slot-scope="text">
          <a-tag :color="statusColorMap[text]">
            {{ statusTextMap[text] }}
          </a-tag>
        </template>

        <template slot="productId" slot-scope="text,reord">
          <a
            style="display:inline-block;color: #007bc0;width: 100%;height: 100%"
            @click="handleOpenProductDetail(reord.productId)">{{ text }}</a>
        </template>
      </a-table>

      <div class="pagination-con">
        <a-pagination
          show-size-changer
          :page-size-options="pageSizeOptions || [10, 20, 30, 40, 100, 150]"
          show-less-items
          :current="queryForm.pageNum"
          :page-size.sync="queryForm.pageSize"
          :total="paginationTotal"
          @showSizeChange="loadTableList"
          @change="changePagination" />
      </div>
    </div>

    <ProductDetail ref="productDetail"></ProductDetail>

  </div>
</template>

<script>
import { mixinTableList } from '@/utils/mixin/index'
import { download } from '@/utils/file'
import ProductDetail from './ProductDetail'


const columns = [
  {
    title: '产品ID',
    key: 'productId',
    dataIndex: 'productId',
    scopedSlots: { customRender: 'productId' },

    width: 80,
  },
  {
    title: '物料号（MNR）',
    key: 'mnr',
    dataIndex: 'mnr',
    width: 140,
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
  }
]

const statusTextMap = {
  0: '正常',
  1: '风险',
}

const statusColorMap = {
  0: '#87d068',
  1: '#f50',
}

const queryFormAttr = () => {
  return {
    mnr: '',
    fd: '',
    sn: '',
    checkedResult: '',
    params: {}
  }
}

export default {
  name: 'Area',
  mixins: [mixinTableList],
  components: {
    ProductDetail
  },
  data () {
    return {
      tableLoading: false,
      uploadLoading: false,
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
  computed: {
    statusTextMap: () => statusTextMap,
    statusColorMap: () => statusColorMap
  },
  methods: {
    handleOpenProductDetail (id) {
      console.log(111)
      this.$refs.productDetail.onOpen(id)
    },
    async handleDownload () {
      try {
        // this.exportLoading = true
        // this.queryForm.pageSize = 0
        // const blobData = await this.$store.dispatch('product/exportVerifyRecord', this.queryForm)
        // download(blobData, '标签打印记录.xlsx')
        this.exportLoading = true
        this.queryForm.pageSize = 0

        this.download('product/verify/export', {
          ...this.queryForm
        }, `扫码信息.xlsx`)
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
    this.loadData()
  }
}
</script>

<style lang="less" scoped></style>
