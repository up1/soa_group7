<template>
  <div class="columns">
    <div class="column is-narrow profile">
      <figure class="image  profile-figure is-1by1">
        <img class="profile-img" src="http://bulma.io/images/placeholders/256x256.png">
      </figure>
    </div>
    <profile-details></profile-details>
  </div>
</template>

<script type="text/ecmascript">
  import ProfileDetails from './ProfileDetails'
  export default {
      components: {
          ProfileDetails
      },
      data() {
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
