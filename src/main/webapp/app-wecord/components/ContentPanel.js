import React, { Component } from 'react'
import LinkList from './content/LinkList'
import ChatList from './content/ChatList'

class ContentPanel extends Component{
    constructor(props){
        super(props)
    }

    render(){
        let list = null
        if(this.props.contentName === 'chats')
            list = <ChatList chats={this.props.chats}/>
        else
            list = <LinkList links={this.props.links}/>

        return (
            <div className="panel panel-default">
                <div className="panel-heading" id="right-header">
                    {this.props.contentName}
                </div>
                <div className="panel-body" id="right-content">
                    {list}
                </div>
            </div>
        )
    }
}

export default ContentPanel