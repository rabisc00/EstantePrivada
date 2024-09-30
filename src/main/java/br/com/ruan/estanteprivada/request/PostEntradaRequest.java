package br.com.ruan.estanteprivada.request;

public record PostEntradaRequest(String resenha,
                                 String data,
                                 Integer porcentagem,
                                 boolean terminouLivro) {
}
