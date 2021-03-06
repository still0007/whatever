const changeSelection = (state = {}, action) => {
    switch(action.type){
        case "CHANGE_TAB":
            return {...state, selectedTab: action.tab }
        case "SELECT_ITEM":
            return {...state, selectedItem: action.itemType, selectedValue: action.itemValue }
        default:
            return state
    }
}

export default changeSelection