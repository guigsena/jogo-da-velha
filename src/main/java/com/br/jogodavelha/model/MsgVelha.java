package com.br.jogodavelha.model;

public class MsgVelha extends Msg {
	private String status;
	private String winner;
	
	public MsgVelha() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	
}
