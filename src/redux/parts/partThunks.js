import {
  fetchListSuccess,
  fetchListFailed,
  addPartSuccess,
  addPartFailed,
} from './partActions'
import {
  loading,
  stopLoading,
  clearLoginState,
  clearShoppingState,
} from '../login/loginActions'

// Async Thunks
export const getPartList = (token) => async (dispatch) => {
  const request = {
    method: 'GET',
    mode: 'cors',
    headers: { 'Content-type': 'application/json', token },
  }

  dispatch(loading())
  const response = await fetch('/parts', request)
  dispatch(stopLoading())

  if (!response) {
    dispatch(fetchListFailed('Connection error! Failed to fetch parts.'))
    return
  }

  if (response.ok) {
    const data = await response.json()
    if (!data) {
      dispatch(fetchListFailed('Failed to parse the response!'))
      return
    }
    dispatch(fetchListSuccess(data))
  } else {
    if (response.status === 403) {
      dispatch(clearShoppingState())
      dispatch(clearLoginState())
      dispatch(fetchListFailed('Session expired. Logging out.'))
    } else {
      dispatch(fetchListFailed(`Server error: ${response.status}`))
    }
  }
}

export const addPart = (part, token) => async (dispatch) => {
  const request = {
    method: 'POST',
    mode: 'cors',
    headers: { 'Content-type': 'application/json', token },
    body: JSON.stringify(part),
  }

  dispatch(loading())
  const response = await fetch('/parts', request)
  dispatch(stopLoading())

  if (!response) {
    dispatch(addPartFailed('Connection error! Failed to add part.'))
    return
  }

  if (response.ok) {
    dispatch(addPartSuccess())
    dispatch(getPartList(token))
  } else {
    if (response.status === 403) {
      dispatch(clearShoppingState())
      dispatch(clearLoginState())
      dispatch(addPartFailed('Session expired. Logging out.'))
    } else {
      dispatch(addPartFailed(`Server error: ${response.status}`))
    }
  }
}
