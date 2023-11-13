package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		//TODO: automatic movement
		
		while(this.getSize() < 10){
			try {
				Thread.sleep(this.getBoard().REMOTE_REFRESH_INTERVAL); //
				
				moveTowardsGoal();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt(); //Preserva o estado de interrupção
			}
		}
	}
	
	private void moveTowardsGoal() throws InterruptedException {
		// Obter a célula atual onde está a cabeça da cobra
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
			
			//Se a célula vizinha não estiver ocupada, calcula a distância até ao objetivo
			if (!neighborCell.isOcupied()) {
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
		
	}
	
}
