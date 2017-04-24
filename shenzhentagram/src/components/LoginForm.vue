<template>
  <form v-on:submit.prevent="login()">
    <div v-if="error" class="notification is-danger">
      <button type="button" class="delete" v-on:click="clearError"></button>
      Incorrect username or password
    </div>

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

    <div class="field is-grouped">
      <p class="control">
        <button type="submit" class="button is-primary is-medium">
          Login
        </button>
      </p>
      <p class="control">
        <a class="button is-link is-medium" v-on:click="showRegister">
          Register
        </a>
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
            username: '',
            password: ''
          },
          rememberMe: false
        },
        error: null
      }
    },
    mounted () {
      // Can set query parameter here for auth redirect or just do it silently in login redirect.
    },
    methods: {
      login () {
        var redirect = this.$auth.redirect()
        this.$auth.login({
          body: this.data.body,
          rememberMe: this.data.rememberMe,
          redirect: {name: redirect ? redirect.from.name : 'home'},
          success (request) {
            // TODO Toasting some login success ?
          },
          error (response) {
            this.error = response.body
          }
        })
      },
      clearError () {
        this.error = null
      },
      showRegister () {
        this.$router.push({ path: '/register' })
      }
    }
  }
</script>
