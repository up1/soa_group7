<template>
  <div class="container">
    <profile-header></profile-header>
    <profile-media></profile-media>
  </div>
</template>

<script type="text/babel">
  import ProfileHeader from './ProfileHeader'
  import ProfileMedia from './ProfileMedia'
  export default {
    components: {
      ProfileHeader,
      ProfileMedia
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
