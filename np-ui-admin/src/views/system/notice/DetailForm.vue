<template>
  <a-drawer width="30%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>详情</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="标题" >
        {{ form.noticeTitle }}
      </a-form-model-item>
      <a-form-model-item label="内容" >
        {{ form.noticeContent }}
      </a-form-model-item>
      <a-form-model-item label="状态" prop="status">
        {{ form.status ==='0'?'正常':'关闭' }}
      </a-form-model-item>
      <a-form-model-item label="备注" >
        {{ form.remark }}
      </a-form-model-item>
    </a-form-model>
  </a-drawer>
</template>

<script>

import { getNotice, updateNotice } from '@/api/system/notice'
import Editor from '@/components/Editor'

export default {
  name: 'NoticeForm',
  components: {
    Editor
  },
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data () {
    return {
      labelCol: { span: 4 },
      wrapperCol: { span: 24 },
      submitLoading: false,
      total: 0,
      id: undefined,
      formTitle: '',
      // 表单参数
      form: {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: '',
        status: '0',
        remark:''
      },
      baseRules: {
        noticeTitle: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }]
      },
      rules: {
        noticeType: [{ required: true, message: '公告类型不能为空', trigger: 'change' }]
      },
      open: false
    }
  },
  filters: {
  },
  created () {
  },
  computed: {
  },
  watch: {
  },

  methods: {
    onOpen(id){
      this.handleUpdate(id)
    },
    // 表单重置
    reset () {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: '',
        remark:'',
        status: '0'
      }
    },
     /** 新增按钮操作 */
    handleAdd () {
      this.reset()
    },
    /** 修改按钮操作 */
    handleUpdate (id) {
      this.reset()
      const noticeId = id
      getNotice(noticeId).then(response => {
        this.form = response.data
        this.open = true
      })
    },
    onClose () {
      this.open = false
    },

  }
}
</script>

<style lang="less" scoped>
/deep/.ant-form-item-children{
  font-size: 12px;
}

/deep/.ant-form-item-control{
  line-height: 16px;
}

/deep/.ant-form-item{
  margin-bottom: 20px;
}
</style>
