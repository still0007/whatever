import React, { Component } from 'react'
import NavBar from './NavBar'
import ContactList from './ContactList'
import LinkTagList from './LinkTagList'

const Nav = ( { users, linkTags } ) => (
    <div>
        <NavBar/>
        <div className="tab-content">
            <ContactList tabName="chats" users={users}/>
            <LinkTagList tanName="links" linkTags={linkTags}/>
        </div>
    </div>
)

export default Nav