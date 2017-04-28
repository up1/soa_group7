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
    props: ['userId'],
    components: {
      ProfileHeader,
      ProfileMedia
    },
    created () {
      this.loadProfile(this.userId)
    },
    beforeRouteUpdate (to, from, next) {
      this.loadProfile(to.params.userId)
      next()
    },
    data () {
      return {
        currentUserId: 0
      }
    },
    methods: {
      loadProfile (id) {
        this.currentUserId = id
        this.$store.dispatch('fetchUser', id)
        this.$store.dispatch('fetchUserPosts', id)
      }
    }
  }
</script>

<style scoped>
  .container {
    margin-top: 50px;
  }
</style>
