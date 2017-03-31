/**
 * Created by phompang on 3/30/2017 AD.
 */
import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import { FETCH_POSTS } from './mutation-types'

// Make vue aware of Vuex
Vue.use(Vuex)

// Create an object to hold the initial state when
// the app starts up
const state = {
  posts: []
}

// Create an object storing various mutations. We will write the mutation
const mutations = {
  [FETCH_POSTS] (state, posts) {
    state.posts = posts
  }
}

// Combine the initial state and the mutations to create a Vuex store.
// This store can be linked to our app.
export default new Vuex.Store({
  state,
  actions,
  getters,
  mutations,
  strict: process.env.NODE_ENV !== 'production'
})
