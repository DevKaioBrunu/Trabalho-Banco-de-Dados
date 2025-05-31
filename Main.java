import java.util.Scanner;

public class Main {klaio
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Sistema Biblioteca ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Livro");
            System.out.println("3. Registrar Empréstimo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Cadastrar Aluno - Em construção");
                    break;
                case 2:
                    System.out.println("Cadastrar Livro - Em construção");
                    break;
                case 3:
                    System.out.println("Registrar Empréstimo - Em construção");
                    break;
            }
        } while (opcao != 0);
    }
}
