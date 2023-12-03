package Ej3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Ejercicio3_2 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Debe proporcionar la ruta de un directorio como argumento.");
            return;
        }

        String directorioPath = args[0];
        File directorio = new File(directorioPath);

        if (!directorio.exists()) {
            System.out.println("El directorio no existe.");
            return;
        }

        if (!directorio.isDirectory()) {
            System.out.println("El argumento no corresponde a un directorio.");
            return;
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ls", "-lF", directorioPath);
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
