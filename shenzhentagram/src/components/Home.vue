<template>
  <div class="container home">
    <div class="columns">
      <div class="column is-half is-offset-one-quarter">
        <card></card>
        <card></card>
        <card></card>
        <card></card>
        <card></card>
      </div>
    </div>
  </div>
</template>

<script type="application/ecmascript">
  import Card from './Card'
  export default {
      name: 'home',
      components: {
          Card
      },
      data () {
          return {}
      }
  }
</script>

<style scoped>
</style>
