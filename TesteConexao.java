import util.ConnectionFactory;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão bem-sucedida com o banco de dados!");
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}