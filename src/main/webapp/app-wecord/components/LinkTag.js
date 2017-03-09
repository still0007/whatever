import React, { Component } from 'react'
import { connect } from 'react-redux'
import { fetchItem } from '../actions'
import { Glyphicon } from 'react-bootstrap'

const LinkTag = ({ dispatch, name }) => (
    <a href="#" className="list-group-item" data={name} onClick={e=>{e.preventDefault();dispatch(fetchItem('tag', name))}}>
        <Glyphicon glyph="tags" /> { name }
    </a>
)

export default connect()(LinkTag)
