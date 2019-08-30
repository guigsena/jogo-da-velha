package com.br.jogodavelha.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "game")
public class Game {
	//FOI DECIDIDO GERAR UM ID MAIS SIMPLES PARA CADA PARTIDA 
	//AO INVES DE USAR UM PADRAO COMO fbf7d720-df90-48c4-91f7-9462deafefb8
	@Id @GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private @NonNull TypePlayer firstPlayer;

	/*CONSTRUTOR*/
	public Game() {
		super();
		sortPlayerBegin();
	}
	
	public Game(Long id) {
		super();
		this.id = id;
	}

	public Game(Long id, TypePlayer firstPlayer) {
		super();
		this.id = id;
		this.firstPlayer = firstPlayer;
	}

	/*METODOS*/
	private void sortPlayerBegin() {
		this.firstPlayer = TypePlayer.randomTypePlayer();
	}
	
	@Override
	public String toString() {
		return "Partida [id=" + id + ", firstPlayer=" + firstPlayer + "]";
	}

	/*CRIA GETS AND SETS*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypePlayer getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(TypePlayer firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	
}
