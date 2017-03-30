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
  export default {
    name: 'home',
    components: {
      Card
    },
    data () {
      return {
        posts: [
          {
            'id': 1,
            'type': 'image',
            'comments': 22,
            'caption': 'caption',
            'reactions': 33,
            'media': 'photo.jpg',
            'userId': 1,
            'created_at': 1490781129000,
            'updated_at': 1490781129000
          },
          {
            'id': 2,
            'type': 'image',
            'comments': 222,
            'caption': 'caption11',
            'reactions': 333,
            'media': 'photo.jpg',
            'userId': 1,
            'created_at': 1490781129000,
            'updated_at': 1490781129000
          },
          {
            'id': 3,
            'type': 'image',
            'comments': 0,
            'caption': 'dksfjsadkfjklasdjklfjs',
            'reactions': 4,
            'media': 'photo.jpg',
            'userId': 1,
            'created_at': 1490781129000,
            'updated_at': 1490781129000
          }
        ]
      }
    }
  }
</script>

<style scoped>
</style>
