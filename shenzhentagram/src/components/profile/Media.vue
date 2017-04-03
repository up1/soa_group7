<template>
  <div class="column is-one-third">
    <figure class="image is-1by1">
      <img :src="'https://storage.googleapis.com/shenzhentagram-posts/' + this.post.media" alt="Image">
    </figure>
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
