import React, { Component } from 'react'
import { changeTab } from '../actions'
import { connect } from 'react-redux'

const NavBar = ({ dispatch, activeTab }) => (
    <ul className="nav nav-tabs">
        <li className={ activeTab == "contacts" ? "active" : "" } onClick={e=>{dispatch(changeTab('contacts'))}}>
            <a href="#contacts">Contacts</a>
        </li>
        <li className={ activeTab == "links" ? "active" : "" } onClick={e=>dispatch(changeTab('links'))}>
            <a href="#links">Links</a>
        </li>
    </ul>
)

const mapStateToProps = (state) => {
    return {activeTab: state.selection.selectedTab ? state.selection.selectedTab : "contacts" }
}

export default connect(mapStateToProps)(NavBar)
