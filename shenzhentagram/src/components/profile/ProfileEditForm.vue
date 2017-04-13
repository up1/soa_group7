<template>
  <form v-on:submit.prevent="editProfile">
    <div class="columns">
      <div class="column profile is-one-quarter is-paddingless">
        <figure class="image profile-figure is-48x48 is-pulled-right">
          <img class="profile-img" src="http://bulma.io/images/placeholders/64x64.png">
        </figure>
      </div>
      <div class="column is-paddingless display_name">
        <h1 class="title is-4">{{this.user.username}}</h1>
      </div>
    </div>

    <div class="field is-horizontal form">
      <div class="field-label is-normal">
        <label class="label">Full Name</label>
      </div>
      <div class="field-body">
        <div class="field">
          <div class="control">
            <input :value="user.full_name" @input="setFullName" class="input" type="text" placeholder="Full Name">
          </div>
          <!--<p class="help is-danger">-->
            <!--This field is required-->
          <!--</p>-->
        </div>
      </div>
    </div>

    <div class="field is-horizontal">
      <div class="field-label is-normal">
        <label class="label">Display Name</label>
      </div>
      <div class="field-body">
        <div class="field">
          <div class="control">
            <input :value="user.display_name" @input="setDisplayName" class="input" type="text" placeholder="Display Name">
          </div>
          <!--<p class="help is-danger">-->
            <!--This field is required-->
          <!--</p>-->
        </div>
      </div>
    </div>

    <div class="field is-horizontal">
      <div class="field-label is-normal">
        <label class="label">Bio</label>
      </div>
      <div class="field-body">
        <div class="field">
          <div class="control">
            <textarea :value="user.bio" @input="setBio" class="textarea" placeholder="Bio"></textarea>
          </div>
        </div>
      </div>
    </div>


    <div class="field is-horizontal">
      <div class="field-label">
        <!-- Left empty for spacing -->
      </div>
      <div class="field-body">
        <div class="field">
          <div class="control">
            <button type="submit" class="button is-primary">
              Submit
            </button>
          </div>
        </div>
      </div>
    </div>

  </form>
</template>

<script type="text/babel">
  import {mapGetters} from 'vuex'
  export default {
    created () {
      this.$store.dispatch('fetchUser', this.$auth.user().id)
    },
    computed: mapGetters({
      user: 'getUser'
    }),
    methods: {
      setFullName (e) {
        this.user.full_name = e.target.value
      },
      setDisplayName (e) {
        this.user.display_name = e.target.value
      },
      setBio (e) {
        this.user.bio = e.target.value
      },
      editProfile () {
        this.$store.dispatch('editProfile', this.user)
      }
    }
  }
</script>

<style scoped>
  .profile {
    margin-right: 30px;
  }
  .display_name {
    display: flex;
    align-items: center;
  }
  .form {
    margin-top: 40px;
  }
</style>
