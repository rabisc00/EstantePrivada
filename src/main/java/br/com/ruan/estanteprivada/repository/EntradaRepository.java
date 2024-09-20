package br.com.ruan.estanteprivada.repository;

import br.com.ruan.estanteprivada.model.Entrada;
import br.com.ruan.estanteprivada.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    @Query("SELECT e FROM Livro l JOIN l.entradas e WHERE l = :livro ORDER BY e.vezLendo ASC, e.data ASC")
    List<Entrada> buscarPorLivro(Livro livro);
}
