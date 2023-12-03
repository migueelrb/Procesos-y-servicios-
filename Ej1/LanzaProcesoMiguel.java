package Ej1;

import java.io.IOException;

public class LanzaProcesoMiguel {

    public static void main(String[] args) {
        try {
            Process proceso = Runtime.getRuntime().exec("bash bucle.sh");

            while (proceso.isAlive()) {
                System.out.println("El proceso está en ejecución.");
                Thread.sleep(3000); // Pausa de 3 segundos
            }

            System.out.println("El proceso ha terminado.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}