package com.br.jogodavelha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.br.jogodavelha.model.Game;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
	
}
