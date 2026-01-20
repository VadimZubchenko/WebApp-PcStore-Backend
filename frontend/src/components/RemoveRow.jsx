const RemoveRow = (props) => {
  return (
    <tr>
      <td className="text-center">{props.customer.customerID}</td>
      <td className="text-start">{props.customer.customerName}</td>
      <td className="text-start">{props.customer.address}</td>
      <td className="text-start">{props.customer.email}</td>
      <td>
        <button
          className="btn btn-outline-secondary"
          onClick={() => props.cancel()}
        >
          Cancel
        </button>
      </td>
      <td>
        <button
          className="btn btn-outline-success"
          onClick={() => props.removeCustomer(props.customer.customerID)}
        >
          Confirm
        </button>
      </td>
    </tr>
  );
};
export default RemoveRow;
