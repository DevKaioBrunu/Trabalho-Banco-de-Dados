package dao;

import model.Aluno;
import util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;

public class AlunoDAO {

    public void cadastrar(Aluno aluno) {
        String sql = "INSERT INTO Alunos (nome_aluno, matricula, data_nascimento) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));

            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }
}