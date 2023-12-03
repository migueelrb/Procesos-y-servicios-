package T3Ej3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

class GameState {
    int numberToGuess;
    boolean gameEnded;
    String winner;
    int maxAttempts = 5;
    int attemptsLeft;

    public GameState() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1; // Número aleatorio entre 1 y 100
        gameEnded = false;
        winner = null;
        attemptsLeft = maxAttempts;
    }

    public synchronized boolean makeGuess(int guess, String playerName) {
        if (!gameEnded) {
            attemptsLeft--;

            if (guess == numberToGuess) {
                gameEnded = true;
                winner = playerName;
                System.out.println(playerName + " ha ganado!");
            } else if (attemptsLeft == 0) {
                gameEnded = true;
                winner = "Nadie, el número era " + numberToGuess;
                System.out.println("El juego ha terminado. Nadie ha ganado.");
            }

            return true;
        } else {
            return false;
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameState gameState;
    private String playerName;

    public ClientHandler(Socket socket, GameState gameState) {
        this.clientSocket = socket;
        this.gameState = gameState;

        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            playerName = (String) in.readObject();
            System.out.println("Nuevo jugador: " + playerName);

            while (!gameState.gameEnded) {
                int guess = (int) in.readObject();
                boolean correctGuess = gameState.makeGuess(guess, playerName);
                out.writeObject(correctGuess);
                out.writeObject(gameState.gameEnded ? gameState.winner : null);
            }

            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Ejercicio {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor en ejecución...");

            GameState gameState = new GameState();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, gameState);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
