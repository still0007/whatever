import queryString from 'query-string'

export const changeTab = (tabName) => ({
    type: 'CHANGE_TAB',
    tab: tabName
})

export const selectItem = (itemType) => ({
    type: 'SELECT_ITEM',
    itemType: itemType
})

export const receiveContacts = (contacts) => ({
    type: "RECEIVE_CONTACTS",
    contacts: contacts
})

export const receiveTags = (tags) => ({
    type: "RECEIVE_TAGS",
    tags: tags
})

export const receiveItems = (itemType, items) => ({
    type: "RECEIVE_ITEMS",
    itemType: itemType,
    items: items
})

export const fetchContacts = () => {
    return function(dispatch){
        return fetch('/api/user/contacts.json', { credentials: 'same-origin' })
            .then(response => response.json())
            .then(json => {
                dispatch(receiveContacts(json))
            })
    }
}

export const fetchLinkTags = () => {
    return function(dispatch){
        return fetch('/api/message/link/tags.json', { credentials: 'same-origin' })
            .then(response => response.json())
            .then(json => {
                dispatch(receiveTags(json))
            })
    }
}

export const fetchItem = (type, key) => {
    return function(dispatch){

        dispatch(selectItem(type))

        switch(type){
            case 'contact':
                return fetch(`/api/message/list.json?`+queryString.stringify({contact: key}), { credentials: 'same-origin' })
                    .then(response => response.json())
                    .then(json => dispatch(receiveItems('contact', json)))
            case 'tag':
                return fetch('/api/message/links.json?'+queryString.stringify({tagname: key}), { credentials: 'same-origin' })
                    .then(response => response.json())
                    .then(json => dispatch(receiveItems('tag', json)))
            default:
        }
    }
}