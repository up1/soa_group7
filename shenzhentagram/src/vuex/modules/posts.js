/**
 * Created by phompang on 3/31/2017 AD.
 */
import Vue from 'vue'
import * as types from '../mutation-types'

const state = {
  posts: [],
  singlePost: null
}

const getters = {
  getPosts: state => state.posts,
  getSinglePost: state => state.singlePost
}

const actions = {

  /*
    Post Actions
   */

  // Mutliple post (Feed)

  fetchPosts ({commit}) {
    Vue.http.get('posts')
      .then((response) => commit(types.FETCH_POSTS, response.body.content))
  },
  editCaption ({commit}, {id, value}) {
    return Vue.http.patch('posts/' + id, value)
      .then((response) => commit(types.EDIT_CAPTION, response.body))
  },
  deletePost ({commit}, post) {
    Vue.http.delete('posts/' + post.id)
      .then((response) => commit(types.DELETE_POST, post))
  },

  // Single post

  fetchSinglePost ({commit}, postId) {
    // Clear old post
    commit(types.CLEAR_SINGLE_POST)

    // Fetch single post
    return Vue.http.get('posts/' + postId)
      .then((response) => {
        let post = response.body
        let comments = []

        // Fetch single post comments
        Vue.http.get('posts/' + postId + '/comments')
          .then((response) => {
            comments = response.body.comments
            commit(types.FETCH_SINGLE_POST, {post, comments})
          })

        commit(types.FETCH_SINGLE_POST, {post, comments})
      })
  },

  /*
    Comment Actions
   */

  fetchComment ({commit}, postId) {
    Vue.http.get('posts/' + postId + '/comments')
      .then((response) => commit(types.FETCH_COMMENT, {
        postId: postId,
        comments: response.body.comments
      }))
  },
  addComment ({commit}, {postId, text}) {
    return Vue.http.post('posts/' + postId + '/comments', { text })
      .then((response) => commit(types.ADD_COMMENT, {
        postId: postId,
        comment: response.body
      }))
  },
  editComment ({commit}, {postId, commentId, text}) {
    return Vue.http.put('posts/' + postId + '/comments/' + commentId, { text })
      .then((response) => commit(types.EDIT_COMMENT, {
        postId,
        commentId,
        text
      }))
  },
  deleteComment ({commit}, {postId, commentId}) {
    return Vue.http.delete('posts/' + postId + '/comments/' + commentId)
      .then((response) => commit(types.DELETE_COMMENT, {
        postId,
        commentId
      }))
  },

  fetchReaction ({commit}, postId) {
    Vue.http.get('posts/' + postId + '/reactions')
      .then((response) => commit(types.FETCH_REACTION, {
        postId: postId,
        reactions: response.body
      }))
  },
  addReaction ({commit}, {postId, reaction}) {
    console.log('action')
    console.log(reaction)
    return Vue.http.post('posts/' + postId + '/reactions', { reaction })
      .then((response) => commit(types.ADD_REACTION, {
        postId: postId,
        reaction: response.body
      }))
  }

}

const mutations = {

  /*
    Post Actions
   */

  [types.CLEAR_POSTS] (state) {
    state.posts = []
  },
  [types.FETCH_POSTS] (state, posts) {
    posts = posts.reverse()
    posts.map(p => {
      p.comments = []
      p.reactions_data = []
    })
    state.posts = posts
  },
  [types.CLEAR_SINGLE_POST] (state) {
    state.singlePost = null
  },
  [types.FETCH_SINGLE_POST] (state, {post, comments}) {
    post.comments = comments
    state.singlePost = post
  },
  [types.EDIT_CAPTION] (state, body) {
    state.posts.map((p, i) => {
      if (body.id === p.id) {
        state.posts[i].caption = body.caption
      }
    })
  },
  [types.DELETE_POST] (state, post) {
    state.posts.splice(state.posts.indexOf(post), 1)
  },

  /*
    Comment Actions
   */

  [types.FETCH_COMMENT] (state, {postId, comments}) {
    state.posts.map((p, i) => {
      if (p.id === postId) {
        state.posts[i].comments = comments
      }
    })
  },
  [types.ADD_COMMENT] (state, {postId, comment}) {
    state.posts.map((p, i) => {
      if (p.id === postId) {
        if (state.posts[i].comments == null) {
          state.posts[i].comments = []
        }
        state.posts[i].comments.push(comment)
        state.posts[i].comment_count++
      }
    })
  },
  [types.EDIT_COMMENT] (state, {postId, commentId, text}) {
    state.posts.map((p, i) => {
      if (p.id === postId) {
        state.posts[i].comments.map((c, j) => {
          if (c.id === commentId) {
            state.posts[i].comments[j].text = text
          }
        })
      }
    })
  },
  [types.DELETE_COMMENT] (state, {postId, commentId}) {
    state.posts.map((p, i) => {
      state.posts[i].comment_count--
      if (p.id === postId) {
        state.posts[i].comments = state.posts[i].comments.filter((comment) => {
          return comment.id !== commentId
        })
      }
    })
  },

  [types.FETCH_REACTION] (state, {postId, reactions}) {
    state.posts.map((p, i) => {
      if (p.id === postId) {
        state.posts[i].reactions_data = reactions
      }
    })
  },
  [types.ADD_REACTION] (state, {postId, reaction}) {
    console.log('mutation')
    console.log(reaction)
    state.posts.map((p, i) => {
      if (p.id === postId) {
        if (state.posts[i].reactions_data == null) {
          state.posts[i].reactions_data = []
        }
        state.posts[i].reactions_data.push(reaction)
        state.posts[i].reactions++
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
