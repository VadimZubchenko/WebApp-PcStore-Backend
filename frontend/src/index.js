// React core libraries

import React from 'react'
import ReactDOM from 'react-dom/client'

// Main App component and utilities

import App from './App'
import reportWebVitals from './reportWebVitals' // For measuring performance metrics
import { BrowserRouter as Router } from 'react-router-dom'

// Context API

import { createContext } from 'react'
import OrderDetailStore from './store/OrderDetailStore' // Custom store for managing order details

// Redux

import { Provider } from 'react-redux'
import store from './redux/redux_store'

// Creating a React Context to share OrderDetailStore globally in the app
export const Context = createContext(null)

const root = ReactDOM.createRoot(document.getElementById('root'))

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <Context.Provider value={{ parts: new OrderDetailStore() }}>
        <Router>
          <App />
        </Router>
      </Context.Provider>
    </Provider>
  </React.StrictMode>
)

// For measuring app performance and optionally reporting to analytics
reportWebVitals()
