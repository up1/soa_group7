/**
 * Created by phompang on 3/30/2017 AD.
 */
import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import * as types from './mutation-types'
import user from './modules/user'
import posts from './modules/posts'

// Make vue aware of Vuex
Vue.use(Vuex)

// Create an object to hold the initial state when
// the app starts up
const state = {
  showLogin: true
}

// Create an object storing various mutations. We will write the mutation
const mutations = {
  [types.SHOW_LOGIN] (state, show) {
    state.showLogin = show
  }
}

// Combine the initial state and the mutations to create a Vuex store.
// This store can be linked to our app.
export default new Vuex.Store({
  state,
  actions,
  getters,
  mutations,
  modules: {
    posts,
    user
  },
  strict: process.env.NODE_ENV !== 'production'
})
