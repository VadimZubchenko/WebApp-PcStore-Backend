import loginReducer from './reducers/loginReducer' // Reducer for login-related state
import shoppingReducer from './reducers/shoppingReducer'
import { configureStore } from '@reduxjs/toolkit'

// Configure the Redux store using Redux Toolkit's configureStore
const redux_store = configureStore({
  reducer: {
    // Register reducers
    login: loginReducer,
    shopping: shoppingReducer,
  },
})

export default redux_store
