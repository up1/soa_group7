<template>
  <div class="column is-one-third">
    <router-link :to="{name: 'post-single', params: { postId: this.post.id }}">
      <figure class="image">
        <img :src="'https://storage.googleapis.com/shenzhentagram-posts/' + this.post.media" alt="Image">
      </figure>
    </router-link>
  </div>
</template>

<script type="text/babel">
  export default {
    props: ['post'],
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .column {
    margin: 0;
    padding: 1.5rem;
  }
</style>
