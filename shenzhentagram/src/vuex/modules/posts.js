/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  posts: [],
  userPosts: []
}

const getters = {
  getPosts: state => state.posts,
  getUserPosts: state => state.userPosts
}

const actions = {
  fetchPosts ({commit}) {
    Vue.http.get('posts')
      .then((response) => commit(types.FETCH_POSTS, response.body.content))
  },
  fetchUserPosts ({commit}, userId) {
    commit(types.CLEAR_USER_POSTS)
    Vue.http.get('users/' + userId + '/posts')
      .then((response) => commit(types.FETCH_USER_POST, response.body.content))
  }
}

const mutations = {
  [types.CLEAR_POSTS] (state) {
    state.posts = []
  },
  [types.CLEAR_USER_POSTS] (state) {
    state.userPosts = []
  },
  [types.FETCH_POSTS] (state, posts) {
    state.posts = posts.reverse()
  },
  [types.FETCH_USER_POST] (state, posts) {
    state.userPosts = posts.reverse()
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
