package model;

public class Aluno {
    private int id;
    private String nome;
    private String matricula;
    private String dataNascimento;

    public Aluno(String nome, String matricula, String dataNascimento) {
        this.nome = nome;
        this.matricula = matricula;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getDataNascimento() { return dataNascimento; }

    public void setNome(String nome) { this.nome = nome; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
}
