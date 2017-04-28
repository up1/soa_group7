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
  import { mapGetters } from 'vuex'
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
    created () {
      this.$store.dispatch('fetchPosts')
    },
    data () {
      return {
      }
    }
  }
</script>

<style scoped>
</style>
