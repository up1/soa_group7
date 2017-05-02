<template>
  <div class="container box is-paddingless">
    <div class="columns">
      <div class="column is-one-quarter menu">
        <div class="tabs">
          <ul>
            <li v-bind:class="{ 'is-active': profileEditActive }"><a v-on:click="editProfile">Edit Profile</a></li>
            <li v-bind:class="{ 'is-active': pictureFormActive }"><a v-on:click="editPicture">Change Profile Picture</a></li>
            <li class="is-unselectable"><a>Change Password</a></li>
          </ul>
        </div>
      </div>
      <div class="column form">
        <profile-edit-form v-if="profileEditActive"></profile-edit-form>
        <profile-picture-form v-if="pictureFormActive"></profile-picture-form>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import ProfileEditForm from './ProfileEditForm'
  import ProfilePictureForm from './ProfilePictureForm'

  export default {
    data () {
      return {
        profileEditActive: true,
        pictureFormActive: false
      }
    },
    components: {
      ProfileEditForm, ProfilePictureForm
    },
    methods: {
      editProfile () {
        this.profileEditActive = true
        this.pictureFormActive = false
      },
      editPicture () {
        this.profileEditActive = false
        this.pictureFormActive = true
      }
    }
  }
</script>

<style scoped>
  .box {
    margin-top: 50px;
    border-radius: 0;
  }
  .column {
    padding: 0 0.75rem;
  }
  .form {
    padding: 3rem 2rem;
  }
  .menu {
    border-right: 1px solid #dbdbdb;
  }
  .tabs ul {
    display: block;
    border-bottom: none;
  }
  .tabs a {
    padding: 16px 16px 16px 30px;
    border-bottom: none;
    color: #4a4a4a;
    border-left: 2px solid #fff;
  }
  .tabs li.is-active a {
    border-left-color: #00d1b2;
    color: #00d1b2;
  }
</style>
