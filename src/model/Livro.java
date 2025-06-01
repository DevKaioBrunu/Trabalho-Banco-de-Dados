package src.model;

import java.sql.Timestamp;

/**
 * Classe que representa um livro no sistema da biblioteca.
 */
public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private String isbn;
    private int quantidadeTotal;
    private int quantidadeDisponivel;
    private Timestamp dataCadastro;

    // Construtor padrão
    public Livro() {
    }

    // Construtor para novos livros (sem ID)
    public Livro(String titulo, String autor, String editora, int anoPublicacao, String isbn, int quantidadeTotal) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeTotal;
    }

    // Construtor completo
    public Livro(int id, String titulo, String autor, String editora, int anoPublicacao, String isbn, 
                int quantidadeTotal, int quantidadeDisponivel, Timestamp dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Verifica se o livro está disponível para empréstimo.
     * @return true se houver exemplares disponíveis, false caso contrário
     */
    public boolean isDisponivel() {
        return quantidadeDisponivel > 0;
    }

    /**
     * Reduz a quantidade disponível quando um livro é emprestado.
     * @return true se a operação foi bem-sucedida, false se não há exemplares disponíveis
     */
    public boolean emprestar() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    /**
     * Aumenta a quantidade disponível quando um livro é devolvido.
     * @return true se a operação foi bem-sucedida, false se todos os exemplares já estão disponíveis
     */
    public boolean devolver() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", anoPublicacao=" + anoPublicacao +
                ", isbn='" + isbn + '\'' +
                ", quantidadeTotal=" + quantidadeTotal +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}