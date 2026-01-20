import { makeAutoObservable } from "mobx";

class OrderDetailStore {
  constructor() {
    this._parts = [];
    this._customers = [];

    this._selectedPart = {};
    this._newCustomer = {};

    makeAutoObservable(this);
  }
  // set-function changes the state of variables
  setSelectedPart(parts) {
    this._selectedPart = parts;
  }
  setNewCustomer(customers) {
    this._newCustomer = customers;
  }

  // get-functions can be called when variables inside component had been changed (optimization)
  get selectedPart() {
    return this._selectedPart;
  }

  get newCustomer() {
    return this._newCustomer;
  }
}
export default OrderDetailStore;
