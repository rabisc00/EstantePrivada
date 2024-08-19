package br.com.ruan.estanteprivada.controller;

import br.com.ruan.estanteprivada.dto.LivroDTO;
import br.com.ruan.estanteprivada.dto.LivroTempDTO;
import br.com.ruan.estanteprivada.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return livroService.buscarLivros(titulo, autor, editora, isbn, pagina);
    }
}
