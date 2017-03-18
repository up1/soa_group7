<template>
  <div class="columns">
    <div class="column is-narrow profile">
      <figure class="image is-128x128">
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
    padding: 0 50px;
    align-items: center;
    display: flex;
  }
</style>
