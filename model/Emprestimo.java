package model;

import java.time.LocalDate;

public class Emprestimo {
    private int idAluno;
    private int idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(int idAluno, int idLivro) {
        this.idAluno = idAluno;
        this.idLivro = idLivro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = dataEmprestimo.plusDays(7);
    }

    public int getIdAluno() { return idAluno; }
    public int getIdLivro() { return idLivro; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
}
