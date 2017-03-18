import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Profile from '@/components/profile/Profile'
import VueResource from 'vue-resource'
import VueAuth from '@websanova/vue-auth'
import custom1 from '@/driver/custom1'

const router = new Router({
  hashbang: false,
  linkActiveClass: 'active',
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {auth: false}
    }, {
      path: '/',
      name: 'home',
      component: Home,
      meta: {auth: true}
    }, {
      path: '/users/:username',
      name: 'users',
      component: Profile
    }
  ]
})

Vue.router = router
Vue.use(Router)
Vue.use(VueResource)
Vue.http.options.root = 'http://127.0.0.1:9005'
Vue.http.options.xhr = {withCredentials: true}
Vue.http.options.emulateJSON = true
// Vue.use(VueAxios, axios)
// Vue.axios.defaults.baseURL = 'http://localhost:9005'
// Vue.axios.defaults.withCredentials = true
// Vue.axios.defaults.timeout = 10000
// Vue.axios.defaults.headers = {
//   'Accept': 'application/json',
//   'Content-Type': 'application/json'
// }
Vue.use(VueAuth, {
  auth: custom1,
  http: require('@websanova/vue-auth/drivers/http/vue-resource.1.x.js'),
  // http: require('@websanova/vue-auth/drivers/http/axios.1.x.js'),
  router: require('@websanova/vue-auth/drivers/router/vue-router.2.x.js'),
  token: [{request: 'Authorization', response: 'Authorization', authType: 'bearer', foundIn: 'header'}, {request: 'token', response: 'token', authType: 'custom1', foundIn: 'response'}],
  loginData: {
    url: 'auth',
    fetchUser: false
  }
})

export default router
