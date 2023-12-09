package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.Obstacle;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	private Client client;
	
	
	public RemoteBoard(Client client) {
		this.client = client;
	}
	
	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
		
		/*
		 * Converter em texto o keyCode para a direção 
		 */
	
		client.sendMessage("");
		
	}

	@Override
	public void handleKeyRelease() {
		// TODO
		
		/*
		 * fazer depois
		 */
		
	}

	@Override
	public void init() {
		// TODO 
		
		/*
		 * inicializar o jogo (ver local board)
		 * correr o método do client que está sempre a receber updates (listenForMessages) client.listenForMessages(this)
		 * 
		 */
		
		
		
	}


	

}
