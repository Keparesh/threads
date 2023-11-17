package game;

import environment.Cell;
import environment.LocalBoard;
import environment.BoardPosition;
import environment.Board;


public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		// TODO
		
		try {
			while (obstacle.getRemainingMoves() > 0) {
				
				//Aguardar o intervalo definido antes de mover o obstáculo
				Thread.sleep(Obstacle.getObstacleMoveInterval());
				
				//Escolher uma nova posição para o obstáculo
				BoardPosition newPosition = board.findRandomFreePosition();
				
				//Mover o obstáculo para a nova posição encontrada
				moveObstacleToNewPosition(newPosition);
				
				//Decrementar o número de movimentos restantes
				obstacle.decrementRemainingMoves();
				
			}
			
		} catch (InterruptedException e) {
			
			//Thread foi interrompida, terminar execução
			Thread.currentThread().interrupt();
		}
	}
	
	private void moveObstacleToNewPosition(BoardPosition newPosition) {
		
		//Obter célula atual onde o obstáculo está localizado
		Cell currentCell = board.getCell(obstacle.getPosition());
		
		//Remover o obstáculo da posição atual
		currentCell.removeObstacle();
		
		//Obter a nova célula para onde o obstáculo será movido
		Cell newCell = board.getCell(newPosition);
		
		//Adicionar o obstáculo à nova céula encontrada anteriormente
		newCell.setGameElement(obstacle);
		
		//Atualizar a posição do obstáculo
		obstacle.setPosition(newPosition);
		
		//Notificar mudanças no tabuleiro
		board.setChanged();
	
	}

}


// é suposto as cobras bloquearem nos obstáculos e só se desviam quando clicamos no botão. 
// é suposto as cobras evitarem posições onde elas estão
// método setGameElement tem de ser alterado
