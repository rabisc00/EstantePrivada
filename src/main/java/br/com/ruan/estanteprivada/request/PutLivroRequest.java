package br.com.ruan.estanteprivada.request;

public record PutLivroRequest(Long id,
                              String titulo,
                              String subtitulo,
                              String autores,
                              String editora,
                              String imagem,
                              Integer anoPublicacao,
                              String idioma,
                              String isbn) {
}
