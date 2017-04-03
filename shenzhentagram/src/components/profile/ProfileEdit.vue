<template>
  <div class="container box is-paddingless">
    <div class="columns">
      <div class="column is-one-quarter menu">
        <div class="tabs">
          <ul>
            <li class="is-active"><a>Edit Profile</a></li>
            <li class="is-unselectable"><a>Change Password</a></li>
          </ul>
        </div>
      </div>
      <div class="column form">
        <profile-edit-form></profile-edit-form>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  import ProfileEditForm from './ProfileEditForm'
  export default {
    data () {
      return {}
    },
    components: {
      ProfileEditForm
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
