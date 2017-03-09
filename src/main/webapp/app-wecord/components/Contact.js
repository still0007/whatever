import React, { Component } from 'react'
import { connect } from 'react-redux'
import { fetchItem } from '../actions'
import { Glyphicon } from 'react-bootstrap'

const Contact = ({ dispatch, name, active }) => (
    <a href="#" className={active?"list-group-item active":"list-group-item"} data={name} onClick={e=>{e.preventDefault();dispatch(fetchItem('contact', name))}}>
        <Glyphicon glyph="user" /> { name }
    </a>
)

export default connect((state, ownProps) => ({
    active: state.selection.selectedValue == ownProps.name
}))(Contact)
