<template>
  <a-drawer
    width="800px"
    :title="title"
    placement="right"
    :visible="visible"
    @close="onClose"
  >
    <a-form :form="form" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="产品名称">
        <a-input
          placeholder="产品名称"
          :disabled="updateType === 'edit'"
          v-decorator="[
            'name',
            { rules: [{ required: true, message: '不能为空!' }] }
          ]" />
      </a-form-item>
      <a-form-item label="物料号（MNR）">
        <a-input
          placeholder="物料号（MNR）"
          :disabled="updateType === 'edit'"
          v-decorator="[
            'mnr',
            { rules: [{ required: true, message: '不能为空!' }] }
          ]" />
      </a-form-item>
      <a-form-item label="订单号（FD）">
        <a-input
          placeholder="订单号（FD）"
          :disabled="updateType === 'edit'"
          v-decorator="[
            'fd',
            { rules: [{ required: true, message: '不能为空!' }] }
          ]" />
      </a-form-item>
      <a-form-item label="序列号（SN）">
        <a-input
          placeholder="序列号（SN）"
          :disabled="updateType === 'edit'"
          v-decorator="[
            'sn',
            { rules: [{ required: true, message: '不能为空!' }] }
          ]" />
      </a-form-item>
      <a-form-item label="产品状态">
        <a-radio-group
          v-decorator="[
            'status',
            {initialValue:'0',
             rules: [{ required: true, message: '不能为空!' }] }
          ]">
          <a-radio :value="key" :key="key" v-for="(value,key) in statusList">
            {{ value }}
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="校验状态" v-if="updateType==='edit'">

        <a-radio-group
          v-decorator="[
            'verifyStatus',
            {initialValue:'0',
             rules: [{ required: true, message: '不能为空!' }] }
          ]">
          <a-radio :value="key" :key="key" v-for="(value,key) in verifyStatusList">
            {{ value }}
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="备注">
        <a-input
          type="textarea"
          placeholder="备注"
          v-decorator="[
            'remark',
          ]" />
      </a-form-item>
    </a-form>

    <div class="action">
      <a-button :style="{ marginRight: '8px' }" @click="onClose">
        取消
      </a-button>
      <a-button type="primary" @click="handleSubmit" :loading="submitLoading">
        提交
      </a-button>
    </div>
  </a-drawer>
</template>

<script>
import _ from 'lodash'

const labelCol = {
  span: 5
}
const wrapperCol = {
  span: 19
}

export default {
  props:{
    statusList:{
      type:Object,
      default:()=>{}
    },
    verifyStatusList:{
      type:Object,
      default:()=>{}
    },
  },
  data () {
    return {
      form: this.$form.createForm(this),
      submitLoading: false,
      visible: false,
      updateType:'add',
      record:{}
    }
  },

  computed: {
    title () {
      return this.updateType === 'edit' ? '编辑' : '新增'
    },
    labelCol () {
      return labelCol
    },
    wrapperCol () {
      return wrapperCol
    }
  },
  methods: {
    onClose () {
      this.form.resetFields()

      // this.$emit('change', false)
      this.visible = false
    },
    onOpen(updateType='add',record){
      this.updateType = updateType
      this.visible = true
      if(record){
        this.record = record
        this.$nextTick(() => {
          const columns = ['name', 'mnr', 'fd', 'sn', 'status', 'remark','verifyStatus']
          this.form.setFieldsValue(_.pick(record, columns))
        })
      }
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    onClassificationChange (value) {
      if (value === 'TTS物料') {
        this.showPlan = true
      }
      this.showPlan = false
    },
    async getAndUpdateForm () {
      const { data } = await this.$store.dispatch('ecnRule/getOne', this.id)
      const columns = ['materialCode', 'classification', 'plan']
      this.form.setFieldsValue(_.pick(data, columns))
    },
    async loadData () {
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFieldsAndScroll(async (err, values) => {
        if (err) {
          console.log('Received values of form: ', values)
          return
        }

        try {
          this.submitLoading = true

          if (this.updateType === 'edit') {
            await this.$store.dispatch('product/editProduct', {id:this.record.id,...values})
          } else {
            await this.$store.dispatch('product/addProduct', values)
          }

          this.$emit('on-ok')
          this.onClose()
        } catch (error) {
          console.log(error.code)
          console.log(error.message)

          this.$message.error(error.message)
        } finally {
          this.submitLoading = false
        }
      })
    }
  },

}
</script>

<style lang="less" scoped>
.action{
  position: absolute;
  right: 0;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e9e9e9;
  padding: 10px 16px;
  background: #fff;
  text-align: right;
  z-index: 1;
}
/deep/.ant-drawer-body{
  // overflow-y: auto;
  padding-bottom: 60px;
}

/deep/.ant-input-number{
  width: 100%;
}
</style>
