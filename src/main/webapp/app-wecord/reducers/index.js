import { combineReducers } from 'redux'
import contacts from './contacts'
import tags from './tags'
import items from './items'
import selection from './selection'

export default combineReducers({contacts, tags, items, selection})