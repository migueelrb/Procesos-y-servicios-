package chatTCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

public class JSSE_ClienteEco {

  private final static String COD_TEXTO = "UTF-8";

  public static void main(String[] args) {

    if (args.length < 2) {
      System.err.println("ERROR, indicar: host_servidor puerto");
      System.exit(1);
    }
    String nomHost = args[0];
    int numPuerto = Integer.parseInt(args[1]);

    System.setProperty("javax.net.ssl.trustStore", "keystoreCliCertConf");
    System.setProperty("javax.net.ssl.trustStorePassword", "pwdkeystoreconfcli");

    SSLSocketFactory sfactSSL = (SSLSocketFactory) SSLSocketFactory.getDefault();
    try (Socket socketComunicacion = sfactSSL.createSocket(nomHost, numPuerto)) {
      System.out.println("Conectado a servidor.");

      try (OutputStream osAServidor = socketComunicacion.getOutputStream();
           InputStream isDeServidor = socketComunicacion.getInputStream();
           InputStreamReader isrDeServidor = new InputStreamReader(isDeServidor, COD_TEXTO);
           OutputStreamWriter oswAServidor = new OutputStreamWriter(osAServidor, COD_TEXTO);
           BufferedWriter bwAServidor = new BufferedWriter(oswAServidor);
           BufferedReader brDeServidor = new BufferedReader(isrDeServidor);
           InputStreamReader isrStdIn = new InputStreamReader(System.in);
           BufferedReader brStdIn = new BufferedReader(isrStdIn)) {

        // Receive messages from the server in a separate thread
        new Thread(() -> {
          String receivedMessage;
          try {
            while ((receivedMessage = brDeServidor.readLine()) != null) {
              System.out.println("Server: " + receivedMessage);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();

        String lineaLeida;
        System.out.println("Introducir líneas. Línea vacía para terminar.");
        System.out.print("Línea>");
        while ((lineaLeida = brStdIn.readLine()) != null && lineaLeida.length() > 0) {
          bwAServidor.write(lineaLeida);
          bwAServidor.newLine();
          bwAServidor.flush();
          System.out.print("Línea>");
        }
      }
    } catch (UnknownHostException e) {
      System.err.println("Host desconocido: " + nomHost);
      System.exit(1);
    } catch (IOException ex) {
      System.err.println("Excepción de E/S");
      ex.printStackTrace();
      System.exit(1);
    }
  }
}
