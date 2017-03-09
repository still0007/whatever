import React from 'react'
import '../../stylesheets/chat.css'

const Chat = ({ message }) => (
    <div className={message.direction}>
        <div className="arrow"></div>
        <span dangerouslySetInnerHTML={{__html: message.text}}></span>
    </div>
)

export default Chat