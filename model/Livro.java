package model;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidadeEstoque;

    public Livro(String titulo, String autor, int anoPublicacao, int quantidadeEstoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setAnoPublicacao(int ano) { this.anoPublicacao = ano; }
    public void setQuantidadeEstoque(int qnt) { this.quantidadeEstoque = qnt; }
}
