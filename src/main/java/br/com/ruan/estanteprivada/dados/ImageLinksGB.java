package br.com.ruan.estanteprivada.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageLinksGB(@JsonAlias("thumbnail") String thumbnail) {
}
