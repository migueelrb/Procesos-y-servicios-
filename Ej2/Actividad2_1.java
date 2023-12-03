package Ej2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Actividad2_1 {
    public static void main(String[] args) {
        try {
            // Obtener el directorio de ejecución por defecto
            String directorioPorDefecto = System.getProperty("user.dir");
            System.out.println("Directorio por defecto: " + directorioPorDefecto);

            // Crear un objeto ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder();

            // Asignar el directorio por defecto al ProcessBuilder
            processBuilder.directory(new File(directorioPorDefecto));

            // Ejecutar el comando en diferentes directorios
            List<String> comandos = List.of("ls", "-l");
            List<String> directorios = List.of("/tmp", "/var");

            for (String directorio : directorios) {
                processBuilder.directory(new File(directorio));
                processBuilder.command(comandos);

                // Ejecutar el proceso
                Process proceso = processBuilder.start();

                // Leer la salida del proceso
                java.io.InputStream inputStream = proceso.getInputStream();
                java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
                String resultado = scanner.hasNext() ? scanner.next() : "";
                System.out.println("Resultado en el directorio " + directorio + ":");
                System.out.println(resultado);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

