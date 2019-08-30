# jogo-da-velha
Api com serviços para criar e jogar jogo da velha 
O funcionamento da partida será baseado em turnos, a cada momento um jogador realiza a jogada.

POST - /game
Essa chamada criara uma nova partida e retornará o id da partida criada. Além do id ele vai sortear qual jogador ira começar a partida o "X" ou o "O".
Exemplo de returno:

{
	"id" : "1",
	"firstPlayer": "X"
}

POST - /game/{id}/movement
Essa chamada fará o movimento de cada jogador.
Input:
{
	"id" : "1",
	"player": "X",
	"position": {
		"x": 0,
		"y": 1
		}
}

As coordenadas X e Y representam a posição no tabuleiro do movimento. Começando do índice 0, no canto inferior esquerdo. De forma que o tabuleiro fica assim:

(x=0 y=2) | (x=1 y=2) | (x=2 y=2)

----------|-----------|----------

(x=0 y=1) | (x=1 y=1) | (x=2 y=1)

----------|-----------|----------

(x=0 y=0) | (x=1 y=0) | (x=2 y=0)
