 <template>
  <nav class="nav has-shadow">
    <div class="container">
      <div class="nav-left nav-menu" v-if="$auth.check()">
        <router-link :to="{name: 'home'}" class="nav-item">
          <img src="../assets/images/logo-navbar.png" alt="Shenzhentagram logo">
          <h1 class="title force-fredoka">Shenzhentagram</h1>
        </router-link>
      </div>

      <div class="nav-center">
        <a class="nav-item">
          <p class="control has-icon">
            <input v-model="keyword" class="input" type="text" placeholder="Search...">
            <span class="icon is-small">
              <i class="fa fa-search"></i>
            </span>
          </p>
          <search-modal v-bind:keyword="keyword"></search-modal>
        </a>
      </div>

      <!-- This "nav-menu" is hidden on mobile -->
      <!-- Add the modifier "is-active" to display it on mobile -->
      <div class="nav-right nav-menu" v-if="$auth.check()">
        <router-link :to="{name: 'new_post'}" class="nav-item">
          <span class="icon is-medium">
            <i class="fa fa-plus-circle fa-2x" aria-hidden="true"></i>
          </span>
        </router-link>
        <router-link :to="{name: 'users', params: {userId: this.$auth.user().id}}"  class="nav-item">
          <span class="icon is-medium">
            <i class="fa fa-user-o fa-2x" aria-hidden="true"></i>
          </span>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script type="text/babel">
  import SearchModal from './SearchModal'
  export default {
    data () {
      return {
        keyword: ''
      }
    },
    components: {
      SearchModal
    },
    methods: {
      logout () {
        this.$auth.logout({
          success () {
            this.$router.push({ path: '/login' })
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
    padding: 5px 0px;
  }

  .title {
    font-size: 2rem;
    color: #1ABC9C;
  }
</style>
