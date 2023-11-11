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
		
		while(this.getSize() < 10){
			try {
				Thread.sleep(this.getBoard().REMOTE_REFRESH_INTERVAL);
				
				// Movimento até alcançar o tamanho 10
				BoardPosition actualPos = this.getCells().getLast().getPosition();
				BoardPosition nextPos = new BoardPosition(actualPos.x+1,actualPos.y); 
				Cell nextCell = this.getBoard().getCell(nextPos);
				this.move(nextCell);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt(); //Preserva o estado de interrupção
			}
		}
		
		//TODO: automatic movement
		
		//Após alcançar o tamanho 10, implementa o movimento automático mais avançado 
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(this.getBoard().REMOTE_REFRESH_INTERVAL);
				moveTowardsGoal();	
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
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
			
			//Calcular a distância da posição vizinha até ao objetivo
			double distance = neighbor.distanceTo(goalPosition);
			
			// Se a distancia for menor do que a menor distancia já encontrada e a célula não estiver ocupada...
			if (distance < minDistance && !getBoard().getCell(neighbor).isOcupied()) {
				
				//Atualiza a melhor posição e a menor distancia
				minDistance = distance;
				bestPosition = neighbor;
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
