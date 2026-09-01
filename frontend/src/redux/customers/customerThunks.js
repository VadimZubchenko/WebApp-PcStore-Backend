import {
  fetchCustomersFailed,
  fetchCustomersSuccess,
  removeCustomerFailed,
  removeCustomerSuccess,
  editCustomerFailed,
  editCustomerSuccess,
} from './cusromerActions'
import {
  loading,
  stopLoading,
  clearLoginState,
  clearShoppingState,
} from '../login/loginActions'
import { getOrdersList } from '../orders/orderThunks'
import { getPartList } from '../parts/partThunks'

// Async thunks

export const getCustomersList = (token) => {
  return async (dispatch) => {
    let request = {
      method: 'GET',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
    }
    dispatch(loading())
    let response = await fetch('/api/customers', request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        fetchCustomersFailed(
          'There was an error with the connection. Fetching shoppinglist failed!'
        )
      )
      return
    }
    if (response.ok) {
      let data = await response.json()
      if (!data) {
        dispatch(fetchCustomersFailed('Failed to parse shoppinglist!'))
        return
      }
      dispatch(fetchCustomersSuccess(data))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(
          fetchCustomersFailed('Your session has expired. Logging you out!')
        )
      } else {
        dispatch(
          fetchCustomersFailed(
            'Server responded with a status:' + response.status
          )
        )
      }
    }
  }
}
export const removeCustomer = (customerID, token) => {
  return async (dispatch) => {
    let request = {
      method: 'DELETE',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
    }
    dispatch(loading())
    let response = await fetch('/api/customers/' + customerID, request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        removeCustomerFailed(
          'There was an error with the connectioin. Remove order failed'
        )
      )
      return
    }
    if (response.ok) {
      dispatch(removeCustomerSuccess())
      dispatch(getCustomersList(token))
      dispatch(getOrdersList(token))
      dispatch(getPartList(token))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(
          removeCustomerFailed('Your session was expired. Logging you out')
        )
      } else {
        dispatch(
          removeCustomerFailed(
            'Server responses with status: ' + response.status
          )
        )
      }
    }
  }
}
export const editCustomer = (customer, token) => {
  return async (dispatch) => {
    let request = {
      method: 'PUT',
      mode: 'cors',
      headers: { 'Content-type': 'application/json', token: token },
      body: JSON.stringify(customer),
    }
    dispatch(loading())
    let response = await fetch('/api/customers/' + customer.customerID, request)
    dispatch(stopLoading())
    if (!response) {
      dispatch(
        editCustomerFailed(
          'There was an error with the connectioin. Edit order failed'
        )
      )
      return
    }
    if (response.ok) {
      dispatch(editCustomerSuccess())
      dispatch(getCustomersList(token))
    } else {
      if (response.status === 403) {
        dispatch(clearShoppingState())
        dispatch(clearLoginState())
        dispatch(
          editCustomerFailed('Your session was expired. Logging you out')
        )
      } else {
        dispatch(
          editCustomerFailed('Server responses with status: ' + response.status)
        )
      }
    }
  }
}
