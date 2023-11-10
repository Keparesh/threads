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
				BoardPosition actualPos = this.getCells().getLast().getPosition();
				BoardPosition nextPos = new BoardPosition(actualPos.x+1,actualPos.y); 
				Cell nextCell = this.getBoard().getCell(nextPos);
				this.move(nextCell);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//TODO: automatic movement
	}
	

	
}
