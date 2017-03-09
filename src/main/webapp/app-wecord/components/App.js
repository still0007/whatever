import React, { Component } from 'react'
import Nav from './Nav'
import ContentPanel from './ContentPanel'
import { fetchContacts, fetchLinkTags } from '../actions'
import { connect } from 'react-redux'

class App extends Component {

    constructor(props){
        super(props)
    }

    componentDidMount(){
        const { dispatch } = this.props
        dispatch(fetchContacts())
        dispatch(fetchLinkTags())
    }

    render() {
        return <div className="row">
            <div className="col-md-3 menu">
                <Nav/>
            </div>
            <div className="col-md-9">
                <ContentPanel />
            </div>
        </div>
    }
}

export default connect()(App)