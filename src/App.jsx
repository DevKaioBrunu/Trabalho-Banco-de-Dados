import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import AlunoForm from './components/AlunoForm';

function App() {
  return (
    <Router>
      <div style={{ padding: '20px' }}>
        <h1>Sistema da Biblioteca</h1>
        <nav>
          <Link to="/cadastro-aluno">Cadastrar Aluno</Link>
        </nav>
        <Routes>
          <Route path="/cadastro-aluno" element={<AlunoForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;