import React, { Component } from 'react'
import NavBar from './NavBar'
import ContactList from './ContactList'
import LinkTagList from './LinkTagList'
import { connect } from 'react-redux'

const Nav = ( { contacts, tags } ) => (
    <div>
        <NavBar/>
        <div className="tab-content">
            <ContactList tabName="chats" contacts={contacts}/>
            <LinkTagList tabName="links" tags={tags}/>
        </div>
    </div>
)

export default connect((state) => ({
    contacts: state.contacts,
    tags: state.tags
}))(Nav)