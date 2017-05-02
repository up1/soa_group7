<template>
  <div class="columns">
    <div class="column is-narrow profile">
      <figure class="image profile-figure is-1by1">
        <img v-if="this.user.profile_picture != null" class="profile-img" :src="'https://storage.googleapis.com/shenzhentagram-avatar/' + this.user.profile_picture">
        <img v-else class="profile-img" src="http://bulma.io/images/placeholders/256x256.png">
      </figure>
    </div>
    <profile-details></profile-details>
  </div>
</template>

<script type="text/babel">
  import ProfileDetails from './ProfileDetails'
  import { mapGetters } from 'vuex'
  export default {
    components: {
      ProfileDetails
    },
    computed: mapGetters({
      user: 'getUser'
    }),
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .profile {
    padding: 0 100px;
    align-items: center;
    display: flex;
  }
  .profile-figure {
    width: 150px;
    height: 150px;
  }
</style>
