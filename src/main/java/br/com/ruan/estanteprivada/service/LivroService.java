package br.com.ruan.estanteprivada.service;

import br.com.ruan.estanteprivada.dados.ContainerGB;
import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dados.LivroGB;
import br.com.ruan.estanteprivada.dto.LivroDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.model.Livro;
import br.com.ruan.estanteprivada.repository.LivroRepository;
import br.com.ruan.estanteprivada.util.ConsomeApi;
import br.com.ruan.estanteprivada.util.ConverteDados;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    private final String URL = "https://www.googleapis.com/books/v1/volumes";
    private final String API_KEY = "key=" + System.getenv("GOOGLEBOOKS_KEY");
    private final ConsomeApi CONSOME_API = new ConsomeApi();
    private final ConverteDados CONVERSOR = new ConverteDados();

    public List<LivroTempDTO> buscarLivrosApi(String titulo, String autor, String editora, String isbn, Integer pagina) {
        String q = "";

        if (titulo != null && !titulo.isEmpty()) q += titulo;
        if (autor != null && !autor.isEmpty()) q += " inauthor:" + autor;
        if (editora != null && !editora.isEmpty()) q += " inpublisher:" + editora;
        if (isbn != null && !isbn.isEmpty()) q += " isbn:" + isbn;

        q = q.replace(" ", "+");
        String paginaParams = "&maxResults=" + 10 + "&startIndex=" + (pagina - 1) * 10;
        String requestUrl = URL + "?q=" + q + paginaParams + "&" + API_KEY;

        var httpResponse = CONSOME_API.obterDados(requestUrl);
        var container = CONVERSOR.obterDados(httpResponse.body().toString(), ContainerGB.class);

        if (container.items() == null) return null;
        
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

    public void salvarLivro(String livroId) throws BadRequestException {
        String requestUrl = URL + "/" + livroId + "?" + API_KEY;

        var httpResponse = CONSOME_API.obterDados(requestUrl);
        if (httpResponse.statusCode() != 200) throw new BadRequestException();

        var livroGb = CONVERSOR.obterDados(httpResponse.body().toString(), LivroGB.class);
        var newLivro = new Livro(livroGb.volumeInfo());

        livroRepository.save(newLivro);
    }

    public List<LivroDTO> listarLivros() {
        var todosLivros = livroRepository.findAll();
        return todosLivros.stream()
                .map(LivroDTO::new)
                .collect(Collectors.toList());
    }
}
