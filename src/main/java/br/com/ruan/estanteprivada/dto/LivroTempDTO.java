package br.com.ruan.estanteprivada.dto;

public record LivroTempDTO(String id,
                           String titulo,
                           String subtitulo,
                           String autor,
                           String editora,
                           String isbn,
                           Integer anoLancamento,
                           String idioma,
                           String imagem) { }
