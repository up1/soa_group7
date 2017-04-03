/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  posts: []
}

const getters = {
  getPosts: state => state.posts
}

const actions = {
  fetchPosts ({commit}) {
    Vue.http.get('posts')
      .then((response) => commit(types.FETCH_POSTS, response.body.content))
  }
}

const mutations = {
  [types.FETCH_POSTS] (state, posts) {
    state.posts = posts
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
