import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import thunk from 'redux-thunk'
import createLogger from 'redux-logger'
import reducers from './reducers'
import App from './components/App'

import { createStore, applyMiddleware } from 'redux'

const middleware = [ thunk, createLogger() ]

const store = createStore(
    reducers,
    applyMiddleware(...middleware)
)

render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById("root")
)