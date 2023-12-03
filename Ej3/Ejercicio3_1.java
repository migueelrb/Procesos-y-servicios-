package Ej3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio3_1 {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java GrepExample <texto> <archivo_entrada> <archivo_salida>");
            return;
        }

        String texto = args[0];
        String archivoEntrada = args[1];
        String archivoSalida = args[2];

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("grep", texto, archivoEntrada);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.to(new File(archivoSalida)));
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


