/**
 * Created by phompang on 3/30/2017 AD.
 */
import Vue from 'vue'
import Vuex from 'vuex'
import users from './modules/users'
import posts from './modules/posts'

// Make vue aware of Vuex
Vue.use(Vuex)

// Create an object to hold the initial state when
// the app starts up
const state = {

}

// Actions
const actions = {

}

// Getter for state
const getters = {

}

// Create an object storing various mutations. We will write the mutation
const mutations = {

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
    users
  },
  strict: process.env.NODE_ENV !== 'production'
})
