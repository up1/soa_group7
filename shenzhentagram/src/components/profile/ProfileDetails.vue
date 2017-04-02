<template>
  <div class="column">
    <div class="profile-details">
      <span class="title is-2">{{this.user.display_name}}</span>
      <a class="button edit-profile">Edit Profile</a>
      <a class="logout-modal icon" v-on:click="showModal"><i class="fa fa-ellipsis-h" aria-hidden="true"></i></a>
    </div>

    <ul class="profile-details">
      <li><span class="subtitle is-5"><strong>112 </strong>posts</span></li>
      <li><span class="subtitle is-5"><strong>{{this.user.followed_by}} </strong>followers</span></li>
      <li><span class="subtitle is-5"><strong>{{this.user.follows}} </strong>following</span></li>
    </ul>

    <div class="profile-details">
      <span class="subtitle is-5"><strong>{{this.user.bio}}</strong></span>
    </div>

    <div class="modal">
      <div class="modal-background" v-on:click="hideModal"></div>
      <div class="modal-content">
        <a class="button is-fullwidth is-medium" v-on:click="logout">Logout</a>
        <a class="button is-fullwidth is-medium" v-on:click="hideModal">Cancel</a>
      </div>
      <button class="modal-close" v-on:click="hideModal"></button>
    </div>
  </div>
</template>

<script type="text/babel">
  import { mapGetters } from 'vuex'
  export default {
    data () {
      return {}
    },
    computed: mapGetters({
      user: 'getUser'
    }),
    methods: {
      showModal () {
        $('.modal').addClass('is-active')
      },
      hideModal () {
        $('.modal').removeClass('is-active')
      },
      logout () {
        this.$auth.logout({
          success () {
            console.log('success ' + this.context)
          },
          error () {
            console.log('error ' + this.context)
          }
        })
      }
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
  .logout-modal {
    margin-left: 30px;
    vertical-align: super;
    color: #000;
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
