 <template>
  <nav class="nav has-shadow">
    <div class="container">
      <div class="nav-left nav-menu" v-if="$auth.check()">
        <router-link :to="{name: 'home'}" class="nav-item">
          <span class="icon is-medium">
            <i class="fa fa-home fa-2x" aria-hidden="true"></i>
          </span>
        </router-link>
        <a class="nav-item">
          <span class="icon is-medium">
            <i class="fa fa-search fa-2x" aria-hidden="true"></i>
          </span>
        </a>
        <a class="nav-item">
          <span class="icon is-medium" style="padding-right: 0.45rem;">
            <i class="fa fa-plus-circle fa-2x" aria-hidden="true"></i>
          </span>
          <span style="font-weight: bold;">New Post</span>
        </a>
      </div>

      <div class="nav-center">
        <router-link :to="{name: 'home'}" class="nav-item">
          <img src="../assets/images/logo-navbar.png" alt="Shenzhentagram logo">
          <h1 class="title force-fredoka">Shenzhentagram</h1>
        </router-link>
      </div>

      <!-- This "nav-menu" is hidden on mobile -->
      <!-- Add the modifier "is-active" to display it on mobile -->
      <div class="nav-right nav-menu" v-if="$auth.check()">
        <router-link :to="{name: 'users', params: {userId: this.$auth.user().id}}"  class="nav-item">
          <span class="icon is-medium">
            <i class="fa fa-user-o fa-2x" aria-hidden="true"></i>
          </span>
        </router-link>
        <a class="nav-item" v-on:click="logout">
          <span class="icon is-medium">
            <i class="fa fa-sign-out fa-2x" aria-hidden="true"></i>
          </span>
        </a>
      </div>
    </div>
  </nav>
</template>

<script type="text/babel">
  export default {
    data () {
      return {}
    },
    methods: {
      logout () {
        this.$auth.logout({
          success () {
            this.$router.push('login')
          },
          error () {

          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .nav {
    height: 100px;
    padding: 10px 0px;

    .nav-item {
      font-size: 1.4rem;
      padding: 0.5rem 0.35rem;

      img {
        padding-right: 0.5rem;
        max-height: 50px;
      }
    }

    a.nav-item:hover, .nav-item a:hover {
      color: #1ABC9C;
    }
  }

  .title {
    font-size: 2rem;
    color: #1ABC9C;
  }
</style>
