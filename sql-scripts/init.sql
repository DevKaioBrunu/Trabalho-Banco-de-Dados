-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Tabela de alunos
CREATE TABLE IF NOT EXISTS alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de livros
CREATE TABLE IF NOT EXISTS livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    editora VARCHAR(100),
    ano_publicacao INT,
    isbn VARCHAR(20) UNIQUE,
    quantidade_total INT NOT NULL DEFAULT 1,
    quantidade_disponivel INT NOT NULL DEFAULT 1,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id INT NOT NULL,
    livro_id INT NOT NULL,
    data_emprestimo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_devolucao_prevista TIMESTAMP NOT NULL,
    data_devolucao_efetiva TIMESTAMP NULL,
    status ENUM('ATIVO', 'DEVOLVIDO', 'ATRASADO') DEFAULT 'ATIVO',
    FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    FOREIGN KEY (livro_id) REFERENCES livros(id)
);

-- Índices para melhorar a performance
CREATE INDEX idx_emprestimos_aluno ON emprestimos(aluno_id);
CREATE INDEX idx_emprestimos_livro ON emprestimos(livro_id);
CREATE INDEX idx_emprestimos_status ON emprestimos(status);