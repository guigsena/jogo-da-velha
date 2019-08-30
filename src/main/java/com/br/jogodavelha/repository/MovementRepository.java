package com.br.jogodavelha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.br.jogodavelha.model.Movement;

@RepositoryRestResource
public interface MovementRepository extends JpaRepository<Movement, Long> {
	@Query("select m from Movement m where m.id = :idGame and m.position.x = :x and m.position.y = :y")
	Movement findMovement(@Param("idGame")Long idGame, @Param("x")int x, @Param("y")int y);
	
	@Query("select m from Movement m where m.id = :idGame order by m.dataMovimento desc")
	List<Movement> findMovementByGame(@Param("idGame")Long idGame);
}
