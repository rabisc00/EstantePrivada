package br.com.ruan.estanteprivada.request;

public record PutLivroRequest(String titulo,
                              String subtitulo,
                              String autores,
                              String editora,
                              String imagem,
                              Integer anoPublicacao,
                              String idioma,
                              String isbn) {
}
