package dao;

import java.sql.*;
import model.Emprestimo;
import util.ConnectionFactory;

public class EmprestimoDAO {
    public void registrarEmprestimo(Emprestimo emp) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {

            // Verifica estoque
            String verificaEstoque = "SELECT quantidade_estoque FROM Livros WHERE id_livro = ?";
            PreparedStatement checkStmt = conn.prepareStatement(verificaEstoque);
            checkStmt.setInt(1, emp.getIdLivro());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("quantidade_estoque") > 0) {
                // Atualiza estoque
                String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id_livro = ?";
                PreparedStatement updateStmt = conn.prepareStatement(atualizaEstoque);
                updateStmt.setInt(1, emp.getIdLivro());
                updateStmt.executeUpdate();

                // Registra empréstimo
                String sql = "INSERT INTO Emprestimos (id_aluno, id_livro, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, emp.getIdAluno());
                stmt.setInt(2, emp.getIdLivro());
                stmt.setDate(3, Date.valueOf(emp.getDataEmprestimo()));
                stmt.setDate(4, Date.valueOf(emp.getDataDevolucao()));
                stmt.executeUpdate();
            } else {
                System.out.println("Livro sem estoque disponível.");
            }
        }
    }
}
