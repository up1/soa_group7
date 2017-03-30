<template>
  <div class="hello container">
    <div class="columns">
      <div class="column vue-img">
        <img src="../assets/logo.png">
      </div>
      <div class="column box auth-form">
        <h1 class="title">Shenzhentagram</h1>
        <register-form v-show="!isLogin" v-on:showLogin="showLogin"></register-form>
        <login-form v-show="isLogin" v-on:showRegister="showRegister"></login-form>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import LoginForm from './LoginForm'
  import RegisterForm from './RegisterForm'
  export default {
    name: 'login',
    components: {
      LoginForm, RegisterForm
    },
    data () {
      return {
        isLogin: true
      }
    },
    methods: {
      showLogin () {
        this.isLogin = true
      },
      showRegister () {
        this.isLogin = false
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .hello > .columns {
    height: 80vh;
    align-items: center;
    justify-content: center;
  }

  .vue-img {
    text-align: center;
  }

  .auth-form {
    padding: 22px;
  }
</style>
