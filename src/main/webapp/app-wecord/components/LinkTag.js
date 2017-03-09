import React, { Component } from 'react'
import { connect } from 'react-redux'
import { fetchItem } from '../actions'
import { Glyphicon } from 'react-bootstrap'

const LinkTag = ({ dispatch, name, active }) => (
    <a href="#" className={active?"list-group-item active":"list-group-item"} data={name} onClick={e=>{e.preventDefault();dispatch(fetchItem('tag', name))}}>
        <Glyphicon glyph="tag" />  { name }
    </a>
)

export default connect((state, ownProps) => ({
    active: state.selection.selectedValue == ownProps.name
}))(LinkTag)
