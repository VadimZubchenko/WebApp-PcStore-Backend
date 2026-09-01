import { useState } from 'react'
import RemoveOrderRow from './RemoveOrderRow'
import OrderRow from './OrderRow'
import RemovePartRow from './RemovePartRow'
import PartRow from './PartRow'
import EditPartRow from './EditPartRow'
import { useSelector, useDispatch } from 'react-redux'
import { removeOrder, editOrder } from '../redux'

const OrdersDetails = (props) => {
  const orderList = useSelector((state) => state.shopping.orders)

  const token = useSelector((state) => state.login.token)

  const dispatch = useDispatch()

  //state with array of the all parts of selected orderID in orders table
  const [state, setState] = useState({
    order: [],
    parts: [],
  })

  //state of row in the Orders table
  const [modeOrderRow, setMode] = useState({
    removeIndex: -1,
  })
  //state of raw in the Order Details table
  const [modePartRow, setModePartRow] = useState({
    removePartRowIndex: -1,
    editPartRowIndex: -1,
  })
  //change row index in Orders table
  const changeToRemoveMode = (index) => {
    setMode({
      removeIndex: index,
    })
  }

  //cancel index of selected row in Orders table
  const cancel = () => {
    setMode({
      removeIndex: -1,
    })
  }
  //change the row index in Order Details table
  const changePartsRowToRemoveMode = (index) => {
    setModePartRow({
      removePartRowIndex: index,
      editPartRowIndex: -1,
    })
  }
  const changePartsRowToEditMode = (index) => {
    setModePartRow({
      removePartRowIndex: -1,
      editPartRowIndex: index,
    })
  }
  //cancel index of select raw in Order Details table
  const cancelPartRow = () => {
    setModePartRow({
      removePartRowIndex: -1,
      editPartRowIndex: -1,
    })
  }

  const rmvFromOrderLst = (orderID) => {
    // Clear the order parts list after deleting
    setState((prevState) => ({
      ...prevState,
      parts: [], // Clear the parts list
    }))
    dispatch(removeOrder(orderID, token))
    cancel()
  }
  const editPartRow = (order, partRow) => {
    // Create a new array where the selected part of the order is updated immutably
    let updatedParts = state.parts.map((part) =>
      part.orderDetailID === partRow.orderDetailID
        ? {
            ...part,
            orderDetailQuantity: partRow.quantity,
            orderDetailPrice: partRow.price,
          }
        : part
    )
    // Recalculate the total price after removing the part
    let updatedPrice = updatedParts
      .reduce((sum, part) => sum + part.orderDetailPrice, 0)
      .toFixed(2) // Converts to string with 2 decimal places

    // Create a new order object with the updated total price
    let updatedOrder = {
      ...order,
      totalPrice: parseFloat(updatedPrice),
      orderDetails: updatedParts,
    }

    // Update local state to reflect the changes in the UI immediately
    setState((prevState) => ({
      ...prevState,
      parts: updatedParts, // Update the parts list
    }))
    dispatch(editOrder(updatedOrder, token))
    // Reset row editing state
    cancelPartRow()
  }

  const removePart = (order, partID) => {
    // Filter out the part that needs to be removed, keeping all others
    let updatedParts = state.parts.filter(
      (part) => part.orderDetailID !== partID
    )
    // Recalculate the total price after removing the part
    let updatedPrice = updatedParts
      .reduce((sum, part) => sum + part.orderDetailPrice, 0)
      .toFixed(2) // Converts to string with 2 decimal places

    // Create a new order object with the updated total price
    let updatedOrder = {
      ...order,
      totalPrice: parseFloat(updatedPrice),
      orderDetails: updatedParts,
    }

    // Update local state to reflect the changes in the UI immediately
    setState((prevState) => ({
      ...prevState,
      parts: updatedParts, // Update the parts list
    }))
    dispatch(editOrder(updatedOrder, token))
    cancelPartRow()
  }

  const selectedRow = (order) => {
    setState({
      order: order,
      parts: order.orderDetails,
    })
  }

  //array of all orders with included all parts inside of it
  let orders = orderList.length
    ? orderList.map((order, index) => {
        if (modeOrderRow.removeIndex === index) {
          return (
            <RemoveOrderRow
              key={order.orderID}
              order={order}
              removeOrder={rmvFromOrderLst}
              cancel={cancel}
            />
          )
        }

        return (
          <OrderRow
            key={order.orderID}
            order={order}
            index={index} // index of the row has been taken as a second argument of map-function from customer Array list
            selectedRow={selectedRow}
            changeToRemoveMode={changeToRemoveMode}
          />
        )
      })
    : null

  let orderDtl = state.parts.length
    ? state.parts.map((part, index) => {
        if (modePartRow.removePartRowIndex === index) {
          return (
            <RemovePartRow
              key={part.orderDetailID}
              part={part}
              order={state.order} //selected orderID of Orders table
              removePart={removePart}
              cancelPartRow={cancelPartRow}
            />
          )
        }
        if (modePartRow.editPartRowIndex === index) {
          return (
            <EditPartRow
              key={part.orderDetailID}
              part={part}
              order={state.order} //selected orderID of Orders table
              editPartRow={editPartRow}
              cancelPartRow={cancelPartRow}
            />
          )
        }
        return (
          <PartRow
            key={part.orderDetailID}
            part={part}
            index={index}
            changePartsRowToRemoveMode={changePartsRowToRemoveMode}
            changePartsRowToEditMode={changePartsRowToEditMode}
          />
        )
      })
    : null
  return (
    <div className="row mx-auto">
      <div className="col-6 mx-auto">
        <h2 className="text-center mt-4">Orders</h2>
        <div
          className="ag-theme-alpine mx-auto mb-3 card-box scrollable-table"
          style={{ height: 600, width: 800 }}
        >
          <table className="table table-striped">
            <thead className="th">
              <tr>
                <th>ID</th>
                <th>Customer</th>
                <th>Total price</th>
                <th>Date</th>
                <th>Staff</th>
                <th>Remove</th>
              </tr>
            </thead>
            <tbody>{orders}</tbody>
          </table>
          {props.errorMsg ? <div>{props.errorMsg}</div> : null}
        </div>
      </div>
      <div className="col-6 mx-auto">
        <h2 className="text-center mt-4">Order Details</h2>
        <div
          className="ag-theme-alpine mx-auto mb-3 card-box scrollable-table"
          style={{ height: 600, width: 800 }}
        >
          <table className="table table-striped">
            <thead className="th">
              <tr>
                <th>ID</th>
                <th>Part Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Remove</th>
                <th>Edit</th>
              </tr>
            </thead>
            <tbody>{orderDtl}</tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
export default OrdersDetails
