package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Obstacle extends GameElement {
	
	
	private static final int NUM_MOVES=3;
	private static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	
	//NÃO SEI SE POSSO METER ISTO AQUI
	private BoardPosition position;
	
	public Obstacle(Board board) {
		super();
		this.board = board;
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}
	
	
	public void decrementRemainingMoves() {
		if (remainingMoves > 0) {
			remainingMoves--;
		}
	}

	public void setPosition(BoardPosition position) {
		this.position = position;
	}
	
	//NÃO SEI SE POSSO METER ESTE MÉTODO
	public BoardPosition getPosition() {
		return this.position;
	}
	
	//NÃO SEI SE POSSO METER ESTE MÉTODO
	public static int getObstacleMoveInterval() {
		return OBSTACLE_MOVE_INTERVAL;
	}
	
}
