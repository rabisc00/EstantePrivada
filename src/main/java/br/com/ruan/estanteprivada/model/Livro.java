package br.com.ruan.estanteprivada.model;

import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dados.VolumeGB;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Entrada> entradas = new ArrayList<>();

    @Column(columnDefinition = "varchar(500)")
    private String imagem;

    @Column(nullable = true)
    private Integer anoPublicacao;

    private String titulo;
    private String subtitulo;
    private String autores;
    private String editora;
    private String isbn;
    private String idioma;

    public Livro() {}

    public Livro(VolumeGB dadosLivro) {
        this.titulo = dadosLivro.title();
        this.subtitulo = dadosLivro.subtitle();
        this.editora = dadosLivro.publisher();
        this.idioma = dadosLivro.language();
        this.autores = dadosLivro.authors() != null ? String.join(", ", dadosLivro.authors()) : null;
        this.imagem = dadosLivro.imageLinks() != null ? dadosLivro.imageLinks().thumbnail() : null;
        this.anoPublicacao = dadosLivro.publishedDate() != null ?
                Integer.parseInt(Arrays.asList(dadosLivro.publishedDate().split("-")).get(0))  :
                null;

        if (dadosLivro.industryIdentifiers() != null) {
            Optional<IndustryIdentifierGB> isbnFound = dadosLivro.industryIdentifiers().stream()
                    .filter(i -> i.type().equals("ISBN_13")).findAny();

            this.isbn = isbnFound.isPresent() ? isbnFound.get().identifier() : null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getEditora() {
        return editora;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public void addEntrada(Entrada entrada) {
        entrada.setLivro(this);
        entradas.add(entrada);
    }
}
