package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import environment.Board;
import environment.LocalBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Server {
	// TODO
	
	private int port;
    private Map<Integer, ClientHandler> clientHandlers;
    private ServerSocket serverSocket;
    private LocalBoard localBoard;

    public Server(int port, LocalBoard board) {
        this.port = port;
        this.clientHandlers = Collections.synchronizedMap(new HashMap<>());
        this.localBoard = board; 
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                HumanSnake snake = new HumanSnake(0,localBoard);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this, snake); //O server está a mandar a socket para o ClientHandler, para este tratar das teclas pressionadas. 
                clientHandler.start();   
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateGameState() throws IOException{
    	while(localBoard.isGameActive()) {
    		sendBoardToClients(localBoard);
    		try {
    			Thread.sleep(localBoard.PLAYER_PLAY_INTERVAL);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	sendBoardToClients(localBoard);
    }
    
    public void sendBoardToClients(LocalBoard localBoard) throws IOException {
        for (ClientHandler handler : clientHandlers.values()) {
            handler.sendMessage(localBoard);
        }
    }

	
}
