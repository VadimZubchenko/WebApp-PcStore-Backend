import * as ActionConstants from '../types/actionConstants'

// Sync Action Creators
export const fetchListSuccess = (list) => ({
  type: ActionConstants.FETCH_LIST_SUCCESS,
  list: list,
})

export const fetchListFailed = (error) => ({
  type: ActionConstants.FETCH_LIST_FAILED,
  error: error,
})

export const addPartSuccess = () => ({
  type: ActionConstants.ADD_PART_SUCCESS,
})

export const addPartFailed = (error) => ({
  type: ActionConstants.ADD_PART_FAILED,
  error: error,
})
