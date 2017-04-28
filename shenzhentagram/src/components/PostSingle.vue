<template>
  <div class="container home">
    <div class="columns">
      <div class="column is-half is-offset-one-quarter">
        <card v-if="post" :post="post"></card>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import Card from './Card'
  export default {
    name: 'post-single',
    props: ['postId'],
    components: {
      Card
    },
    data () {
      return {
        post: null
      }
    },
    created () {
      this.$http.get('posts/' + this.postId)
      .then(
        // Success
        (response) => {
          this.post = response.body
        },
        // Error
        () => {
          console.error('Something wrong in PostSingle.vue -> created -> fetchPost();')
        }
      )
    }
  }
</script>

<style scoped>
</style>
