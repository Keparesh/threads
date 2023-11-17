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

public class AutomaticSnake extends Snake {
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}

	@Override
	public void run() {
		
		//Posicionar a cobra na posição inicial do tabuleiro
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		
		//TODO: automatic movement
		
		//Loop principal da cobra
		while(true){
			
			//verifica se o tabuleiro é uma instância de LocalBoard
			if (this.getBoard() instanceof LocalBoard) {
				LocalBoard localBoard = (LocalBoard) this.getBoard();

				//verifica se o jogo ainda está ativo e se a cobra não atingiu o tamanho limite				
				//se alguma destas condições for falsa, sai do loop 
				if (!localBoard.isGameActive() || this.getSize() >= 10) {
					break;
				}
			}
			
			try {
				//Faz a cobra esperar durante um tempo antes de se mover
				Thread.sleep(this.getBoard().REMOTE_REFRESH_INTERVAL); //
					moveTowardsGoal();
									
				if (Thread.interrupted()) {
					System.out.println("Snake " + getIdentification() + "foi interrompida, recalcular rota...");
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				//A interrupção ocorre quando a cobra encontra um obstáculo
				//Espera até que o botão "Reset Snakes' Directions" seja pressionado
				
				synchronized (this) {
					System.out.println("Snake " + getIdentification() + " aguardando botão...");
					try {
						wait(); //Espera até que o botão seja pressionado
						System.out.println("Snake " + getIdentification() + " foi notificada, continuando...");			
					} catch (InterruptedException ex) {
						System.out.println("Snake " + getIdentification() + " interrompida durante a espera.");
						Thread.currentThread().interrupt();
					}	
				}
				//Após ser notificada pelo botão, recalcula a rota
				
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
		
	}
	
	
	
	
	
	
}
