import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Profile from '@/components/profile/Profile'
import ProfileEdit from '@/components/profile/ProfileEdit'
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
      path: '/users/:userId',
      name: 'users',
      component: Profile
    }, {
      path: '/account/edit/',
      name: 'account_edit',
      component: ProfileEdit,
      meta: {auth: true}
    }
  ]
})

Vue.router = router
Vue.use(Router)
Vue.use(VueResource)
Vue.http.options.root = 'http://35.185.168.160'
Vue.http.options.xhr = {withCredentials: true}

Vue.use(VueAuth, {
  auth: custom1,
  http: require('@websanova/vue-auth/drivers/http/vue-resource.1.x.js'),
  router: require('@websanova/vue-auth/drivers/router/vue-router.2.x.js'),
  token: [{request: 'Authorization', response: 'Authorization', authType: 'bearer', foundIn: 'header'}, {request: 'token', response: 'token', authType: 'custom1', foundIn: 'response'}],
  loginData: {
    url: 'auth'
  },
  fetchData: {
    url: 'users/self'
  },
  parseUserData: function (data) {
    return data
  },
  registerData: {
    url: 'users'
  }
})

export default router
