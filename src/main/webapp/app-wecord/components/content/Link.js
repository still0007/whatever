import React from 'react'
import '../../stylesheets/chat.css'

const Link = ({ link }) => (
    <div dangerouslySetInnerHTML={{__html: link}} style={ { borderWidth: "0 0 2 0", borderStyle: "solid", padding: 8, borderColor: '#f5f5f5'}}></div>
)

export default Link