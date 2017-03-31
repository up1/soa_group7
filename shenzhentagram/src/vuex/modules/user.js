/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  user: {}
}

const getters = {
  getUser: state => state.user
}

const actions = {
  fetchUser ({commit}, userId) {
    Vue.http.get('users/' + userId)
      .then((response) => commit(types.FETCH_USER, response.body))
  }
}

const mutations = {
  [types.FETCH_USER] (state, user) {
    state.user = user
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
