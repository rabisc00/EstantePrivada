package br.com.ruan.estanteprivada.service;

import br.com.ruan.estanteprivada.dados.ContainerGB;
import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dto.LivroDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.model.Livro;
import br.com.ruan.estanteprivada.util.ConsomeApi;
import br.com.ruan.estanteprivada.util.ConverteDados;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    public List<LivroTempDTO> buscarLivros(String titulo, String autor, String editora, String isbn, Integer pagina) {
        var consomeApi = new ConsomeApi();
        var conversor = new ConverteDados();

        String endereco = "https://www.googleapis.com/books/v1/volumes?q=";
        String apiKey = "&key=" + System.getenv("GOOGLEBOOKS_KEY");
        String paginaParams = "&maxResults=" + 10 + "&startIndex=" + (pagina - 1) * 10;

        String q = "";

        if (titulo != null) q += titulo;
        if (autor != null) q += " inauthor:" + autor;
        if (editora != null) q += " inpublisher:" + editora;
        if (isbn != null) q += " isbn:" + isbn;

        q = q.replace(" ", "+");

        String requestUrl = endereco + q + apiKey + paginaParams;

        var json = consomeApi.obterDados(requestUrl);
        var container = conversor.obterDados(json, ContainerGB.class);
        
        return container.items().stream().map(l -> {
            String imagem = l.volumeInfo().imageLinks() != null ? l.volumeInfo().imageLinks().thumbnail() : null;
            String autoresLivro = l.volumeInfo().authors() != null ? String.join(", ", l.volumeInfo().authors()) : null;
            Integer anoLancamento = l.volumeInfo().publishedDate() != null ?
                    Integer.parseInt(Arrays.asList(l.volumeInfo().publishedDate().split("-")).get(0)) : null;

            String isbnLivro = null;
            if (l.volumeInfo().industryIdentifiers() != null) {
                Optional<IndustryIdentifierGB> isbnFound = l.volumeInfo().industryIdentifiers().stream()
                        .filter(i -> i.type().equals("ISBN_13")).findAny();

                isbnLivro = isbnFound.isPresent() ? isbnFound.get().identifier() : null;
            }

            return new LivroTempDTO(l.id(),
                    l.volumeInfo().title(),
                    l.volumeInfo().subtitle(),
                    autoresLivro,
                    l.volumeInfo().publisher(),
                    isbnLivro,
                    anoLancamento,
                    l.volumeInfo().language(),
                    imagem);
        }).collect(Collectors.toList());
    }
}
