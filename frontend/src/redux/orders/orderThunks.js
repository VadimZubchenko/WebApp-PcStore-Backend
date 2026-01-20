import {
  fetchOrdersSuccess,
  fetchOrdersFailed,
  addOrderSuccess,
  addOrderFailed,
  removeOrderSuccess,
  removeOrderFailed,
  editOrderSuccess,
  editOrderFailed,
} from './orderActions'
import {
  loading,
  stopLoading,
  clearLoginState,
  clearShoppingState,
} from '../login/loginActions'
import { getPartList } from '../parts/partThunks'
import { getCustomersList } from '../customers/customerThunks'

// Async thunks

export const getOrdersList = (token) => {
  return async (dispatch) => {
    let request = {
      method: 'GET',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
    }
    dispatch(loading())
    let response = await fetch('/orders', request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        fetchOrdersFailed(
          'There was an error with the connection. Fetching shoppinglist failed!'
        )
      )
      return
    }
    if (response.ok) {
      let data = await response.json()
      if (!data) {
        dispatch(fetchOrdersFailed('Failed to parse shoppinglist!'))
        return
      }
      dispatch(fetchOrdersSuccess(data))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(
          fetchOrdersFailed('Your session has expired. Logging you out!')
        )
      } else {
        dispatch(
          fetchOrdersFailed('Server responded with a status:' + response.status)
        )
      }
    }
  }
}

export const addOrder = (order, token) => {
  return async (dispatch) => {
    let request = {
      method: 'POST',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
      body: JSON.stringify(order),
    }
    dispatch(loading())
    let response = await fetch('/orders', request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        addOrderFailed(
          'There was an error with the connectioin. Add new order failed'
        )
      )
      return
    }
    if (response.ok) {
      dispatch(addOrderSuccess())
      dispatch(getPartList(token))
      dispatch(getOrdersList(token))
      dispatch(getCustomersList(token))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(addOrderFailed('Your session was expired. Logging you out'))
      } else {
        dispatch(
          addOrderFailed('Server responses with status: ' + response.status)
        )
      }
    }
  }
}
export const removeOrder = (orderID, token) => {
  return async (dispatch) => {
    let request = {
      method: 'DELETE',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
    }
    dispatch(loading())
    let response = await fetch('/orders/' + orderID, request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        removeOrderFailed(
          'There was an error with the connectioin. Remove order failed'
        )
      )
      return
    }
    if (response.ok) {
      dispatch(removeOrderSuccess())
      dispatch(getPartList(token))
      dispatch(getOrdersList(token))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(removeOrderFailed('Your session was expired. Logging you out'))
      } else {
        dispatch(
          removeOrderFailed('Server responses with status: ' + response.status)
        )
      }
    }
  }
}
export const editOrder = (order, token) => {
  return async (dispatch) => {
    let request = {
      method: 'PUT',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
      body: JSON.stringify(order),
    }
    dispatch(loading())
    let response = await fetch('/orders', request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        editOrderFailed(
          'There was an error with the connectioin. Edit order failed'
        )
      )
      return
    }
    if (response.ok) {
      dispatch(editOrderSuccess())
      dispatch(getPartList(token))
      dispatch(getOrdersList(token))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(editOrderFailed('Your session was expired. Logging you out'))
      } else {
        dispatch(
          editOrderFailed('Server responses with status: ' + response.status)
        )
      }
    }
  }
}
