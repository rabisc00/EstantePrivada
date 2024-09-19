package br.com.ruan.estanteprivada.dto;

import br.com.ruan.estanteprivada.model.Livro;

public record LivroDetalhadoDTO(Long id,
                                String titulo,
                                String subtitulo,
                                String autores,
                                String editora,
                                String isbn,
                                Integer anoPublicacao,
                                String idioma,
                                String imagem) {
    public LivroDetalhadoDTO(Livro livro) {
        this(livro.getId(),
                livro.getTitulo(),
                livro.getSubtitulo(),
                livro.getAutores(),
                livro.getEditora(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getIdioma(),
                livro.getImagem());
    }
}
