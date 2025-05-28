CREATE DATABASE biblioteca;

USE biblioteca;

CREATE TABLE Alunos (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome_aluno VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) UNIQUE,
    data_nascimento DATE
);

CREATE TABLE Livros (
    id_livro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100),
    ano_publicacao INT,
    quantidade_estoque INT DEFAULT 0
);

CREATE TABLE Emprestimos (
    id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT,
    id_livro INT,
    data_emprestimo DATE DEFAULT (CURRENT_DATE),
    data_devolucao DATE,
    FOREIGN KEY (id_aluno) REFERENCES Alunos(id_aluno),
    FOREIGN KEY (id_livro) REFERENCES Livros(id_livro)
);
