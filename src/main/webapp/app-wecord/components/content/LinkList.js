import React, { Component } from 'react'
import Link from './Link'

const LinkList = ({ links }) => (
    <div>
        {links.map(link =>
            <Link key={link.content} link={link.content} />
        )}
    </div>
)

export default LinkList