<template>
  <div class="columns is-multiline">
    <media v-for="post in posts" :key="post.id" :post="post"></media>
  </div>
</template>

<script type="text/babel">
  import Media from './Media'
  import {mapGetters} from 'vuex'
  export default {
    components: {
      Media
    },
    computed: mapGetters({
      posts: 'getUserPosts'
    }),
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .columns {
    padding: 30px 80px;
    align-items: center;
  }
</style>
