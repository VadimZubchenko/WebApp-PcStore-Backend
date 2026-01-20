import React, { Component } from "react";

class HeaderComponent extends Component {
  render() {
    return (
      <div>
        <header>
          <nav className="navbar navbar-expand-sm bg-secondary navbar-dark">
            <div className="container-fluid">
              <a
                href="https://github.com/VadimZubchenko"
                className="navbar-brand"
              >
                Web Application PC Store
              </a>
            </div>
          </nav>
        </header>
      </div>
    );
  }
}

export default HeaderComponent;
