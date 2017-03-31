/**
 * Created by phompang on 3/30/2017 AD.
 */
import Vue from 'vue'
import { FETCH_POSTS } from './mutation-types'

export function fetchPosts ({commit}) {
  Vue.http.get('http://104.154.167.139:9002/posts')
    .then((response) => commit(FETCH_POSTS, response.body.content))
}
