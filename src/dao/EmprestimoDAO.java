package src.dao;

import src.model.Emprestimo;
import src.model.Emprestimo.StatusEmprestimo;
import src.model.Aluno;
import src.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados para a entidade Emprestimo.
 */
public class EmprestimoDAO {
    private Connection conexao;
    private AlunoDAO alunoDAO;
    private LivroDAO livroDAO;

    public EmprestimoDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
        this.alunoDAO = new AlunoDAO();
        this.livroDAO = new LivroDAO();
    }

    /**
     * Insere um novo empréstimo no banco de dados.
     * @param emprestimo o empréstimo a ser inserido
     * @return o empréstimo com o ID gerado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Emprestimo inserir(Emprestimo emprestimo) throws SQLException {
        // Verifica se o livro está disponível
        Livro livro = livroDAO.buscarPorId(emprestimo.getLivroId());
        if (livro == null) {
            throw new SQLException("Livro não encontrado.");
        }
        
        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new SQLException("Livro não disponível para empréstimo.");
        }
        
        // Verifica se o aluno existe
        Aluno aluno = alunoDAO.buscarPorId(emprestimo.getAlunoId());
        if (aluno == null) {
            throw new SQLException("Aluno não encontrado.");
        }
        
        // Inicia transação
        boolean autoCommit = conexao.getAutoCommit();
        conexao.setAutoCommit(false);
        
        try {
            // Insere o empréstimo
            String sql = "INSERT INTO emprestimos (aluno_id, livro_id, data_emprestimo, " +
                         "data_devolucao_prevista, status) VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, emprestimo.getAlunoId());
                stmt.setInt(2, emprestimo.getLivroId());
                stmt.setTimestamp(3, emprestimo.getDataEmprestimo());
                stmt.setTimestamp(4, emprestimo.getDataDevolucaoPrevista());
                stmt.setString(5, emprestimo.getStatus().name());
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Falha ao inserir empréstimo, nenhuma linha afetada.");
                }
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        emprestimo.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Falha ao inserir empréstimo, nenhum ID obtido.");
                    }
                }
            }
            
            // Atualiza a quantidade disponível do livro
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
            livroDAO.atualizarQuantidadeDisponivel(livro.getId(), livro.getQuantidadeDisponivel());
            
            // Confirma a transação
            conexao.commit();
            
            // Busca o empréstimo completo
            Emprestimo emprestimoCompleto = buscarPorId(emprestimo.getId());
            emprestimoCompleto.setAluno(aluno);
            emprestimoCompleto.setLivro(livro);
            
            return emprestimoCompleto;
        } catch (SQLException e) {
            // Reverte a transação em caso de erro
            conexao.rollback();
            throw e;
        } finally {
            // Restaura o modo de auto-commit
            conexao.setAutoCommit(autoCommit);
        }
    }

    /**
     * Registra a devolução de um livro.
     * @param id o ID do empréstimo
     * @param dataDevolucao a data de devolução efetiva
     * @return o empréstimo atualizado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Emprestimo registrarDevolucao(int id, Timestamp dataDevolucao) throws SQLException {
        // Busca o empréstimo
        Emprestimo emprestimo = buscarPorId(id);
        if (emprestimo == null) {
            throw new SQLException("Empréstimo não encontrado.");
        }
        
        // Verifica se o empréstimo já foi devolvido
        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new SQLException("Este livro já foi devolvido.");
        }
        
        // Busca o livro
        Livro livro = livroDAO.buscarPorId(emprestimo.getLivroId());
        if (livro == null) {
            throw new SQLException("Livro não encontrado.");
        }
        
        // Inicia transação
        boolean autoCommit = conexao.getAutoCommit();
        conexao.setAutoCommit(false);
        
        try {
            // Determina o status com base na data de devolução
            StatusEmprestimo status = (dataDevolucao.after(emprestimo.getDataDevolucaoPrevista())) ? 
                                     StatusEmprestimo.ATRASADO : StatusEmprestimo.DEVOLVIDO;
            
            // Atualiza o empréstimo
            String sql = "UPDATE emprestimos SET data_devolucao_efetiva = ?, status = ? WHERE id = ?";
            
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setTimestamp(1, dataDevolucao);
                stmt.setString(2, status.name());
                stmt.setInt(3, id);
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new SQLException("Falha ao atualizar empréstimo, nenhuma linha afetada.");
                }
            }
            
            // Atualiza a quantidade disponível do livro
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
            livroDAO.atualizarQuantidadeDisponivel(livro.getId(), livro.getQuantidadeDisponivel());
            
            // Confirma a transação
            conexao.commit();
            
            // Busca o empréstimo atualizado
            return buscarPorId(id);
        } catch (SQLException e) {
            // Reverte a transação em caso de erro
            conexao.rollback();
            throw e;
        } finally {
            // Restaura o modo de auto-commit
            conexao.setAutoCommit(autoCommit);
        }
    }

    /**
     * Busca um empréstimo pelo ID.
     * @param id o ID do empréstimo a ser buscado
     * @return o empréstimo encontrado ou null se não existir
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emprestimos WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Emprestimo emprestimo = mapearResultSet(rs);
                    
                    // Carrega aluno e livro associados
                    emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                    emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                    
                    return emprestimo;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Lista todos os empréstimos.
     * @return lista de empréstimos
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Emprestimo> listarTodos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos ORDER BY data_emprestimo DESC";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Emprestimo emprestimo = mapearResultSet(rs);
                
                // Carrega aluno e livro associados
                emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                
                emprestimos.add(emprestimo);
            }
        }
        
        return emprestimos;
    }

    /**
     * Lista empréstimos ativos (não devolvidos).
     * @return lista de empréstimos ativos
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Emprestimo> listarAtivos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE status = 'ATIVO' ORDER BY data_devolucao_prevista";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Emprestimo emprestimo = mapearResultSet(rs);
                
                // Carrega aluno e livro associados
                emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                
                emprestimos.add(emprestimo);
            }
        }
        
        return emprestimos;
    }

    /**
     * Lista empréstimos atrasados.
     * @return lista de empréstimos atrasados
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Emprestimo> listarAtrasados() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE (status = 'ATIVO' AND data_devolucao_prevista < NOW()) " +
                     "OR status = 'ATRASADO' ORDER BY data_devolucao_prevista";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Emprestimo emprestimo = mapearResultSet(rs);
                
                // Carrega aluno e livro associados
                emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                
                emprestimos.add(emprestimo);
            }
        }
        
        return emprestimos;
    }

    /**
     * Lista empréstimos de um aluno.
     * @param alunoId o ID do aluno
     * @return lista de empréstimos do aluno
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Emprestimo> listarPorAluno(int alunoId) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE aluno_id = ? ORDER BY data_emprestimo DESC";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emprestimo = mapearResultSet(rs);
                    
                    // Carrega aluno e livro associados
                    emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                    emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                    
                    emprestimos.add(emprestimo);
                }
            }
        }
        
        return emprestimos;
    }

    /**
     * Lista empréstimos de um livro.
     * @param livroId o ID do livro
     * @return lista de empréstimos do livro
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Emprestimo> listarPorLivro(int livroId) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE livro_id = ? ORDER BY data_emprestimo DESC";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emprestimo = mapearResultSet(rs);
                    
                    // Carrega aluno e livro associados
                    emprestimo.setAluno(alunoDAO.buscarPorId(emprestimo.getAlunoId()));
                    emprestimo.setLivro(livroDAO.buscarPorId(emprestimo.getLivroId()));
                    
                    emprestimos.add(emprestimo);
                }
            }
        }
        
        return emprestimos;
    }

    /**
     * Mapeia um ResultSet para um objeto Emprestimo.
     * @param rs o ResultSet contendo os dados do empréstimo
     * @return o objeto Emprestimo mapeado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    private Emprestimo mapearResultSet(ResultSet rs) throws SQLException {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(rs.getInt("id"));
        emprestimo.setAlunoId(rs.getInt("aluno_id"));
        emprestimo.setLivroId(rs.getInt("livro_id"));
        emprestimo.setDataEmprestimo(rs.getTimestamp("data_emprestimo"));
        emprestimo.setDataDevolucaoPrevista(rs.getTimestamp("data_devolucao_prevista"));
        emprestimo.setDataDevolucaoEfetiva(rs.getTimestamp("data_devolucao_efetiva"));
        
        // Converte a string do status para o enum
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            emprestimo.setStatus(StatusEmprestimo.valueOf(statusStr));
        }
        
        return emprestimo;
    }
}