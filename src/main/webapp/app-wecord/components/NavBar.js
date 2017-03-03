import React, { Component } from 'react'

const NavBar = () => (
    <ul className="nav nav-tabs" role="tablist" id="tablist">
        <li role="presentation"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Chats</a></li>
        <li role="presentation"><a href="#links" aria-controls="links" role="tab" data-toggle="tab">Links</a></li>
    </ul>
)

export default NavBar


