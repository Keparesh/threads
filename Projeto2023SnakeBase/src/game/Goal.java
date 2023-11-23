package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Goal extends GameElement  {
	private static int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public static int getValue() {
		return value;
	}
	
	public void incrementValue() throws InterruptedException {
		//TODO
		this.value ++;
	}

	public Board getBoard() {
		return this.board;
	}
	
	public int captureGoal(Snake snake) throws InterruptedException {
		this.incrementValue();
		if(Goal.value<10) {
//		TODO
		BoardPosition pos = getBoard().findRandomFreePosition();
		getBoard().setGoalPosition(pos);
		getBoard().getCell(pos).setGameElement((GameElement) this);
		getBoard().setChanged();
		snake.size++;
		}
		
		
		return -1;
	}
}
