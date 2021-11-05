package br.com.duyllyan.reservaconsultas.model.entities;

public class Dentista {

    private Integer id;
    private Integer numeroMatricula;
    private String nome;
    private String sobrenome;

    public Dentista() {
    }

    public Dentista(Integer numeroMatricula, String nome, String sobrenome) {
        this.numeroMatricula = numeroMatricula;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(Integer numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return "Dentista{" +
                "id=" + id +
                ", numeroMatricula=" + numeroMatricula +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                '}';
    }
}
