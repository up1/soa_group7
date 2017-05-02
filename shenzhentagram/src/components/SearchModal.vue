<template>
  <div class="search" v-if="this.users.length > 0">
    <div class="arrow"></div>
    <div class="search-box">
      <div class="content">
        <router-link :to="{name: 'users', params: { userId: user.id }}" v-for="user in this.users" :key="user.id" v-on:click.native="clear" class="list">
          <div>
            <figure class="image is-32x32">
              <img v-if="user.profile_picture != null" class="profile-img" :src="'https://storage.googleapis.com/shenzhentagram-avatar/' + user.profile_picture">
              <img v-else class="profile-img" src="http://bulma.io/images/placeholders/96x96.png" alt="Image">
            </figure>
            <div class="name">
              <p class="name is-bold">{{user.username}}</p>
              <p class="name">{{user.display_name}}</p>
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  export default {
    props: ['keyword'],
    data () {
      return {
        users: []
      }
    },
    watch: {
      keyword: function (v) {
        if (v === '') {
          this.users = []
        } else {
          this.search()
        }
      }
    },
    methods: {
      clear () {
        this.$parent.keyword = ''
      },
      search () {
        this.$http.get('users/search?name=' + this.keyword)
          .then((response) => (this.users = response.body))
      }
    }
  }
</script>

<style scoped>
  .search {
    position: absolute;
    margin-top:30px;
  }
  .arrow {
    border: solid 1px #e6e6e6;
    box-shadow: 0 0 5px 1px rgba(0,0,0,.0975);
    background-color: #fff;
    height: 14px;
    margin: auto;
    position: relative;
    -webkit-transform: rotate(45deg);
    transform: rotate(45deg);
    width: 14px;
    top: 12px;
    z-index: 1;
  }
  .search-box {
    background: #fff;
    border-radius: 3px;
    box-shadow: 0 0 5px rgba(0,0,0,.0975);
    display: block;
    position: absolute;
    right: -122px;
    top: 18px;
    width: 245px;
    z-index: 9;
  }
  .search-box > .content {
    max-height: 362px;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 0;
    -webkit-overflow-scrolling: touch;
  }
  .search-box > .content > .list {
    align-items: center;
    border-bottom: solid 1px #dbdbdb;
    display: flex;
    flex-direction: row;
    flex-shrink: 0;
    padding: 12px 16px;
  }
  .search-box > .content > .list > div {
    align-items: center;
    margin-right: 0;
    white-space: nowrap;
    width: 100%;
    flex-direction: row;
    display: flex;
  }
  .list .image {
    margin-right: 10px;
  }
  .list .name {
    font-size: 16px;
    margin-bottom: 0;
  }
</style>
