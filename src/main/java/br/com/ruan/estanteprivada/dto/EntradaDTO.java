package br.com.ruan.estanteprivada.dto;

import br.com.ruan.estanteprivada.model.Entrada;

import java.time.LocalDate;

public record EntradaDTO(String resenha,
                         String data,
                         Integer porcentagem,
                         Integer vezLendo,
                         boolean terminouLivro) {
    public EntradaDTO(Entrada entrada, String dataFormatada) {
        this(entrada.getResenha(),
                dataFormatada,
                entrada.getPorcentagem(),
                entrada.getVezLendo(),
                entrada.isTerminouLivro());
    }
}
