import React, { useContext, useEffect, useState } from "react";
import { Context } from "../index";
import { observer } from "mobx-react-lite";

const AddCustomerForm = observer((props) => {
  const { parts } = useContext(Context);

  const [state, setState] = useState({
    customerName: "",
    address: "",
    email: "",
  });

  /* create a class property with arrow function, that doesn't have own 'this.'
     and uses object of includes this arrow function class.
    It makes possible to avoid binding the arrow function in constructor */
  const changeHandler = (event) => {
    /* 1. no need separates handlers for differents input elements,
          because [event.target.name] takes the names of state from 'name' attribute in XML 
      2. extract the input element from event parameter */
    setState((state) => {
      return {
        ...state,
        [event.target.name]: event.target.value,
      };
    });
  };

  const cancel = (event) => {
    event.preventDefault();
    setState({
      customerName: "",
      address: "",
      email: "",
    });
    console.log("Value from shop:", props.value);
  };

  useEffect(() => {
    parts.setNewCustomer(state);
  }, [state]);

  // to order for clearing customer form from parent class
  useEffect(() => {
    setState(props.value); //set empty state from parent class
  }, [props.value]);

  const { customerName, address, email } = state;
  return (
    <div
      className="ag-theme-alpine mx-auto mt-3"
      style={{ height: 400, width: 500 }}
    >
      <h2 className="text-center">Customer</h2>
      <div className="p-3 card-box">
        <div className="form-group was-validated">
          <div className="mb-3 mt-3">
            <input
              type="text"
              className="form-control"
              id="customerName"
              placeholder="Enter customer name"
              name="customerName"
              required
              value={customerName}
              /* onChange make possible user to write text on input element */
              onChange={changeHandler}
            />
            <div className="valid-feedback">Valid</div>
            <div className="invalid-feedback">Please fill out this field</div>
          </div>
          <div className="mb-3">
            <input
              type="text"
              className="form-control"
              id="address"
              placeholder="Enter address"
              name="address"
              required
              value={address}
              onChange={changeHandler}
            />
            <div className="valid-feedback">Valid</div>
            <div className="invalid-feedback">Please fill out this field</div>
          </div>
          <div className="mb-3">
            <input
              type="email"
              className="form-control"
              id="email"
              placeholder="Enter email "
              name="email"
              required
              value={email}
              onChange={changeHandler}
            />
            <div className="valid-feedback">Valid</div>
            <div className="invalid-feedback">Please fiil out this field</div>
          </div>
        </div>
        <button
          className="btn btn-outline-secondary"
          onClick={cancel}
          style={{ marginLeft: "10px" }}
        >
          Cancel
        </button>
      </div>
    </div>
  );
});

export default AddCustomerForm;
