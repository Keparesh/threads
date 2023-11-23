package game;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;
import game.Goal;

public class AutomaticSnake extends Snake {
	
	LocalBoard board;
	
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);
		this.board = board;

	}

	
	@Override
	public void run() {
		
		//Posicionar a cobra na posição inicial do tabuleiro
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		
		while(this.getBoard().getGoalValue()<10) {
			try {
				Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
				Cell currentCell = cells.getLast();
				BoardPosition currentPosition = currentCell.getPosition();
				
				
				// Obtém as posições vizinhas da célula atual 
				
				List<BoardPosition> neighbors = getBoard().getNeighboringPositions(currentCell);
				
				
				// Obtém a posição do objetivo do tabuleiro
				
				BoardPosition goalPosition = getBoard().getGoalPosition();
				
				
				// Inicializa variáveis para armazenar a melhor posição e a menor distância
				
				BoardPosition bestPosition = null;
				double minDistance = Double.MAX_VALUE;
				
				
				// Iterar sobre todas as posições vizinhas para encontrar a posição mais próxima do objetivo
				
				for (BoardPosition neighbor : neighbors) {
					Cell neighborCell = getBoard().getCell(neighbor);
					
					//AQUI antes estava "isOcupied()"
					//AQUI Se a célula vizinha não estiver ocupada, calcula a distância até ao objetivo
					
					if (!neighborCell.isOcupiedBySnake()) {
						double distance = neighbor.distanceTo(goalPosition);
					
						//Atualizar a melhor posição se esta for a menor distância encontrada até agora
						
						if (distance < minDistance ) {
							
							//Atualiza a melhor posição e a menor distancia
							
							minDistance = distance;
							bestPosition = neighbor;
						}
					}
				}
				
				//Se uma posição válida for encontrada 
				
				if (bestPosition != null) {
					
					//Obter a célula correspondente à melhor posição
					
					Cell nextCell = getBoard().getCell(bestPosition);
					
					//Mover a cobra para a posição escolhida
					
					this.move(nextCell);
					
				} 

			} catch (Exception e) {
				// TODO: handle exceptio
				e.printStackTrace();
			}
			
		}
		

	}


	
}


//quando clica no botao faz interrupt da thread, e depois usa uma flag a dizer que posso ir para todas as pos menos esta