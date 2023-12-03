package Ej2;

import java.util.Map;

import java.util.Map;

public class Actividad2_2 {
    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();

        for (Map.Entry<String, String> entry : environment.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}


