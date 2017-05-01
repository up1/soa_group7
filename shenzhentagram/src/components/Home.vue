<template>
  <div class="container home">
    <div class="columns">
      <div class="column is-half is-offset-one-quarter">
        <card v-for="post in posts" :key="post.id" :post="post"></card>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import Card from './Card'
  import store from '../vuex/store'
  import { mapGetters } from 'vuex'

  function preFetchRoute (dispatcher, to, from, next) {
    dispatcher('fetchPosts', to.params.postId)
    .then((response) => {
      next()
    })
  }

  export default {
    name: 'home',
    components: {
      Card
    },
    computed: {
      ...mapGetters({
        posts: 'getPosts'
      })
    },
    beforeRouteEnter (to, from, next) {
      preFetchRoute(store.dispatch, to, from, next)
    },
    beforeRouteUpdate (to, from, next) {
      preFetchRoute(this.$store.dispatch, to, from, next)
    }
  }
</script>

<style scoped>
</style>
