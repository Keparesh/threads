package game;

import java.util.LinkedList;

import environment.Board;
import environment.Cell;
import environment.BoardPosition;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */

public class HumanSnake extends Snake {

	private LinkedList<Cell> cells;
	
	
	public HumanSnake(int id,Board board) {
		super(id,board);
		cells = new LinkedList<>();
	}

	
	public void moveByDirections(String direction) {
		
		//recebe direção e tenta jogar nessa direção
		
		
		
		
		
		
		
		
		try {
			Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		// Primeiro, obtenha a célula atual da cabeça da cobra
        Cell currentHead = getHeadCell();
        
        // Calcule a nova posição baseada na direção atual
        Cell nextCell = calculateNextCell(currentHead, direction);
        
        
        if (nextCell != null && getBoard().isValidPosition(nextCell.getPosition().x, nextCell.getPosition().y)) {
            try {
                if (nextCell.isOcupiedByGoal()) {
                    eatGoal(nextCell);
                } else if (!nextCell.isOcupied()) {
                    move(nextCell);
                }
                // Se a célula estiver ocupada por um obstáculo, a cobra não se move.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Se a célula estiver fora dos limites, a cobra também não se move.
         
         */
	}
	
	
	
	
}
