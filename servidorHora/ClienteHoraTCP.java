package servidorHora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteHoraTCP {

    public static void main(String[] args) {
        final String direccionServidor = "localhost";
        final int puertoServidor = 12345;

        try {
            Socket socket = new Socket(direccionServidor, puertoServidor);

            // Leer la fecha y hora del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fechaHora = in.readLine();

            System.out.println("Fecha y Hora del Servidor: " + fechaHora);

            // Cerrar la conexión con el servidor
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

