import src.ui.BibliotecaUI;

/**
 * Classe principal que inicia a aplicação de gerenciamento da biblioteca.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gerenciamento da Biblioteca...");
        
        try {
            // Inicia a interface do usuário
            BibliotecaUI ui = new BibliotecaUI();
            ui.iniciar();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}