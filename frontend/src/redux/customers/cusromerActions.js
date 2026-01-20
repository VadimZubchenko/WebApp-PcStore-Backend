import * as ActionConstants from '../types/actionConstants'

// Sync actions

export const fetchCustomersSuccess = (customers) => {
  return {
    type: ActionConstants.FETCH_CUSTOMERS_SUCCESS,
    customers: customers,
  }
}
export const fetchCustomersFailed = (error) => {
  return {
    type: ActionConstants.FETCH_CUSTOMERS_FAILED,
    error: error,
  }
}

export const removeCustomerSuccess = () => {
  return {
    type: ActionConstants.REMOVE_CUSTOMER_SUCCESS,
  }
}
export const removeCustomerFailed = (error) => {
  return {
    type: ActionConstants.REMOVE_CUSTOMER_FAILED,
    error: error,
  }
}
export const editCustomerSuccess = () => {
  return {
    type: ActionConstants.EDIT_CUSTOMER_SUCCESS,
  }
}
export const editCustomerFailed = (error) => {
  return {
    type: ActionConstants.EDIT_CUSTOMER_FAILED,
    error: error,
  }
}
