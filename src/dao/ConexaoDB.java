package src.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 */
public class ConexaoDB {
    // Parâmetros de conexão
    private static final String HOST = System.getenv("MYSQL_HOST") != null ? 
                                      System.getenv("MYSQL_HOST") : "localhost";
    private static final String PORT = System.getenv("MYSQL_PORT") != null ? 
                                      System.getenv("MYSQL_PORT") : "3306";
    private static final String DATABASE = System.getenv("MYSQL_DATABASE") != null ? 
                                          System.getenv("MYSQL_DATABASE") : "biblioteca";
    private static final String USER = System.getenv("MYSQL_USER") != null ? 
                                      System.getenv("MYSQL_USER") : "root";
    private static final String PASSWORD = System.getenv("MYSQL_PASSWORD") != null ? 
                                          System.getenv("MYSQL_PASSWORD") : "root_password";
    
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + 
                                     "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    
    // Singleton para a conexão
    private static Connection conexao = null;
    
    /**
     * Obtém uma conexão com o banco de dados.
     * @return objeto Connection representando a conexão
     * @throws SQLException se ocorrer um erro ao conectar
     */
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                // Carrega o driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Estabelece a conexão
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC não encontrado: " + e.getMessage());
                throw new SQLException("Driver JDBC não encontrado", e);
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                throw e;
            }
        }
        return conexao;
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            } finally {
                conexao = null;
            }
        }
    }
    
    /**
     * Testa a conexão com o banco de dados.
     * @return true se a conexão foi estabelecida com sucesso, false caso contrário
     */
    public static boolean testarConexao() {
        try {
            Connection conn = getConexao();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Teste de conexão falhou: " + e.getMessage());
            return false;
        }
    }
}