package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;

	
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake) throws InterruptedException {
		synchronized (this) {
			while (isOcupied()) {
				System.out.println("a cobra esta bloqueiada");
				wait(); //A cobra espera até que a célula fique livre.	
			}
			if (isOcupiedByGoal()) {
				 this.getGoal().captureGoal(snake);//se a cobra tentar entrar num goal, ele faz capture de um goal
				 this.gameElement = null;
			}
			ocuppyingSnake=snake;
		}
		//TODO coordination and mutual exclusion
		
	}

	public synchronized void release() {
		//TODO
		synchronized (this) {
			ocuppyingSnake=null;
			notifyAll(); //Notifica as cobras que estão à espera. 
		}
		
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}


	public synchronized void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		
		//Verifica se a célula já está ocupada por outro elemento
		if (this.gameElement==null) {
			this.gameElement=element;
		} else {
			
		}
		
		notifyAll();

	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public synchronized Goal removeGoal() {
		// TODO
		return null;
	}
	
	public synchronized void removeObstacle() {
	//TODO
		
		//Verifica se o elemento do jogo atual na célula é um obstáculo
		
		if (this.gameElement instanceof Obstacle) {
			//Remove o obstáculo na célula
			this.gameElement = null;
		}
		
		//Notifica outras threads que possam estar à espera de mudanças nesta célula
		notifyAll();
	}

	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}
	
	

}
