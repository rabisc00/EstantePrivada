package br.com.ruan.estanteprivada.model;

import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dados.VolumeGB;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
