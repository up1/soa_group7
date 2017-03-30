<template>
  <form v-on:submit.prevent="login()">
    <div class="field">
      <p class="control has-icon">
        <input v-model="data.body.username" class="input is-medium" type="text" placeholder="Username">
        <span class="icon">
        <i class="fa fa-user"></i>
      </span>
      </p>
    </div>
    <div class="field">
      <p class="control has-icon">
        <input v-model="data.body.password" class="input is-medium" type="password" placeholder="Password">
        <span class="icon">
        <i class="fa fa-lock"></i>
      </span>
      </p>
    </div>
    <div class="field">
      <p class="control">
        <label class="checkbox">
          <input v-model="data.rememberMe" type="checkbox" />
          Remember me
        </label>
      </p>
    </div>

    <div v-show="error" class="help is-danger" style="color:red; word-wrap:break-word;">{{ error }}</div>
    <div class="field is-grouped">
      <p class="control">
        <button class="button is-primary is-medium">
          Login
        </button>
      </p>
      <p class="control">
        <button class="button is-link is-medium">
          Register
        </button>
      </p>
    </div>
  </form>
</template>

<script type="text/babel">
  export default {
    data () {
      return {
        context: 'login context',
        data: {
          body: {
            username: 'admin',
            password: 'password'
          },
          rememberMe: false
        },
        error: null
      }
    },
    mounted () {
      console.log(this.$auth.redirect())
      console.log('test')
      // Can set query parameter here for auth redirect or just do it silently in login redirect.
    },
    methods: {
      login () {
        var redirect = this.$auth.redirect()
        this.$auth.login({
          body: this.data.body,
          rememberMe: this.data.rememberMe,
          redirect: {name: redirect ? redirect.from.name : 'home'},
          success () {
            console.log('success ' + this.context)
          },
          error (res) {
            console.log('error ' + this.context)
            this.error = res.data
          }
        })
      }
    }
  }
</script>
