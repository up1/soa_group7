/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  posts: []
}

const getters = {
  getPosts: state => state.posts.reverse()
}

const actions = {
  fetchPosts ({commit}) {
    Vue.http.get('posts')
      .then((response) => commit(types.FETCH_POSTS, response.body.content))
  },
  fetchUserPosts ({commit}, userId) {
    Vue.http.get('users/' + userId + '/posts')
      .then((response) => commit(types.FETCH_USER_POST, response.body.content))
  },
  editCaption ({commit}, {body, id}) {
    Vue.http.patch('posts/' + id, body)
      .then((response) => commit(types.EDIT_CAPTION, response.body))
  }
}

const mutations = {
  [types.FETCH_POSTS] (state, posts) {
    state.posts = posts
  },
  [types.FETCH_USER_POST] (state, posts) {
    state.posts = posts
  },
  [types.EDIT_CAPTION] (state, post) {
    for (let p of state.posts) {
      if (p.id === post.id) {
        p = post
      }
    }
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
