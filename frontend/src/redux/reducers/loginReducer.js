import * as ActionConstants from '../types/actionConstants'

const getInitialState = () => {
  if (sessionStorage.getItem('loginstate')) {
    let state = JSON.parse(sessionStorage.getItem('loginstate'))
    return state
  } else {
    return {
      isLogged: false,
      loading: false,
      token: '',
      error: '',
    }
  }
}

const saveToStorage = (state) => {
  sessionStorage.setItem('loginstate', JSON.stringify(state))
}

const initialState = getInitialState()

const loginReducers = (state = initialState, action) => {
  console.log('loginReducer, action:', action)
  let tempState = {}
  switch (action.type) {
    case ActionConstants.LOADING:
      return {
        ...state,
        loading: true,
        error: '',
      }
    case ActionConstants.STOP_LOADING:
      return {
        ...state,
        loading: false,
      }
    case ActionConstants.REGISTER_SUCCESS:
      tempState = {
        ...state,
        loading: false,
        error: 'Register success!',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.REGISTER_FAILED:
      tempState = {
        ...state,
        loading: false,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.LOGIN_SUCCESS:
      tempState = {
        ...state,
        isLogged: true,
        token: action.data.token,
        staff: action.data.staffLogin,
        role: action.data.role,
        loading: false,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.LOGIN_FAILED:
      tempState = {
        ...state,
        loading: false,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.LOGOUT_SUCCESS:
      tempState = {
        isLogged: false,
        token: '',
        loading: false,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.LOGOUT_FAILED:
      tempState = {
        isLogged: false,
        token: '',
        loading: false,
        error: action.error,
      }
      saveToStorage(tempState)
      return tempState
    case ActionConstants.CLEAR_LOGIN_STATE:
      tempState = {
        isLogged: false,
        token: '',
        loading: false,
        error: '',
      }
      saveToStorage(tempState)
      return tempState
    default:
      return state
  }
}

export default loginReducers
