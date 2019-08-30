package com.br.jogodavelha.Jogo.da.Velha;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.br.jogodavelha.model.Game;
import com.br.jogodavelha.model.Movement;
import com.br.jogodavelha.model.Position;
import com.br.jogodavelha.model.TypePlayer;
import com.br.jogodavelha.repository.GameRepository;
import com.br.jogodavelha.repository.MovementRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class JogoDaVelhaApplicationTests {
	
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private MovementRepository movementRepository;
	
	@LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
	
	@Before
    public void setUp() throws Exception {
 
        //Deletamos todos os registros do banco
		gameRepository.deleteAll();
		movementRepository.deleteAll();
 
        //Inserimos alguns games
		gameRepository.save(new Game(Long.valueOf(1), TypePlayer.O));
		gameRepository.save(new Game(Long.valueOf(2), TypePlayer.X));
		
		restTemplate = new RestTemplate();
	}
	
	@Test
	 public void testCreateGame() throws URISyntaxException{
        Game game = new Game();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("CONTENT-TYPE", "application/json");     
 
        HttpEntity<Game> request = new HttpEntity<>(game, headers);

        Object response = restTemplate.postForObject("http://localhost:"+randomServerPort+"/game", request, String.class);
        ResponseEntity<String> result = (ResponseEntity<String>) response;
         
        //Verify request succeed
        Assert.assertEquals(201, result.getStatusCodeValue());		
    }
	
	@Test
	public void testMovimentoJogador() throws URISyntaxException{
		//mov1
		Movement mov1 = new Movement();
		mov1.setId(Long.valueOf(1));
		mov1.setPlayer(TypePlayer.O);
		Position pos1 = new Position();
		pos1.setX(1);
		pos1.setY(0);
		mov1.setPosition(pos1);
		//mov2
		Movement mov2 = new Movement();
		mov2.setId(Long.valueOf(1));
		mov2.setPlayer(TypePlayer.X);
		Position pos2 = new Position();
		pos2.setX(0);
		pos2.setY(0);
		mov2.setPosition(pos2);
		//mov3
		Movement mov3 = new Movement();
		mov3.setId(Long.valueOf(1));
		mov3.setPlayer(TypePlayer.O);
		Position pos3 = new Position();
		pos3.setX(1);
		pos3.setY(1);
		mov3.setPosition(pos3);
		//moov4
		Movement mov4 = new Movement();
		mov4.setId(Long.valueOf(1));
		mov4.setPlayer(TypePlayer.X);
		Position pos4 = new Position();
		pos4.setX(1);
		pos4.setY(2);
		mov4.setPosition(pos4);
		//mov5
		Movement mov5 = new Movement();
		mov5.setId(Long.valueOf(1));
		mov5.setPlayer(TypePlayer.O);
		Position pos5 = new Position();
		pos5.setX(1);
		pos5.setY(2);
		mov5.setPosition(pos5);
		
		List<Movement> lstMov = new ArrayList<Movement>();
		lstMov.add(mov1);
		lstMov.add(mov2);
		lstMov.add(mov3);
		lstMov.add(mov4);
		lstMov.add(mov5);

		for (Movement movement : lstMov) {

			HttpHeaders headers = new HttpHeaders();
			headers.set("CONTENT-TYPE", "aplication/json");     

			HttpEntity<Movement> request = new HttpEntity<>(movement, headers);

			Object response = restTemplate.postForObject("http://localhost:"+randomServerPort+"/game/" + mov1.getId() +"/movement", request, String.class);
	        ResponseEntity<String> result = (ResponseEntity<String>) response;

			//Verify request succeed
			Assert.assertEquals(201, result.getStatusCodeValue());
		}
		
				
	}

}
