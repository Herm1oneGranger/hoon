<template>
  <div id="userLayout" :class="['user-layout-wrapper']">
    <div class="container">
      <div class="black-model">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>

export default {

  name: 'UserLayout',

  mounted () {
    document.body.classList.add('userLayout')
  },

  beforeDestroy () {
    document.body.classList.remove('userLayout')
  }

}

</script>

<style lang="less" scoped>

#userLayout.user-layout-wrapper {
    height: 100%;
    .container {
      width: 100%;
      height: 100%;
      // background: #f0f2f5 url(~@/assets/background.jpg) no-repeat 50%;
      background-size: 100%;
      // padding: 110px 0 144px;
      position: relative;
    }
    .black-model{
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.2);
    }
  }
</style>
