package servidorHora;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorHoraTCP {

    public static void main(String[] args) {
        final int puerto = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor de Hora TCP iniciado en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

                // Obtener la fecha y hora actual
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaHora = sdf.format(new Date());

                // Enviar la fecha y hora al cliente
                PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
                out.println(fechaHora);

                // Cerrar la conexión con el cliente
                clienteSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

