<template>
  <div id="app">
    <navbar></navbar>
    <router-view></router-view>
  </div>
</template>

<script>
  import Navbar from './components/NavBar'
  import store from './vuex/store'

  export default {
    name: 'app',
    components: {
      Navbar
    },
    store
  }
</script>

<style lang="scss">
#app {
  min-height: 100vh;
}
</style>
