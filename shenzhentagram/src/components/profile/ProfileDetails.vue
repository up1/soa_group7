<template>
  <div class="column">
    <div class="profile-details">
      <span class="title is-2">{{$route.params.username}}</span>
      <a class="button edit-profile">Edit Profile</a>
    </div>

    <ul class="profile-details">
      <li><span class="subtitle is-5"><strong>112 </strong>posts</span></li>
      <li><span class="subtitle is-5"><strong>44 </strong>followers</span></li>
      <li><span class="subtitle is-5"><strong>44 </strong>following</span></li>
    </ul>

    <div class="profile-details">
      <span class="subtitle is-5"><strong>Pichai Sivawat</strong></span>
    </div>
  </div>
</template>

<script type="text/ecmascript">
  export default {
      data() {
          return {}
      }
  }
</script>

<style scoped>
  .profile-details {
    margin-bottom: 20px;
  }
  .edit-profile {
    margin-left: 30px;
    vertical-align: text-bottom;
  }
  ul {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    -ms-flex-direction: row;
    flex-direction: row;
  }
  li {
    margin-right: 40px;
  }
</style>
