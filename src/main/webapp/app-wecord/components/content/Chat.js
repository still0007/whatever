import React from 'react'
import '../../stylesheets/chat.css'

const Chat = ({ message }) => (
    <div dangerouslySetInnerHTML={{__html: message.text}}></div>
)

export default Chat