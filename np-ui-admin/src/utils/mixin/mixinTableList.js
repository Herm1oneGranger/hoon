import moment from 'moment'

const mixinTableList = {
  data () {
    return {
      visible: false,
      updateType: 'add', // edit、add
      currentUpdateId: 0,

      // 高级搜索 展开/关闭
      advanced: false,

      searchLoading: false,
      queryForm: {
        pageSize: 300,
        pageNum: 1
      },
      paginationTotal: 0,

      tableLoading: false,
      pageSizeOptions: ['10', '20', '30', '40', '50', '100', '300', '500'],

      dateRange:[]
    }
  },
  computed: {
    tableScroll: () => ({ x: 1300, y: 700 }),
    startDateFormat () {
      return 'YYYY-MM-DD HH:MM:00'
    },
    endDateFormat () {
      return 'YYYY-MM-DD HH:MM:00'
    }
  },
  methods: {
    onDateRangeChange(dateRange){
      const beginTime = moment(dateRange[0]).format(this.startDateFormat)
      const endTime = moment(dateRange[1]).format(this.endDateFormat)

      this.queryForm.params['beginTime'] = beginTime
			this.queryForm.params['endTime'] = endTime
    },
    onShowSizeChange () {
      this.queryForm.pageNum = 1
      this.loadTableList()
    },
    changePagination (page) {
      this.queryForm.pageNum = page
      this.loadTableList()
    },
    async handleSearch () {
      this.searchLoading = true
      this.selectedRowKeys = []
      this.selectedRows = []
      await this.loadTableList()
      this.searchLoading = false
    },


    handleEdit (record) {
      this.updateType = 'edit'
      this.visible = true
      this.currentUpdateId = record.id
    },
    handleAdd () {
      this.updateType = 'add'
      this.visible = true
      this.currentUpdateId = null
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    }
  }
}

export { mixinTableList }
