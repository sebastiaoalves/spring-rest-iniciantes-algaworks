package br.uern.di.poo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uern.di.poo.domain.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
