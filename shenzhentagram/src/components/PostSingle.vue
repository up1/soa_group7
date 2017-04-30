<template>
  <div class="container home">
    <div class="columns">
      <div class="column is-half is-offset-one-quarter">
        <card v-if="post" :key="post.id" :post="post"></card>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import Vue from 'vue'
  import store from '../vuex/store'
  import Card from './Card'
  import { mapGetters } from 'vuex'

  function preFetchRoute (dispatcher, to, from, next) {
    dispatcher('fetchSinglePost', to.params.postId)
    .then(
      // Success (Post found)
      (response) => {
        next()
      },
      // Error (Post not found)
      () => {
        Vue.router.push({url: '/'})
      }
    )
  }

  export default {
    name: 'post-single',
    props: ['postId'],
    components: {
      Card
    },
    computed: {
      ...mapGetters({
        post: 'getSinglePost'
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
