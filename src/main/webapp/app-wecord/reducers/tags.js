const tags = (state = [], action) => {
    switch(action.type){
        case 'RECEIVE_TAGS':
            return action.tags
        default:
            return state
    }
}

export default tags