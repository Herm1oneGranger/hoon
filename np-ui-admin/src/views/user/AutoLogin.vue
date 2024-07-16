<template>
  <div>自动登陆中....</div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  methods: {
    ...mapActions(['Login']),
    async login () {
      const query = this.$route.query
      await this.Login({
        password: query.password,
        rememberMe: false,
        username: query.username
      })
      this.$router.push({ path: '/print-label' })
    },
  },
  mounted () {
    this.login()
  }
}
</script>