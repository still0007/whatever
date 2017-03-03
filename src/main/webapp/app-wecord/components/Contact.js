import React, { Component } from 'react'

const Contact = ({ id, name, avatar_url }) => (
    <a href="#" class="list-group-item chat-item" data={id}>
        <img src={ avatar_url } alt="" className="weui_media_appmsg_thumb" style={{width: 32, height: 32}}/>
        { name }
    </a>
)

export default Contact
