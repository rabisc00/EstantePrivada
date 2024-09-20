package br.com.ruan.estanteprivada.dto;

import br.com.ruan.estanteprivada.model.Entrada;

import java.time.LocalDate;

public record EntradaDTO(String resenha,
                         LocalDate data,
                         Integer porcentagem,
                         Integer vezLendo,
                         boolean terminouLivro) {
    public EntradaDTO(Entrada entrada) {
        this(entrada.getResenha(),
                entrada.getData(),
                entrada.getPorcentagem(),
                entrada.getVezLendo(),
                entrada.isTerminouLivro());
    }
}
