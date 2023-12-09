package remote;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import gui.SnakeGui;

/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {
	private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    private void listenForMessages(RemoteBoard board) {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Eco: " + message);
                //"descerealização" (converter de texto para objetos) 
                //atualizar a board com o estado do jogo
                //depois é que faço o setChanged
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public static void main(String[] args) {
        try {
        	
        	Client client = new Client("localhost", 8080);
        	RemoteBoard remoteBoard = new RemoteBoard(client);
        	SnakeGui snakeGui = new SnakeGui(remoteBoard, 600, 0);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
    public void closeEverything() {
    	try {
            if (socket != null) {
                socket.close(); // Fecha o socket do cliente
            }
            if (reader != null) {
                reader.close(); // Fecha o BufferedReader
            }
            if (writer != null) {
                writer.close(); // Fecha o PrintWriter
            }
            if (scanner != null) {
                scanner.close(); // Fecha o Scanner
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
