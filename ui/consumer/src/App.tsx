import React from 'react';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
          <Navbar collapseOnSelect sticky="top" expand="lg" role="navigation">
            <Navbar.Brand href="#home" >Plot Router</Navbar.Brand>
            <Navbar.Toggle aria-controls="navbar-nav" />
            <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="#Events">Events</Nav.Link>
            </Nav>
            </Navbar.Collapse>
          </Navbar>
        <img src={logo} className="App-logo" alt="logo" />
      </header>

      <footer>
        <p>Created by PossibleLlama</p>
      </footer>
    </div> // App
  );
}

export default App;
