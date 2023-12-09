package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import environment.Board;

public class ClientHandler extends Thread {

    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private HumanSnake snake;

    public ClientHandler(Socket clientSocket, Server server, HumanSnake snake) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.snake = snake;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            String key;
            while ((key = in.readLine()) != null) {
            	System.out.println("Client pressed: " + key);
            	snake.moveByDirections(key); //Manda à HumanSnake a tecla pressionada
            	
            	//sendMessage("Eco: " + key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Board board) throws IOException {
    	ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
    	output.writeObject(board);
    	output.flush();
    }
	
}
