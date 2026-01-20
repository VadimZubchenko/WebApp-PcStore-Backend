import * as ActionConstants from '../types/actionConstants'

// Sync action creators

export const loading = () => {
  return {
    type: ActionConstants.LOADING,
  }
}

export const stopLoading = () => {
  return {
    type: ActionConstants.STOP_LOADING,
  }
}

export const registerSuccess = () => {
  return {
    type: ActionConstants.REGISTER_SUCCESS,
  }
}

export const registerFailed = (error) => {
  return {
    type: ActionConstants.REGISTER_FAILED,
    error: error,
  }
}

export const loginSuccess = (data) => {
  return {
    type: ActionConstants.LOGIN_SUCCESS,
    data: data,
  }
}
export const loginFailed = (error) => {
  return {
    type: ActionConstants.LOGIN_FAILED,
    error: error,
  }
}

export const logoutSuccess = () => {
  return {
    type: ActionConstants.LOGOUT_SUCCESS,
  }
}

export const logoutFailed = (error) => {
  return {
    type: ActionConstants.LOGOUT_FAILED,
    error: error,
  }
}

export const clearLoginState = () => {
  return {
    type: ActionConstants.CLEAR_LOGIN_STATE,
  }
}

export const clearShoppingState = () => {
  return {
    type: ActionConstants.CLEAR_SHOPPING_STATE,
  }
}
