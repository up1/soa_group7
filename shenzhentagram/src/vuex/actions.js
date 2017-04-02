/**
 * Created by phompang on 3/30/2017 AD.
 */
import * as types from './mutation-types'

export const showLogin = ({commit}, show) => {
  commit(types.SHOW_LOGIN, show)
}
