package br.com.ruan.estanteprivada.service;

import br.com.ruan.estanteprivada.dados.ContainerGB;
import br.com.ruan.estanteprivada.dados.IndustryIdentifierGB;
import br.com.ruan.estanteprivada.dados.LivroGB;
import br.com.ruan.estanteprivada.dto.LivroSnippetDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.model.Livro;
import br.com.ruan.estanteprivada.repository.LivroRepository;
import br.com.ruan.estanteprivada.request.PutLivroRequest;
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
                    Integer.parseInt(Arrays.asList(l.volumeInfo().publishedDate().split("-")).get(0).replace("?", "0")) :
                    null;

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

    public List<LivroSnippetDTO> listarLivros() {
        var todosLivros = livroRepository.findAll();
        return todosLivros.stream()
                .map(LivroSnippetDTO::new)
                .collect(Collectors.toList());
    }

    public void atualizarLivro(PutLivroRequest request) throws BadRequestException {
        Optional<Livro> livroUpdate = livroRepository.findById(request.id());

        if (livroUpdate.isPresent()) {
            var livroGet = livroUpdate.get();

            if ((request.titulo() != null && !request.titulo().isEmpty()) &&
                    (livroGet.getTitulo() == null || (livroGet.getTitulo() != null && !livroGet.getTitulo().equals(request.titulo())))) {
                livroGet.setTitulo(request.titulo());
            }

            if ((request.subtitulo() != null && !request.subtitulo().isEmpty()) &&
                    (livroGet.getSubtitulo() == null || (livroGet.getSubtitulo() != null && !livroGet.getSubtitulo().equals(request.subtitulo())))) {
                livroGet.setSubtitulo(request.subtitulo());
            }

            if ((request.autores() != null && !request.autores().isEmpty()) &&
                    (livroGet.getAutores() == null || (livroGet.getAutores() != null && !livroGet.getAutores().equals(request.autores())))) {
                livroGet.setAutores(request.autores());
            }

            if ((request.editora() != null && !request.editora().isEmpty()) &&
                    (livroGet.getEditora() == null || livroGet.getEditora() != null && !livroGet.getEditora().equals(request.editora()))) {
                livroGet.setEditora(request.editora());
            }

            if ((request.imagem() != null && !request.imagem().isEmpty()) &&
                    (livroGet.getImagem() == null || (livroGet.getImagem() != null && !livroGet.getImagem().equals(request.imagem())))) {
                livroGet.setImagem(request.imagem());
            }

            if (request.anoPublicacao() != null &&
                    (livroGet.getAnoPublicacao() == null || (livroGet.getAnoPublicacao() != null && !livroGet.getAnoPublicacao().equals(request.anoPublicacao())))) {
                livroGet.setAnoPublicacao(request.anoPublicacao());
            }

            if ((request.isbn() != null && !request.isbn().isEmpty()) &&
                    (livroGet.getIsbn() == null || (livroGet.getIsbn() != null && !livroGet.getIsbn().equals(request.isbn())))) {
                livroGet.setIsbn(request.isbn());
            }

            if ((request.idioma() != null && !request.idioma().isEmpty()) &&
                    (livroGet.getIdioma() == null || (livroGet.getIdioma() != null && !livroGet.getIdioma().equals(request.idioma())))) {
                livroGet.setIdioma(request.isbn());
            }

            livroRepository.save(livroGet);
        }
        else {
            throw new BadRequestException();
        }
    }
}
