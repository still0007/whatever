import { combineReducers } from 'redux'
import todos from './todos'
import visibilityFilter from './visibilityFilter'
import { loadingBarReducer } from 'react-redux-loading-bar'

const todoApp = combineReducers({
  todos,
  visibilityFilter,
  loadingBar: loadingBarReducer
})

export default todoApp