package br.com.ruan.estanteprivada.repository;

import br.com.ruan.estanteprivada.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
