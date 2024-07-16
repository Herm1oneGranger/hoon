<template>
  <a-drawer width="30%" :label-col="4" :wrapper-col="14" :visible="open" @close="onClose">
    <a-divider orientation="left">
      <b>修改公告</b>
    </a-divider>
    <a-form-model ref="form" :model="form" :rules="rules">
      <a-form-model-item label="标题" >
        <a-input disabled v-model="form.noticeTitle" ></a-input>
      </a-form-model-item>
      <a-form-model-item label="内容" >
        <a-input disabled v-model="form.noticeContent" type="textarea"></a-input>
      </a-form-model-item>
      <a-form-model-item label="状态" prop="status">
        <a-radio-group v-model="form.status" button-style="solid">
          <a-radio-button v-for="(d, index) in dict.type['sys_notice_status']" :key="index" :value="d.value" >{{ d.label }}</a-radio-button>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="备注" >
        <a-input v-model="form.remark" type="textarea"></a-input>
      </a-form-model-item>

      <div class="bottom-control">
        <a-space>
          <a-button type="primary" :loading="submitLoading" @click="submitForm">
            保存
          </a-button>
          <a-button type="dashed" @click="onClose">
            取消
          </a-button>
        </a-space>
      </div>
    </a-form-model>
  </a-drawer>
</template>

<script>

import { getNotice, addNotice, updateNotice } from '@/api/system/notice'
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
    /** 提交按钮 */
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true
            updateNotice(this.form).then(response => {
              this.$message.success(
                '修改成功',
                3
              )
              this.onClose()
              this.$emit('on-ok')
            }).finally(() => {
              this.submitLoading = false
            })
        } else {
          return false
        }
      })
    },
    onClose () {
      this.open = false
    },
    handleSubmit () {
      this.$refs.baseForm.validate(valid => {
        if (valid) {
          this.open = true
        } else {
          return false
        }
      })
    }
  }
}
</script>
