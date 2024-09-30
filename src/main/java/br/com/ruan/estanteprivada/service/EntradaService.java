package br.com.ruan.estanteprivada.service;

import br.com.ruan.estanteprivada.dto.EntradaDTO;
import br.com.ruan.estanteprivada.model.Entrada;
import br.com.ruan.estanteprivada.model.Livro;
import br.com.ruan.estanteprivada.repository.EntradaRepository;
import br.com.ruan.estanteprivada.repository.LivroRepository;
import br.com.ruan.estanteprivada.request.PostEntradaRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<EntradaDTO> buscarEntradas(Long livroId) throws BadRequestException {
        Optional<Livro> livroOptional = livroRepository.findById(livroId);

        if (livroOptional.isPresent()) {
            List<Entrada> entradas = entradaRepository.buscarPorLivro(livroOptional.get());

            return entradas.stream()
                    .map(e -> {
                        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dataFormatada = sdf.format(e.getData());

                        return new EntradaDTO(e, dataFormatada);
                    })
                    .collect(Collectors.toList());
        }
        else {
            throw new BadRequestException();
        }
    }

    public void criarEntrada(PostEntradaRequest entradaRequest, Long livroId) throws BadRequestException {
        Optional<Livro> livroOptional = livroRepository.findById(livroId);

        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();

            int entradasFinais = livro.getEntradas().stream()
                    .filter(Entrada::isTerminouLivro)
                    .toList().size();

            Entrada newEntrada = new Entrada(
                    entradaRequest.resenha(),
                    entradaRequest.data(),
                    entradaRequest.porcentagem(),
                    entradaRequest.terminouLivro(),
                    entradasFinais + 1
            );

            livro.addEntrada(newEntrada);

            livroRepository.save(livro);
        }
        else {
            throw new BadRequestException();
        }
    }
}
