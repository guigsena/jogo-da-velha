package com.br.jogodavelha.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.jogodavelha.model.Game;
import com.br.jogodavelha.model.Movement;
import com.br.jogodavelha.model.Msg;
import com.br.jogodavelha.model.MsgVelha;
import com.br.jogodavelha.model.MsgVencedor;
import com.br.jogodavelha.model.Position;
import com.br.jogodavelha.model.TypePlayer;
import com.br.jogodavelha.repository.GameRepository;
import com.br.jogodavelha.repository.MovementRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/game")
public class gameController {
	
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private MovementRepository movementRepository;
	
	private Msg msg = new Msg();
	
	@ApiOperation(value = "Cria nova partida, retorna o id criado e o usuario que começa")
	@PostMapping(path= "", produces = "application/json")
	public Game criaNovaPartida() {
		
		Game partida = new Game();
		//CRIA NOVO REGISTRO
		gameRepository.saveAndFlush(partida);
		
		if (partida.getId() == null ) {
			   throw new WebApplicationException(Response
			     .status(Response.Status.BAD_REQUEST)
			     .entity("Erro ao criar nova partida").build());
		}
		
		return partida;
	}
	
	@ApiOperation(value = "cria um movimento")
	@PostMapping(path= "/{id}/movement", consumes= "application/json", produces = "application/json")
	public ResponseEntity<Msg> movimentoJogador(@PathVariable(value="id")Long id, @RequestBody Movement movement) {
		
		try {
			Game game = gameRepository.findById(id).orElse(null);
			if(game != null) {
				List<Movement> lstMovements = movementRepository.findMovementByGame(id);
				if(isTurno(movement, lstMovements, game)) {
					if(!isPartidaFinalizada(lstMovements)) {
						if(existeMovimento(movement)) {
							msg = msgErro(msg,"Movimento já realizado");
						} else {
							movement.setDataMovimento(new Date());
							movementRepository.saveAndFlush(movement);
							//valida se partida terminou
							lstMovements = movementRepository.findMovementByGame(id);
							if(!isPartidaFinalizada(lstMovements)) {
								msg = msgErro(msg,"Jogada realizada com sucesso");
							}
						}
					}
				}else {
					msg = msgErro(msg,"Não é o turno do jogador");
				}
			} else {
				msg = msgErro(msg,"Partida não encontrada");
				
			}
		} catch (Exception e) {	
			e.printStackTrace();
			msg =  msgErro(msg, e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(msg);
		
	}
	
	private Msg msgErro(Msg msg, String mensagem) {
		msg =  (new Msg(mensagem));
		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
		return msg;
	}
	
	@ApiOperation(value = "Valida se é o movimento ja foi realizado")
	private boolean existeMovimento(Movement movement) {
		Movement mov = movementRepository.findMovement(movement.getId(), movement.getPosition().getX(), movement.getPosition().getY());
		if(mov != null)
			return true;
		return false;
	}

	@ApiOperation(value = "Valida se é a vez do jogador fazer a jodada")
	private boolean isTurno(Movement movimentoAtual, List<Movement> lstMovements, Game game) {
		if(lstMovements != null && !lstMovements.isEmpty()) {
			Movement ultimoMovement = lstMovements.get(0);
			if(movimentoAtual.getPlayer().equals(ultimoMovement.getPlayer()))
				return false;
		} else {
			if(!movimentoAtual.getPlayer().equals(game.getFirstPlayer())){
				return false;
			}
		}
		return true;
	}
	
	@ApiOperation(value = "Cria nova partida, retorna o id criado e o usuario que começa")
	private boolean isPartidaFinalizada(List<Movement> lstMovements) {
		//minimo para alguem ganhar
		if(lstMovements.size() > 5) {
			List<Position> jogadasX = new ArrayList<Position>();
			List<Position> jogadasO = new ArrayList<Position>();
			for (Movement movement : lstMovements) {
				if(movement.getPlayer().equals(TypePlayer.X)) {
					jogadasX.add(movement.getPosition());
				} else if(movement.getPlayer().equals(TypePlayer.O)) {
					jogadasO.add(movement.getPosition());
				}
			}
			//valida se existe vencedor
			if(jogadorVenceu(jogadasX) ) {
				MsgVencedor msgVencedor = new MsgVencedor();
				msgVencedor.setMsg("Partida finalizada");
				msgVencedor.setWinner("X");
				msg = msgVencedor;
				return true;				
			} else if(jogadorVenceu(jogadasO)) {
				MsgVencedor msgVencedor = new MsgVencedor();
				msgVencedor.setMsg("Partida finalizada");
				msgVencedor.setWinner("O");
				msg = msgVencedor;
				return true;				
			} else if(lstMovements.size() == 9) {
				MsgVelha msgVelha = new MsgVelha();
				msgVelha.setStatus("Partida finalizada");
				msgVelha.setWinner("Draw");
				msg = msgVelha;
				return true;
			}
		}
		return false;
	}
	
	@ApiOperation(value = "Verifica se um determinado jogador venceu")
	private boolean jogadorVenceu(List<Position> jogadas) {
		//cria lista para armazenar valor das linhas e colunas
		List<Integer> lstX = new ArrayList<Integer>();
		List<Integer> lstY = new ArrayList<Integer>();
		//contadores
		int contX = 1;
		int contY = 1;
		int diag1 = 1;
		int diag2 = 1;
		for (Position position : jogadas) {
			//coluna
			if(lstX.contains(position.getX())) {
				contX++;
			} else {
				lstX.add(position.getX());
			}
			//linha
			if(lstY.contains(position.getY())) {
				contY++;
			} else {
				lstY.add(position.getY());
			}
			//Diagonal1
			if(position.getX() == position.getY()) {
				diag1++;
			}
			//Diagonal2
			if((position.getX() == 0 && position.getY() == 2) || 
					(position.getX() == 1 && position.getY() == 1) ||
					(position.getX() == 2 && position.getY() == 0)){
				diag2++;
			}
			//linhas ou colunas
			if(contX == 3 || contY == 3 || diag1 == 3 || diag2 == 3) {
				return true;
			}
			
		}
		return false;
	}

}
