import React from 'react'
import '../../stylesheets/chat.css'

const Link = ({ link }) => (
    <div dangerouslySetInnerHTML={{__html: link}}></div>

)

export default Link