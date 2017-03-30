<template>
  <nav class="nav has-shadow">
    <div class="container">
      <div class="nav-left">
      <span class="nav-item">
        <router-link :to="{name: 'home'}"><h1 class="title is-4">Shenzhentagram</h1></router-link>
        <!--<img src="http://bulma.io/images/bulma-logo.png" alt="Bulma logo">-->
      </span>
      </div>

      <div class="nav-center">
        <a class="nav-item">
          <p class="control has-icon">
            <input class="input is-medium" type="text" placeholder="Search...">
            <span class="icon is-small">
              <i class="fa fa-search"></i>
            </span>
          </p>
        </a>
      </div>

      <!-- This "nav-menu" is hidden on mobile -->
      <!-- Add the modifier "is-active" to display it on mobile -->
      <div class="nav-right nav-menu">
        <router-link :to="{name: 'users', params: {username: 'test'}}" v-if="$auth.ready()" class="nav-item">
          <i class="fa fa-user-o fa-2x" aria-hidden="true"></i>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script type="text/babel">
  export default {
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .nav {
    padding: 10px 0px;
  }
</style>
