package environment;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.ObstacleMover;
import game.Server;
import game.Snake;
import game.AutomaticSnake;


/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board{
	
	private static final int NUM_SNAKES = 2;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	private ExecutorService threadPoolObstaculos;
	
	private volatile boolean gameActive = true;
	
	public LocalBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles( NUM_OBSTACLES);
		threadPoolObstaculos=Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
		
		
		Goal goal=addGoal();
		// System.err.println("All elements placed");
	}
	

	public void init() {
		
		for(Snake s:snakes)
			s.start();
		
		// TODO: launch other threads
		

		for (Obstacle obs : this.getObstacles()) {
			ObstacleMover obsmover= new ObstacleMover(obs, this);
			threadPoolObstaculos.execute(obsmover);
		}
		
		setChanged();
	}

	public void resetSnakesDirections() {
		System.out.println("Reseting snakes directions");
		//Percorre todas as cobras e notifica-as para continuar movendo
		
		for (Snake snake : snakes) {
			synchronized (snake) {
				System.out.println("Notifying snake: " + snake.getIdentification());
				snake.recalculateRoute();

				
			}
		}
		
	}
	
	
	public synchronized void endGame() {
		gameActive = false;
		notifyAll();
	}
	
	public boolean isGameActive() {
		return gameActive;
	}

	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}

	
	






}
