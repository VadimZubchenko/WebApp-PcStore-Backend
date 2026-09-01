import './App.css'
import { Routes, Route, Navigate } from 'react-router-dom'
import Navbar from './components/Navbar'
import LoginPage from './pages/LoginPage'
import CustomerListComponent from './components/CustomerListComponent'
import ShopPage from './pages/ShopPage'
import FooterComponent from './components/FooterComponent'
import AddParts from './components/AddParts'
import OrdersDetails from './components/OrdersDetails'
import { useSelector } from 'react-redux'
import { useMemo } from 'react'

function App() {
  // Retrieve authentication and loading states from Redux
  const isLogged = useSelector((state) => state.login.isLogged)
  const loading = useSelector((state) => state.login.loading)
  const error = useSelector(
    (state) => state.shopping.error || state.login.error
  )

  // Helper function to display messages (loading or errors)
  const getMessageArea = () => {
    if (loading) {
      return (
        <h5 className="alert alert-info opacity-75 scroll-top m-0">
          Loading...
        </h5>
      )
    }
    if (error) {
      return (
        <h5 className="alert alert-info opacity-75 alert-dismissable m-0">
          {error}
        </h5>
      )
    }
    return null
  }

  // Memoized routes to optimize rendering
  const routes = useMemo(
    () => (
      <Routes>
        {isLogged ? (
          <>
            <Route exact path="/" element={<ShopPage />} />
            <Route path="/orders" element={<OrdersDetails />} />
            <Route path="/parts" element={<AddParts />} />
            <Route path="/customers" element={<CustomerListComponent />} />
          </>
        ) : (
          <Route exact path="/" element={<LoginPage />} />
        )}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    ),
    [isLogged]
  )

  return (
    <div className="container-fluid">
      {/* Header with navigation */}
      <header>
        <Navbar />
      </header>

      {/* Display loading or error messages */}
      <div className="text-center">{getMessageArea()}</div>

      {/* Main content area */}
      <div className="container-fluid bd-gutter grey-background align-items-start card-box">
        {routes}
      </div>

      {/* Footer */}
      <footer className="footer bd-footer py-2 py-md-3 mt-4 bg-black position-relative">
        <FooterComponent />
      </footer>
    </div>
  )
}

export default App
