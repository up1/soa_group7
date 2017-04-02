<template>
  <div class="columns is-multiline">
    <media></media>
    <media></media>
    <media></media>
    <media></media>
    <media></media>
    <media></media>
    <media></media>
  </div>
</template>

<script type="text/babel">
  import Media from './Media'
  export default {
    components: {
      Media
    },
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .columns {
    padding: 30px 80px;
  }
</style>
