package game;


import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	private Board board;
	
	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}
	

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	
	protected void move(Cell cell) throws InterruptedException {
		
		//pede para ocupar a célula
		cell.request(this);
		
		//adiciona a célula à lista de células ocupadas pela cobra
		cells.add(cell);
		
		//se o tamanho da cobra exceder o tamanho atual, remove a útltima célula
		if (getSize() < cells.size()) {
			Cell last = cells.removeFirst();
			last.release();
			
		}
		
		//Notifica mudanças no tabuleiro
		getBoard().setChanged();
		
		
		//verifica se o tamanho da cobra atingiu ou excedeu o limite
		if (this.getSize() >= 10) {
			
			//verifica se o tabuleiro é uma instância da LocalBoard
			if (this.getBoard() instanceof LocalBoard ) {
				//faz o cast para LocalBoard e invoca o endGame
				LocalBoard localBoard = (LocalBoard) getBoard();
				localBoard.endGame();
			}
		}
		
	}
	
	
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}
	
	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX,  posY);
		
		while(getBoard().getCell(at).isOcupied()) {
			posY = (int) (Math.random() * Board.NUM_ROWS); //se houver posicoes iniciais iguais, ele corre o random de novo.
			at=new BoardPosition(posX, posY);
		}
		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
		
		
		System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void recalculateRoute() {
		
		Cell currentCell = cells.getLast();
		BoardPosition goalPosition = getBoard().getGoalPosition();
		
		List<BoardPosition> alternativeRoutes = new ArrayList<>();
		
		//Encontra rotas alternativas que não estão bloqueadas por obstáculos ou pelo prórpio corpo da cobra
		
		for (BoardPosition neighbor : getBoard().getNeighboringPositions(currentCell)) {
			Cell neighborCell = getBoard().getCell(neighbor);
			if (!neighborCell.isOcupied()) {
				System.out.println("OKKKKKKKK");
				alternativeRoutes.add(neighbor);
			}
		}
		
		//Escolhe a rota alternativa que está mais próxima do prémio
		BoardPosition bestPosition = null;
		double minDistance = Double.MAX_VALUE;
		for (BoardPosition position : alternativeRoutes) {
			double distance = position.distanceTo(goalPosition);
			if (distance < minDistance) {
				minDistance = distance;
				bestPosition = position;
			}
		}
		
		if (bestPosition != null) {
			try {
				Cell nextCell = getBoard().getCell(bestPosition);
				this.move(nextCell);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	
}
