import { useState } from "react";

const EditRow = (props) => {
  const [state, setState] = useState({
    customerName: props.customer.customerName,
    address: props.customer.address,
    email: props.customer.email,
  });

  const onChange = (event) => {
    setState((state) => {
      return {
        ...state,
        [event.target.name]: event.target.value,
      };
    });
  };

  const editCustomer = (event) => {
    event.preventDefault();
    let customer = {
      ...state,
      customerID: props.customer.customerID,
    };
    props.editCustomer(customer);
  };
  return (
    <tr>
      <td className="text-center">{props.customer.customerID}</td>
      <td className="text-start">
        <input
          type="text"
          name="customerName"
          id="customerName"
          onChange={onChange}
          value={state.customerName}
        />
      </td>
      <td className="text-start">
        <input
          type="text"
          name="address"
          id="address"
          onChange={onChange}
          value={state.address}
        />
      </td>
      <td className="text-start">
        <input
          type="text"
          name="email"
          id="email"
          onChange={onChange}
          value={state.email}
        />
      </td>
      <td>
        <button className="btn btn-outline-success" onClick={editCustomer}>
          Save
        </button>
      </td>
      <td>
        <button
          className="btn btn-outline-danger"
          onClick={() => props.cancel()}
        >
          Cancel
        </button>
      </td>
    </tr>
  );
};

export default EditRow;
