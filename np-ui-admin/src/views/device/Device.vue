<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-row :gutter="24">
        <a-col :span="24">
          <!-- 条件搜索 -->
          <div class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :span="4">
                  <a-form-item label="名称">
                    <a-input v-model="queryParam.userName" placeholder="请输入" allow-clear />
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item label="型号" >
                    <a-input v-model="queryParam.phonenumber" placeholder="请输入" allow-clear />
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item label="生产厂家" >
                    <a-input v-model="queryParam.phonenumber" placeholder="请输入" allow-clear />
                  </a-form-item>
                </a-col>
                <template v-if="advanced">
                  <a-col :md="8" :sm="24">
                    <a-form-item label="购置日期">
                      <a-range-picker style="width: 100%" v-model="dateRange" valueFormat="YYYY-MM-DD" format="YYYY-MM-DD" allow-clear />
                    </a-form-item>
                  </a-col>
                </template>
                <a-col :md="!advanced && 8 || 24" :sm="24">
                  <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                    <a-button type="primary" @click="handleQuery"><a-icon type="search" />查询</a-button>
                    <a-button style="margin-left: 8px" @click="resetQuery"><a-icon type="redo" />重置</a-button>
                    <a @click="toggleAdvanced" style="margin-left: 8px">
                      {{ advanced ? '收起' : '展开' }}
                      <a-icon :type="advanced ? 'up' : 'down'"/>
                    </a>
                  </span>
                </a-col>
              </a-row>
            </a-form>
          </div>
          <!-- 操作 -->
          <div class="table-operations">
            <a-button type="primary" @click="$refs.createForm.handleAdd()" v-hasPermi="['system:user:add']">
              <a-icon type="plus" />新增
            </a-button>
            <table-setting
              :style="{float: 'right'}"
              :table-size.sync="tableSize"
              v-model="columns"
              :refresh-loading="loading"
              @refresh="getList" />
          </div>
         
          <!-- 数据展示 -->
          <a-table
            :loading="loading"
            :size="tableSize"
            rowKey="userId"
            :columns="columns"
            :data-source="list"
            :pagination="false"
            :bordered="tableBordered">
            <span slot="status" slot-scope="text, record">
              <a-popconfirm
                ok-text="是"
                cancel-text="否"
                @confirm="confirmHandleStatus(record)"
                @cancel="cancelHandleStatus(record)"
              >
                <span slot="title">确认<b>{{ record.status === '1' ? '启用' : '停用' }}</b>{{ record.nickName }}的用户吗?</span>
                <a-switch checked-children="开" un-checked-children="关" :checked="record.status == 0" />
              </a-popconfirm>
            </span>
            <span slot="createTime" slot-scope="text, record">
              {{ parseTime(record.createTime) }}
            </span>
            <span slot="operation" slot-scope="text, record" v-if="record.userId !== 1">
              <a @click="$refs.createForm.handleUpdate(record,undefined)" v-hasPermi="['system:user:edit']">
                <a-icon type="edit" />
                修改
              </a>
              <a-divider type="vertical" v-hasPermi="['system:user:remove']" />
              <a @click="handleDelete(record)" v-hasPermi="['system:user:remove']">
                <a-icon type="delete" />
                删除
              </a>
            </span>
          </a-table>
          <!-- 分页 -->
          <a-pagination
            class="ant-table-pagination"
            show-size-changer
            show-quick-jumper
            :current="queryParam.pageNum"
            :total="total"
            :page-size="queryParam.pageSize"
            :showTotal="total => `共 ${total} 条`"
            @showSizeChange="onShowSizeChange"
            @change="changeSize"
          />
        </a-col>
      </a-row>
    </a-card>
  </page-header-wrapper>
</template>

<script>

import { listUser, delUser } from '@/api/system/user'
import { tableMixin } from '@/store/table-mixin'

const columns = [
  {
    title: '名称',
    dataIndex: 'userId',
    align: 'center'
  },
  {
    title: '编号',
    dataIndex: 'userId',
    align: 'center'
  },
  {
    title: '型号',
    dataIndex: 'userName',
    align: 'center'
  },
  {
    title: '生产厂商',
    dataIndex: 'nickName',
    align: 'center'
  },
  {
    title: '供应厂商',
    dataIndex: 'nickName',
    align: 'center'
  },
  {
    title: '购置日期',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '使用日期',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '安装地点',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '出场编号',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '技术参数',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '购置费用',
    dataIndex: 'phonenumber',
    align: 'center'
  },
  {
    title: '保修日期',
    dataIndex: 'd',
    align: 'center'
  },
  {
    title: '状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' },
    align: 'center'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    dataIndex: 'operation',
    scopedSlots: { customRender: 'operation' },
    align: 'center'
  }
]

export default {
  name: 'Device',
  components: {
  },
  mixins: [tableMixin],
  data () {
    return {
      list: [],
      // 高级搜索 展开/关闭
      advanced: false,
      loading: false,
      total: 0,
    
      queryParam: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        dateRange:[]
      },
      columns
    }
  },
  filters: {
  },
  created () {
    this.getList()
  },
  computed: {
  },
  watch: {
  },
  methods: {
    /** 查询用户列表 */
    getList () {
      // this.loading = true
      // listUser(this.addDateRange(this.queryParam, this.dateRange)).then(response => {
      //     this.list = response.rows
      //     this.total = response.total
      //     this.loading = false
      //   }
      // )
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParam.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.dateRange = []
      this.queryParam = {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      }
      this.handleQuery()
    },
 
    /** 删除按钮操作 */
    handleDelete (row) {
      var that = this
      const userIds = row.userId || this.ids
      this.$confirm({
        title: '确认删除所选中数据?',
        content: '当前选中编号为' + userIds + '的数据',
        onOk () {
          return delUser(userIds)
            .then(() => {
              that.onSelectChange([], [])
              that.getList()
              that.$message.success(
                '删除成功',
                3
              )
          })
        },
        onCancel () {}
      })
    },
  }
}
</script>
