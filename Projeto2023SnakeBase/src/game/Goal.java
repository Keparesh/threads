package game;

import environment.Board;
import environment.LocalBoard;

public class Goal extends GameElement  {
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		//TODO
		this.value ++;
	}

	public Board getBoard() {
		return this.board;
	}
	
	public int captureGoal() throws InterruptedException {
//		TODO
		this.incrementValue();
		getBoard().setGoalPosition(getBoard().findRandomFreePosition());
		
		return -1;
	}
}
