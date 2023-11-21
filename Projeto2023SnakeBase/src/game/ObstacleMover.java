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
		while (this.obstacle.getRemainingMoves() > 0) {
			try {
				BoardPosition new_position = board.findRandomFreePosition();//arranja uma board position nova
				Cell new_cell = board.getCell(new_position);//vai buscar a cell daquela position
				obstacle.move(new_cell);
				Thread.sleep(obstacle.getSleepTime());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	


}


