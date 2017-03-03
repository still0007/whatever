import React, { Component } from 'react'
import Nav from './Nav'
import ContentPanel from './ContentPanel'

const App = () => (
    <div className="row">
        <div className="col-md-3 menu">
            <Nav/>
        </div>
        <div className="col-md-9">
            <ContentPanel/>
        </div>
    </div>
)

export default App