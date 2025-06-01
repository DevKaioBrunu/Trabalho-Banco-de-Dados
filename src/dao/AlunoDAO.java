package src.dao;

import src.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados para a entidade Aluno.
 */
public class AlunoDAO {
    private Connection conexao;

    public AlunoDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    /**
     * Insere um novo aluno no banco de dados.
     * @param aluno o aluno a ser inserido
     * @return o aluno com o ID gerado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Aluno inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (nome, matricula, email, telefone) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir aluno, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao inserir aluno, nenhum ID obtido.");
                }
            }
            
            // Busca o aluno completo (incluindo data de cadastro)
            return buscarPorId(aluno.getId());
        }
    }

    /**
     * Atualiza um aluno existente no banco de dados.
     * @param aluno o aluno a ser atualizado
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public boolean atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, matricula = ?, email = ?, telefone = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.setInt(5, aluno.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Remove um aluno do banco de dados pelo ID.
     * @param id o ID do aluno a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM alunos WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca um aluno pelo ID.
     * @param id o ID do aluno a ser buscado
     * @return o aluno encontrado ou null se não existir
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Aluno buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Busca um aluno pela matrícula.
     * @param matricula a matrícula do aluno a ser buscado
     * @return o aluno encontrado ou null se não existir
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Aluno buscarPorMatricula(String matricula) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     * @return lista de alunos
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos ORDER BY nome";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alunos.add(mapearResultSet(rs));
            }
        }
        
        return alunos;
    }

    /**
     * Busca alunos pelo nome (busca parcial).
     * @param nome parte do nome a ser buscado
     * @return lista de alunos que contêm o nome buscado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Aluno> buscarPorNome(String nome) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos WHERE nome LIKE ? ORDER BY nome";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(mapearResultSet(rs));
                }
            }
        }
        
        return alunos;
    }

    /**
     * Mapeia um ResultSet para um objeto Aluno.
     * @param rs o ResultSet contendo os dados do aluno
     * @return o objeto Aluno mapeado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    private Aluno mapearResultSet(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setMatricula(rs.getString("matricula"));
        aluno.setEmail(rs.getString("email"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return aluno;
    }
}