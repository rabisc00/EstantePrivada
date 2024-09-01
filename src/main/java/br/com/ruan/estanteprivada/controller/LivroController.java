package br.com.ruan.estanteprivada.controller;

import br.com.ruan.estanteprivada.dto.LivroDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.request.PostLivroRequest;
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
    public List<LivroDTO> listarLivros() {
        return livroService.listarLivros();
    }

    @PostMapping
    public ResponseEntity<String> criarLivro(@RequestBody PostLivroRequest request) {
        try {
            livroService.salvarLivro(request.id());
            return ResponseEntity.ok().build();
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body("Id incorreto");
        }
    }
}
