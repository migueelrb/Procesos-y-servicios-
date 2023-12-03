package Ej4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio4_1 {
    public static void main(String[] args) {
        try {
            // Crear el proceso de Powershell
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe");
            Process process = processBuilder.start();

            // Obtener el flujo de salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Leer la salida del proceso línea por línea
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

