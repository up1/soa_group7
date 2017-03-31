<template>
  <form v-on:submit.prevent="register()">
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
        <input v-model="data.body.full_name" class="input is-medium" type="text" placeholder="Full name">
      </p>
    </div>
    <div class="field">
      <p class="control">
        <input v-model="data.body.display_name" class="input is-medium" type="text" placeholder="Display name">
      </p>
    </div>

    <div v-show="error" class="help is-danger" style="color:red; word-wrap:break-word;">{{ error }}</div>
    <div class="field is-grouped">
      <p class="control">
        <button type="submit" class="button is-primary is-medium">
          Register
        </button>
      </p>
      <p class="control">
        <a class="button is-link is-medium" v-on:click="showLogin">
          Login
        </a>
      </p>
    </div>
  </form>
</template>

<script type="text/babel">
  export default {
    data () {
      return {
        context: 'register context',
        data: {
          body: {
            username: '',
            password: '',
            full_name: '',
            display_name: ''
          }
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
      register () {
        this.$auth.register({
          body: this.data.body,
          success () {
            console.log('success ' + this.context)
            this.$store.dispatch('showLogin', true)
          },
          error (res) {
            console.log('error ' + this.context)
            this.error = res.data
          }
        })
      },
      showLogin () {
        this.$store.dispatch('showLogin', true)
      }
    }
  }
</script>
