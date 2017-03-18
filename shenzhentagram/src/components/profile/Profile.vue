<template>
  <div class="container">
    <profile-header></profile-header>
  </div>
</template>

<script type="text/ecmascript">
  import ProfileHeader from './ProfileHeader'
  export default {
      components: {
          ProfileHeader
      },
      data () {
          return {}
      }
  }
</script>

<style scoped>
  .container {
    margin-top: 50px;
  }
</style>
