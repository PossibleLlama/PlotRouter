import React from 'react';
import logo from './logo.svg';
import './App.css';

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <nav role="navigation">
              <a
                className="App-link"
                href="#"
              ><h1>PlotRouter</h1></a>
              <a
                className="App-link"
                href="#"
              ><h2>Events</h2></a>
        </nav>
        <img src={logo} className="App-logo" alt="logo" />
      </header>

      <footer>
        <p>Created by PossibleLlama</p>
      </footer>
    </div>
  );
}

export default App;
