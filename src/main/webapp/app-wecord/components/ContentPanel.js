import React, { Component } from 'react'
import { connect } from 'react-redux'
import LinkList from './content/LinkList'
import ChatList from './content/ChatList'

const ContentPanel = ({ itemType, items }) => {
    let header, content = null

    if(items == undefined || items.length == 0){
        header = ""
        content = "empty"
    }
    else{
        if(itemType === 'contact'){
            header = "Chats"
            content = <ChatList messages={items}/>
        }
        else{
            header = "Links"
            content = <LinkList links={items}/>
        }
    }

    return (
        <div className="panel panel-default">
            <div className="panel-heading" id="right-header">
                {header}
            </div>
            <div className="panel-body" id="right-content">
                {content}
            </div>
        </div>
    )
}

export default connect((state) => ({
    itemType: state.selection.selectedItem,
    items: state.items
}))(ContentPanel)