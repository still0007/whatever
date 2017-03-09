const contacts = (state = [], action) => {
    switch(action.type){
        case 'RECEIVE_CONTACTS':
            return action.contacts
        default:
            return state
    }
}

export default contacts