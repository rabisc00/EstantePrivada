package br.com.ruan.estanteprivada.dto;

import br.com.ruan.estanteprivada.model.Livro;

import java.time.LocalDate;

public record LivroSnippetDTO(Long id,
                              String titulo,
                              String subtitulo,
                              String autores,
                              String editora,
                              Integer anoPublicacao,
                              String imagem) {
    public LivroSnippetDTO(Livro livro) {
        this(livro.getId(),
                livro.getTitulo(),
                livro.getSubtitulo(),
                livro.getAutores(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getImagem());
    }
}
