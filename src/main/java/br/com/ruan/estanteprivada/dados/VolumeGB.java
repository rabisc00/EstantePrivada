package br.com.ruan.estanteprivada.dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeGB(@JsonAlias("title") String title,
                       @JsonAlias("subtitle") String subtitle,
                       @JsonAlias("authors")List<String> authors,
                       @JsonAlias("publisher") String publisher,
                       @JsonAlias("publishedData") String publishedDate,
                       @JsonAlias("industryIdentifiers") List<IndustryIdentifierGB> industryIdentifiers,
                       @JsonAlias("categories") List<String> categories,
                       @JsonAlias("imageLinks") ImageLinksGB imageLinks,
                       @JsonAlias("language") String language) {
}
