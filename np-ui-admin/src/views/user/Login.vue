<template>
  <div class="wrapper">
    <div class="main">
      <div class="image-main">
        <img src="@/assets/images/home.png" alt="">
      </div>
      <div class="form-main">
        <div class="form-content">
          <div class="header">
            <img src="@/assets/logo.gif" alt="">
            <h3>铭牌防伪验证</h3>
          </div>
          <a-form-model
            layout="vertical"
            id="formLogin"
            ref="form"
            class="user-layout-login"
            :model="form"
            :rules="rules">
            <a-alert
              v-if="isLoginError"
              type="error"
              showIcon
              style="margin-bottom: 24px;"
              :message="loginErrorInfo"
              closable
              :after-close="handleCloseLoginError"
            />
            <a-form-model-item prop="username" label="账户：">
              <a-input v-model="form.username" size="large" placeholder="账户:" >
                <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-model-item>
            <a-form-model-item prop="password" label="密码">
              <a-input-password v-model="form.password" size="large" placeholder="密码:">
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input-password>
            </a-form-model-item>
            <a-form-model-item prop="rememberMe">
              <a-checkbox :checked="form.rememberMe" @change="rememberMe">记住密码</a-checkbox>
            </a-form-model-item>
            <a-form-item style="margin-top:24px">
              <a-button
                size="large"
                type="primary"
                htmlType="submit"
                class="login-button"
                :loading="logining"
                :disabled="logining"
                @click="handleSubmit"
              >确定</a-button>
            </a-form-item>
          </a-form-model>
        </div>

      </div>
    </div>

  </div>

</template>
<script>
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'

import { LOGIN_USERNAME, LOGIN_PASSWORD, LOGIN_REMEMBERME } from '@/store/mutation-types'
import storage from 'store'

export default {
  components: {
  },
  data () {
    return {
      codeUrl: '',
      isLoginError: false,
      loginErrorInfo: '',
      form: {
        username: '',
        password: '',
        code: undefined,
        uuid: '',
        rememberMe: false
      },
      rules: {
        username: [{ required: true, message: '请输入帐户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      logining: false,
      captchaEnabled: true
    }
  },
  created () {
    this.getStorage()
    // this.getCode()
  },
  methods: {
    // getCode () {
    //   getCodeImg().then(res => {
    //     this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
    //     if (this.captchaEnabled) {
    //       this.codeUrl = 'data:image/gif;base64,' + res.img
    //       this.form.uuid = res.uuid
    //     }
    //   })
    // },
    getStorage () {
      const username = storage.get(LOGIN_USERNAME)
      const password = storage.get(LOGIN_PASSWORD)
      const rememberMe = storage.get(LOGIN_REMEMBERME)
      if (username) {
        this.form = {
          username: username,
          password: password,
          rememberMe: rememberMe
        }
      }
    },
    rememberMe (e) {
      this.form.rememberMe = e.target.checked
    },
    ...mapActions(['Login', 'Logout']),
    handleSubmit () {
      this.logining = true
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.rememberMe) {
            storage.set(LOGIN_USERNAME, this.form.username)
            storage.set(LOGIN_PASSWORD, this.form.password)
            storage.set(LOGIN_REMEMBERME, this.form.rememberMe)
          } else {
            storage.remove(LOGIN_USERNAME)
            storage.remove(LOGIN_PASSWORD)
            storage.remove(LOGIN_REMEMBERME)
          }

          this.Login(this.form)
            .then((token) => this.loginSuccess(token))
            .catch(err => this.requestFailed(err))
            .finally(() => {
              this.logining = false
            })
        } else {
          setTimeout(() => {
            this.logining = false
          }, 600)
        }
      })
    },

    loginSuccess (token) {
      this.$router.push({ path: '/' })
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
      this.handleCloseLoginError()
    },

    requestFailed (err) {
      this.isLoginError = true
      this.loginErrorInfo = err
      this.form.code = undefined
      // if (this.captchaEnabled) {
      //   this.getCode()
      // }
    },
    handleCloseLoginError () {
      this.isLoginError = false
      this.loginErrorInfo = ''
    }
  }
}

</script>

<style lang="less" scoped>
.wrapper{
  background: rgb(240,242,245);
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.main{
  width: 880px;
  height: 450px;
  // padding: 12px;
  display: flex;
  background: #fff;
  box-shadow: 0px 0px 1px 1px #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 #fff;
  overflow: hidden;
  .image-main{
    width: 501px;
    display: flex;
    align-items: center;
    justify-content: center;
    img{
      width: 360px;
    }
    // padding: 0 4px;
    // background: url('~');
    // background-repeat: no-repeat;
    // background-size: 100% 100%;
  }
  .form-main{
    flex:1;
    display: flex;
    justify-content: center;
    align-items: center;
    // border-left:2px solid @primary-color;
    .form-content{
      width: 400px;
      padding:0px 10px;
    }
  }
}

.header{
  display: flex;
  justify-content: left;
  align-items: center;
  margin-bottom: 40px;
  img{
    width: 140px;
    height: auto;
    margin-right: 8px;
  }
  h3{
    font-size:32px;
    font-weight: bold;
    color: @primary-color;
    
  }
}

/deep/.ant-form-item{
  // color: #fff;
  margin-bottom: 8px;
}

.user-layout-login {
  label {
    font-size: 14px;
    // color: #fff;
  }
  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }
  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }
  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;
    .register {
      float: right;
    }
  }
}

</style>
