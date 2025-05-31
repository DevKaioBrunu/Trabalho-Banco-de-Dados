import React, { useState } from 'react';
import axios from 'axios';

function AlunoForm() {
  const [nome, setNome] = useState('');
  const [matricula, setMatricula] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/alunos', {
        nome,
        matricula,
        dataNascimento
      });
      alert('Aluno cadastrado com sucesso!');
      setNome('');
      setMatricula('');
      setDataNascimento('');
    } catch (error) {
      alert('Erro ao cadastrar aluno: ' + error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Cadastro de Aluno</h2>
      <label>
        Nome:
        <input value={nome} onChange={(e) => setNome(e.target.value)} required />
      </label><br />
      <label>
        Matrícula:
        <input value={matricula} onChange={(e) => setMatricula(e.target.value)} required />
      </label><br />
      <label>
        Data de Nascimento:
        <input type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
      </label><br />
      <button type="submit">Cadastrar</button>
    </form>
  );
}

export default AlunoForm;