package src.dao;

import src.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados para a entidade Livro.
 */
public class LivroDAO {
    private Connection conexao;

    public LivroDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    /**
     * Insere um novo livro no banco de dados.
     * @param livro o livro a ser inserido
     * @return o livro com o ID gerado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Livro inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, editora, ano_publicacao, isbn, " +
                     "quantidade_total, quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getIsbn());
            stmt.setInt(6, livro.getQuantidadeTotal());
            stmt.setInt(7, livro.getQuantidadeDisponivel());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir livro, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao inserir livro, nenhum ID obtido.");
                }
            }
            
            // Busca o livro completo (incluindo data de cadastro)
            return buscarPorId(livro.getId());
        }
    }

    /**
     * Atualiza um livro existente no banco de dados.
     * @param livro o livro a ser atualizado
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public boolean atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, editora = ?, ano_publicacao = ?, " +
                     "isbn = ?, quantidade_total = ?, quantidade_disponivel = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getIsbn());
            stmt.setInt(6, livro.getQuantidadeTotal());
            stmt.setInt(7, livro.getQuantidadeDisponivel());
            stmt.setInt(8, livro.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Remove um livro do banco de dados pelo ID.
     * @param id o ID do livro a ser removido
     * @return true se a remoção foi bem-sucedida, false caso contrário
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public boolean remover(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca um livro pelo ID.
     * @param id o ID do livro a ser buscado
     * @return o livro encontrado ou null se não existir
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livros WHERE id = ?";
        
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
     * Busca um livro pelo ISBN.
     * @param isbn o ISBN do livro a ser buscado
     * @return o livro encontrado ou null se não existir
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public Livro buscarPorIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM livros WHERE isbn = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            
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
     * Lista todos os livros cadastrados.
     * @return lista de livros
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Livro> listarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY titulo";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                livros.add(mapearResultSet(rs));
            }
        }
        
        return livros;
    }

    /**
     * Busca livros pelo título (busca parcial).
     * @param titulo parte do título a ser buscado
     * @return lista de livros que contêm o título buscado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Livro> buscarPorTitulo(String titulo) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ? ORDER BY titulo";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapearResultSet(rs));
                }
            }
        }
        
        return livros;
    }

    /**
     * Busca livros pelo autor (busca parcial).
     * @param autor parte do nome do autor a ser buscado
     * @return lista de livros que contêm o autor buscado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Livro> buscarPorAutor(String autor) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE autor LIKE ? ORDER BY autor, titulo";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + autor + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapearResultSet(rs));
                }
            }
        }
        
        return livros;
    }

    /**
     * Atualiza a quantidade disponível de um livro.
     * @param id o ID do livro
     * @param quantidadeDisponivel a nova quantidade disponível
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public boolean atualizarQuantidadeDisponivel(int id, int quantidadeDisponivel) throws SQLException {
        String sql = "UPDATE livros SET quantidade_disponivel = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, quantidadeDisponivel);
            stmt.setInt(2, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Lista livros disponíveis para empréstimo.
     * @return lista de livros disponíveis
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    public List<Livro> listarDisponiveis() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE quantidade_disponivel > 0 ORDER BY titulo";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                livros.add(mapearResultSet(rs));
            }
        }
        
        return livros;
    }

    /**
     * Mapeia um ResultSet para um objeto Livro.
     * @param rs o ResultSet contendo os dados do livro
     * @return o objeto Livro mapeado
     * @throws SQLException se ocorrer um erro no banco de dados
     */
    private Livro mapearResultSet(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setEditora(rs.getString("editora"));
        livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setQuantidadeTotal(rs.getInt("quantidade_total"));
        livro.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        livro.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return livro;
    }
}