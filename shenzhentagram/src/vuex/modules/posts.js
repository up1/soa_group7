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
  },
  fetchUserPosts ({commit}, userId) {
    Vue.http.get('users/' + userId + '/posts')
      .then((response) => commit(types.FETCH_USER_POST, response.body.content))
  },
  editCaption ({commit}, {id, value}) {
    Vue.http.patch('posts/' + id, value)
      .then((response) => commit(types.EDIT_CAPTION, response.body))
  }
}

const mutations = {
  [types.CLEAR_POSTS] (state) {
    state.posts = []
  },
  [types.FETCH_POSTS] (state, posts) {
    state.posts = posts.reverse()
  },
  [types.FETCH_USER_POST] (state, posts) {
    state.posts = posts
  },
  [types.EDIT_CAPTION] (state, body) {
    state.posts.map((p, i) => {
      if (body.id === p.id) {
        state.posts[i].caption = body.caption
      }
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
