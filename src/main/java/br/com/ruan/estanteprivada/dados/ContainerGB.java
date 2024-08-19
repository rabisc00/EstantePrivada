package br.com.ruan.estanteprivada.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ContainerGB(@JsonAlias("items") List<LivroGB> items) {
}
