package br.com.ruan.estanteprivada.controller;

import br.com.ruan.estanteprivada.dto.EntradaDTO;
import br.com.ruan.estanteprivada.model.Entrada;
import br.com.ruan.estanteprivada.request.PostEntradaRequest;
import br.com.ruan.estanteprivada.service.EntradaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    @GetMapping("/{livroId}")
    private List<EntradaDTO> buscarEntradasPorLivro(@PathVariable Long livroId) throws BadRequestException {
        return entradaService.buscarEntradas(livroId);
    }

    @PostMapping("/{livroId}")
    public void criarEntrada(@RequestBody PostEntradaRequest entradaRequest,
                             @PathVariable Long livroId) throws BadRequestException {
        entradaService.criarEntrada(entradaRequest, livroId);
    }
}
