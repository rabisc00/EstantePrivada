package br.com.ruan.estanteprivada.controller;

import br.com.ruan.estanteprivada.dto.LivroDetalhadoDTO;
import br.com.ruan.estanteprivada.dto.LivroSnippetDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.request.PostLivroRequest;
import br.com.ruan.estanteprivada.request.PutLivroRequest;
import br.com.ruan.estanteprivada.service.LivroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping("/pesquisar")
    public List<LivroTempDTO> pesquisarLivro(@RequestParam(name = "titulo", required = false) String titulo,
                                                            @RequestParam(name = "autor", required = false) String autor,
                                                            @RequestParam(name = "editora", required = false) String editora,
                                                            @RequestParam(name = "isbn", required = false) String isbn,
                                                            @RequestParam(name = "pagina") Integer pagina) {
        return livroService.buscarLivrosApi(titulo, autor, editora, isbn, pagina);
    }

    @GetMapping
    public List<LivroSnippetDTO> listarLivros() {
        return livroService.listarLivros();
    }

    @GetMapping(value = "/{livroId}")
    public LivroDetalhadoDTO buscarDetalhesLivro(@PathVariable Long livroId) throws BadRequestException {
        return livroService.buscarDetalhesLivro(livroId);
    }

    @PostMapping
    public void criarLivro(@RequestBody PostLivroRequest request) throws BadRequestException {
        livroService.salvarLivro(request.id());
    }

    @PutMapping(value = "/{livroId}")
    public void atualizarLivros(@PathVariable Long livroId, @RequestBody PutLivroRequest request) throws BadRequestException {
        livroService.atualizarLivro(livroId, request);
    }

    @DeleteMapping(value = "/{livroId}")
    public void excluirLivro(@PathVariable Long livroId) throws BadRequestException {
        livroService.excluirLivro(livroId);
    }
}
