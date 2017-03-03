import React, { Component } from 'react'

const LinkTagList = ({ linkTags, tabName }) => (
    <div role="tabpanel" className="tab-pane" id={tabName}>
        <ul className="list-group" id="taglist">
            {linkTags.map( linkTag =>
                <li key={linkTag.name} className="list-group-item tag-item active" data={linkTag.name}>
                    {linkTag.name}
                </li>
            )}

        </ul>
    </div>
)

export default LinkTagList