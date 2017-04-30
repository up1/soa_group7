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
  import Card from './Card'
  import { mapGetters } from 'vuex'
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
    created () {
      this.$store.dispatch('fetchSinglePost', this.postId)
    }
  }
</script>

<style scoped>
</style>
