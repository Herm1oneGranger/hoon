<template>
  <div class="wrapper">
    <!-- table -->
    <div class="table-content">
      <a-form layout="inline" class="search-content">
        <a-row :gutter="16">
          <a-col :span="6">
            <a-form-model-item label="文件名">
              <a-input v-model="queryForm.name" placeholder="文件名" allow-clear/>
            </a-form-model-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="文件导入状态">
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
          <a-col :span="8">
            <a-form-item label="创建时间" >
              <a-range-picker
                format="YYYY-MM-DD"
                v-model="dateRange"
                @change="onDateRangeChange"
              />
            </a-form-item>
          </a-col>
          <a-col span="4">
            <span class="table-page-search-submitButtons" >
              <a-button type="primary" @click="handleSearch" :loading="searchLoading"><a-icon type="search" />查询</a-button>
              <a-button style="margin-left: 8px" @click="handleResetQuery"><a-icon type="redo" />重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <div class="action-content">
        <a-upload
          :file-list="[]"
          name="file"
          :multiple="true"
          :before-upload="()=>false"
          @change="handleUpload"
        >
          <a-button :loading="uploadLoading" type="primary" icon="upload" >
            导入
          </a-button>
        </a-upload>

        <a-button type="primary" style="margin-left: 8px" :loading="exportLoading" @click="handleDownload"><a-icon type="download" />导出结果</a-button>
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

        <template slot="xml" slot-scope="text">
          <a-tag @click="handleOpenXml(text)">
            查看xml
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

    <XML ref="xml"></XML>

  </div>
</template>

<script>
import { mixinTableList } from '@/utils/mixin/index'
import { download } from '@/utils/file'
import XML from '@/components/XML'


const columns = [
{
    title: '编号',
    key: 'id',
    dataIndex: 'id',
  },
  {
    title: '文件名',
    key: 'name',
    dataIndex: 'name',
  },
  {
    title: '标签数量',
    key: 'printNumber',
    dataIndex: 'printNumber',
  },
  {
    title: '文件导入状态',
    key: 'status',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' },
  },
  {
    title: 'xml',
    key: 'xml',
    dataIndex: 'xml',
    scopedSlots: { customRender: 'xml' },
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
]

const statusTextMap = {
  0:'正常',
  1:'失败',
}

const statusColorMap = {
  0:'#87d068',
  1:'#f50',
}

const queryFormAttr = () => {
  return {
    status:'',
    name:'',
    params:{}
  }
}

export default {
  name: 'Area',
  mixins: [mixinTableList],
  components:{
    XML
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
  computed:{
    statusTextMap:()=>statusTextMap,
    statusColorMap:()=>statusColorMap
  },
  methods: {
    handleOpenXml(xml){
      this.$refs.xml.onOpen(xml)
    },
    async handleUpload (e) {
      const { file } = e

      const formdata = new FormData()

      try {
        formdata.append('file', file)

        this.uploadLoading = true
        await this.$store.dispatch('product/uploadPrintLableFile', formdata)

        this.queryForm.pageNum = 1
        this.loadTableList()

        this.uploadLoading = false

        this.$message.success('导入成功！')
      } catch (error) {
        if (error.code === 400) {
          this.$confirm({
            title: '是否更新？',
            content: '存在重复数据',
            onOk: () => {
              this.uploadBatchUpdate(formdata)
            },
            onCancel: () => {
              this.uploadLoading = false
            }
          })
        } else {
          this.$message.error(error.message)
          this.uploadLoading = false
        }
      }
    },
    async handleDownload () {
      try {
        this.exportLoading = true
        this.queryForm.pageSize = 0

        this.download('product/print/export', {
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
        } = await this.$store.dispatch('product/getPrintLabelList', this.queryForm)
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
