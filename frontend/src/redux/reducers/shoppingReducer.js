import * as ActionConstants from '../types/actionConstants'

const getInitialState = () => {
  if (sessionStorage.getItem('shoppingstate')) {
    let state = JSON.parse(sessionStorage.getItem('shoppingstate'))
    return state
  } else {
    return {
      list: [],
      orders: [],
      customers: [],
      error: '',
    }
  }
}

const saveToStorage = (state) => {
  sessionStorage.setItem('shoppingstate', JSON.stringify(state))
}

const initialState = getInitialState()

const shoppingReducer = (state = initialState, action) => {
  console.log('shoppingReducer, action:', action)
  let tempState = {}
  switch (action.type) {
    case ActionConstants.ADD_ORDER_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.ADD_ORDER_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.ADD_PART_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.ADD_PART_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.REMOVE_ORDER_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.REMOVE_ORDER_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.EDIT_ORDER_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.EDIT_ORDER_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.EDIT_CUSTOMER_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.EDIT_CUSTOMER_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_LIST_SUCCESS:
      tempState = {
        ...state,
        list: action.list,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_LIST_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_ORDERS_SUCCESS:
      tempState = {
        ...state,
        orders: action.orders,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_ORDERS_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_CUSTOMERS_SUCCESS:
      tempState = {
        ...state,
        customers: action.customers,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.FETCH_CUSTOMERS_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState

    case ActionConstants.REMOVE_CUSTOMER_SUCCESS:
      tempState = {
        ...state,
        error: '',
      }
      saveToStorage(tempState)
      return tempState

    case ActionConstants.REMOVE_CUSTOMER_FAILED:
      tempState = {
        ...state,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState

    case ActionConstants.CLEAR_SHOPPING_STATE:
      tempState = {
        list: [],
        orders: [],
        customers: [],
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    default:
      return state
  }
}

export default shoppingReducer
