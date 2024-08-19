package br.com.ruan.estanteprivada.dto;

import br.com.ruan.estanteprivada.model.Livro;

import java.time.LocalDate;

public record LivroDTO(String titulo,
                       String subtitulo,
                       String autores,
                       String descricao,
                       String isbn,
                       String categorias,
                       String idioma,
                       LocalDate dataPublicacao) {
    public LivroDTO(Livro livro) {
        this(livro.getTitulo(),
                livro.getSubtitulo(),
                livro.getAutores(),
                livro.getDescricao(),
                livro.getIsbn(),
                livro.getCategorias(),
                livro.getIdioma(),
                livro.getDataPublicacao());
    }
}
