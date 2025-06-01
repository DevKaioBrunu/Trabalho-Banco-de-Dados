package src.model;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Classe que representa um empréstimo de livro no sistema da biblioteca.
 */
public class Emprestimo {
    private int id;
    private int alunoId;
    private int livroId;
    private Timestamp dataEmprestimo;
    private Timestamp dataDevolucaoPrevista;
    private Timestamp dataDevolucaoEfetiva;
    private StatusEmprestimo status;
    
    // Aluno e Livro associados (não armazenados no banco, apenas para facilitar operações)
    private Aluno aluno;
    private Livro livro;

    /**
     * Enum para representar o status do empréstimo
     */
    public enum StatusEmprestimo {
        ATIVO,
        DEVOLVIDO,
        ATRASADO
    }

    // Construtor padrão
    public Emprestimo() {
    }

    // Construtor para novos empréstimos (sem ID)
    public Emprestimo(int alunoId, int livroId, Timestamp dataEmprestimo, Timestamp dataDevolucaoPrevista) {
        this.alunoId = alunoId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = StatusEmprestimo.ATIVO;
    }

    // Construtor completo
    public Emprestimo(int id, int alunoId, int livroId, Timestamp dataEmprestimo, 
                     Timestamp dataDevolucaoPrevista, Timestamp dataDevolucaoEfetiva, 
                     StatusEmprestimo status) {
        this.id = id;
        this.alunoId = alunoId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public Timestamp getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Timestamp dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Timestamp getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Timestamp dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Timestamp getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public void setDataDevolucaoEfetiva(Timestamp dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    /**
     * Verifica se o empréstimo está atrasado.
     * @return true se a data atual é posterior à data prevista de devolução e o livro não foi devolvido
     */
    public boolean isAtrasado() {
        if (status == StatusEmprestimo.DEVOLVIDO) {
            return false;
        }
        
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        return agora.after(dataDevolucaoPrevista);
    }

    /**
     * Calcula o número de dias de atraso.
     * @return número de dias de atraso, ou 0 se não estiver atrasado
     */
    public long getDiasAtraso() {
        if (!isAtrasado()) {
            return 0;
        }
        
        Timestamp referencia = (dataDevolucaoEfetiva != null) ? dataDevolucaoEfetiva : new Timestamp(System.currentTimeMillis());
        long diff = referencia.getTime() - dataDevolucaoPrevista.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * Registra a devolução do livro.
     * @param dataEfetiva data efetiva da devolução
     */
    public void registrarDevolucao(Timestamp dataEfetiva) {
        this.dataDevolucaoEfetiva = dataEfetiva;
        this.status = (dataEfetiva.after(dataDevolucaoPrevista)) ? 
                      StatusEmprestimo.ATRASADO : StatusEmprestimo.DEVOLVIDO;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", alunoId=" + alunoId +
                ", livroId=" + livroId +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevolucaoEfetiva=" + dataDevolucaoEfetiva +
                ", status=" + status +
                '}';
    }
}