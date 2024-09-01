package br.com.ruan.estanteprivada.model;

import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dados.VolumeGB;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(500)")
    private String imagem;

    private String titulo;
    private String subtitulo;
    private String autores;
    private String editora;
    private String isbn;
    private String categorias;
    private String idioma;
    private LocalDate dataPublicacao;

    public Livro() {}

    public Livro(VolumeGB dadosLivro) {
        this.titulo = dadosLivro.title();
        this.subtitulo = dadosLivro.subtitle();
        this.editora = dadosLivro.publisher();
        this.idioma = dadosLivro.language();
        this.autores = dadosLivro.authors() != null ? String.join(", ", dadosLivro.authors()) : null;
        this.categorias = dadosLivro.categories() != null ? String.join(", ", dadosLivro.categories()) : null;
        this.imagem = dadosLivro.imageLinks() != null ? dadosLivro.imageLinks().thumbnail() : null;

        if (dadosLivro.industryIdentifiers() != null) {
            Optional<IndustryIdentifierGB> isbnFound = dadosLivro.industryIdentifiers().stream()
                    .filter(i -> i.type().equals("ISBN_13")).findAny();

            this.isbn = isbnFound.isPresent() ? isbnFound.get().identifier() : null;
        }

        try {
            this.dataPublicacao = LocalDate.parse(dadosLivro.publishedDate());
        } catch (DateTimeParseException ex) {
            this.dataPublicacao = null;
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

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategorias() {
        return categorias;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getImagem() {
        return imagem;
    }
}
