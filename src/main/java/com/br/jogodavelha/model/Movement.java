package com.br.jogodavelha.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "movement")
public class Movement {
	
	@Id @GeneratedValue
	private Long idMovement;
	
	@Column(name="id_game")
	private Long id;
	@Enumerated(EnumType.STRING)
	private TypePlayer player;

	@OneToOne (cascade = CascadeType.ALL)
	private Position position;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataMovimento;
	
	public Long getId() {
		return id;
	}
	public Long getIdMovement() {
		return idMovement;
	}
	public void setIdMovement(Long idMovement) {
		this.idMovement = idMovement;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TypePlayer getPlayer() {
		return player;
	}
	public void setPlayer(TypePlayer player) {
		this.player = player;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Date getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	
}
