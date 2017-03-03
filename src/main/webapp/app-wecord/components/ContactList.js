import React, { Component } from 'react'
import Contact from './Contact'

const ContactList = ({ users, tabName }) => (
    <div role="tabpanel" className="tab-pane active" id={tabName}>
        <div class="list-group">
            {users.map(user =>
                <Contact key={user.id} {...user}/>
            )}
        </div>
    </div>
)

export default ContactList