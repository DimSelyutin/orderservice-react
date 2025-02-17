import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import CreateOrderPage from './components/OrdersScreen';
import FormOrder from './components/FormOrder';
import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <header className="bg-light">
          <nav className="navbar navbar-expand-lg navbar-light">
            <div className="container ">
              <Link className="navbar-brand" to="/">OrderService</Link>
              <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
              </button>
              <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav">
                  <li className="nav-item">
                    <Link className="nav-link" to="/">Все заказы</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/create">Создать Заказ</Link>
                  </li>

                </ul>
              </div>
            </div>
          </nav>
        </header>

        <main>
          <Routes>
            <Route path="/" Component={CreateOrderPage} />
            <Route path="/create" Component={FormOrder} />

          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
