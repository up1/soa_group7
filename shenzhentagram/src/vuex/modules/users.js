/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  user: {},
  userPosts: []
}

const getters = {
  getUser: state => state.user,
  getUserPosts: state => state.userPosts
}

const actions = {
  fetchUser ({commit}, userId) {
    Vue.http.get('users/' + userId)
      .then((response) => commit(types.FETCH_USER, response.body))
  },
  fetchUserPosts ({commit}, userId) {
    commit(types.CLEAR_USER_POSTS)
    Vue.http.get('users/' + userId + '/posts')
      .then((response) => commit(types.FETCH_USER_POST, response.body.content))
  },
  setFullName ({commit}, e) {
    commit(types.SET_FULL_NAME, e.target.value)
  },
  setDisplayName ({commit}, e) {
    commit(types.SET_DISPLAY_NAME, e.target.value)
  },
  setBio ({commit}, e) {
    commit(types.SET_BIO, e.target.value)
  },
  editProfile ({commit}, body) {
    Vue.http.patch('users/self', {
      'full_name': body.full_name,
      'display_name': body.display_name,
      'bio': body.bio
    })
      .then((response) => commit(types.FETCH_USER, response.body))
  }
}

const mutations = {
  [types.FETCH_USER] (state, user) {
    state.user = user
  },
  [types.CLEAR_USER_POSTS] (state) {
    state.userPosts = []
  },
  [types.FETCH_USER_POST] (state, posts) {
    state.userPosts = posts.reverse()
  },
  [types.SET_FULL_NAME] (state, fullName) {
    state.user.full_name = fullName
  },
  [types.SET_DISPLAY_NAME] (state, displayName) {
    state.user.display_name = displayName
  },
  [types.SET_BIO] (state, bio) {
    state.user.bio = bio
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
