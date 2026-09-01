const Row = (props) => {
  let deleteBtn = (
    <button className="btn btn-outline-danger disabled">Remove</button>
  );

  if (props.role === "admin") {
    deleteBtn = (
      <button
        className="btn btn-outline-danger"
        onClick={() => props.changeToRemoveMode(props.index)}
      >
        Remove
      </button>
    );
  }

  return (
    <tr>
      <td className="text-center pt-3">{props.customer.customerID}</td>
      <td className="text-start pt-3">{props.customer.customerName}</td>
      <td className="text-start pt-3">{props.customer.address}</td>
      <td className="text-start pt-3">{props.customer.email}</td>
      <td>{deleteBtn}</td>
      <td>
        <button
          className="btn btn-outline-primary"
          onClick={() => props.changeToEditMode(props.index)}
        >
          Edit
        </button>
      </td>
    </tr>
  );
};

export default Row;
