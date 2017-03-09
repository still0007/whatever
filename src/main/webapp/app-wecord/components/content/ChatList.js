import React, { Component } from 'react'
import Chat from './Chat'

const ChatList = ({ messages }) => {
    return (
        <div>
        {messages.map(message =>
            <Chat key={message.id} message={message} />
        )}
        </div>
    )

}

export default ChatList