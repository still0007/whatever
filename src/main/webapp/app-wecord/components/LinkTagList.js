import React, { Component } from 'react'
import { connect } from 'react-redux'
import LinkTag from './LinkTag'

const LinkTagList = ({ tags, tabName, activeTab }) => (
    <div role="tabpanel" className="tab-pane" style={{ display: activeTab=="links" ? "inline" : "none" }}>
        <div className="list-group">
            {tags.map( tag =>
                <LinkTag key={tag} name={tag}/>
            )}
        </div>
    </div>
)

export default connect((state) => ({
    activeTab: state.selection.selectedTab ? state.selection.selectedTab : "contacts"
}))(LinkTagList)