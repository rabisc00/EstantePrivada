package br.com.ruan.estanteprivada.model;

import br.com.ruan.estanteprivada.request.PostEntradaRequest;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "entradas")
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Livro livro;

    @Column(columnDefinition = "varchar(5000)")
    private String resenha;

    private Integer porcentagem;
    private Integer vezLendo;
    private LocalDate data;
    private boolean terminouLivro;

    public Entrada() {}

    public Entrada(String resenha,
                   String data,
                   Integer porcentagem,
                   boolean terminouLivro,
                   Integer vezLendo) {
        this.resenha = resenha;
        this.porcentagem = porcentagem;
        this.terminouLivro = terminouLivro;
        this.vezLendo = vezLendo;

        try {
            this.data = LocalDate.parse(data);
        } catch (DateTimeParseException ex) {
            this.data = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getResenha() {
        return resenha;
    }

    public void setResenha(String resenha) {
        this.resenha = resenha;
    }

    public Integer getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Integer porcentagem) {
        this.porcentagem = porcentagem;
    }

    public Integer getVezLendo() {
        return vezLendo;
    }

    public void setVezLendo(Integer vezLendo) {
        this.vezLendo = vezLendo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isTerminouLivro() {
        return terminouLivro;
    }

    public void setTerminouLivro(boolean terminouLivro) {
        this.terminouLivro = terminouLivro;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
