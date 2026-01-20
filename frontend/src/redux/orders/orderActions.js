import * as ActionConstants from '../types/actionConstants'

// Sync Action Creators

export const fetchOrdersSuccess = (orders) => {
  return {
    type: ActionConstants.FETCH_ORDERS_SUCCESS,
    orders: orders,
  }
}

export const fetchOrdersFailed = (error) => {
  return {
    type: ActionConstants.FETCH_ORDERS_FAILED,
    error: error,
  }
}

export const addOrderSuccess = () => {
  return {
    type: ActionConstants.ADD_ORDER_SUCCESS,
  }
}
export const addOrderFailed = (error) => {
  return {
    type: ActionConstants.ADD_ORDER_FAILED,
    error: error,
  }
}
export const removeOrderSuccess = () => {
  return {
    type: ActionConstants.REMOVE_ORDER_SUCCESS,
  }
}
export const removeOrderFailed = (error) => {
  return {
    type: ActionConstants.REMOVE_ORDER_FAILED,
    error: error,
  }
}

export const editOrderSuccess = () => {
  return {
    type: ActionConstants.EDIT_ORDER_SUCCESS,
  }
}
export const editOrderFailed = (error) => {
  return {
    type: ActionConstants.EDIT_ORDER_FAILED,
    error: error,
  }
}
