import React, { Component } from 'react'
import { connect } from 'react-redux'
import Contact from './Contact'

const ContactList = ({ contacts, tabName, activeTab }) => (
    <div role="tabpanel" className="tab-pane" style={{ display: activeTab=="contacts" ? "inline" : "none" }}>
        <div className="list-group">
            {contacts.map(contact =>
                <Contact key={contact} name={contact}/>
            )}
        </div>
    </div>
)

export default connect((state) => ({
    activeTab: state.selection.selectedTab ? state.selection.selectedTab : "contacts"
}))(ContactList)