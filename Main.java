import dao.AlunoDAO;
import model.Aluno;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();

        System.out.println("Sistema da Biblioteca - Cadastro de Aluno");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        LocalDate nascimento = LocalDate.parse(scanner.nextLine());

        Aluno aluno = new Aluno(nome, matricula, nascimento);
        alunoDAO.cadastrar(aluno);
    }
}