<template>
  <a-dropdown v-if="nickname" placement="bottomRight">
    <span class="ant-pro-account-avatar" style="display: flex;">
      <a-avatar size="small" :src="avatar" class="antd-pro-global-header-index-avatar" />
      <span>{{ nickname }}</span>
    </span>
    <template v-slot:overlay>
      <a-menu class="ant-pro-drop-down menu" :selected-keys="[]">
        <a-menu-item v-if="menu" key="settings" @click="handleToSettings">
          <a-icon type="setting" />
          个人设置
        </a-menu-item>
        <a-menu-divider v-if="menu" />
        <a-menu-item key="logout" @click="handleLogout">
          <a-icon type="logout" />
          退出登录
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
  <span v-else>
    <a-spin size="small" :style="{ marginLeft: 8, marginRight: 8 }" />
  </span>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { mapGetters } from 'vuex'

export default {
  name: 'AvatarDropdown',
  props: {
    menu: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    ...mapGetters([
      'avatar',
      'nickname'
    ])
  },
  methods: {
    // handleToCenter () {
    //   this.$router.push({ path: '/account/center' })
    // },
    handleToSettings () {
      this.$router.push({ path: '/account/settings' })
    },
    handleLogout (e) {
      Modal.confirm({
        title: '提示',
        content: '确定注销并退出系统吗？',
        onOk: () => {
          return this.$store.dispatch('Logout').then(() => {
            location.href = '/index'
          })
        },
        onCancel () {}
      })
    }
  }
}
</script>

<style lang="less" scoped>
.ant-pro-drop-down {
  /deep/ .action {
    margin-right: 8px;
  }
  /deep/ .ant-dropdown-menu-item {
    min-width: 160px;
  }
}

</style>
