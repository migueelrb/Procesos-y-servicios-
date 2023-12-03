package T2Ej1;

import java.util.Random;

public class Ejercicio1 extends Thread {
    private String nombre;

    public Ejercicio1(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                int distancia = random.nextInt(100) + 1;
                System.out.println(nombre + " ha recorrido " + distancia + " metros en el tramo " + i);
                Thread.sleep(1000); // Esperar 1 segundo entre tramos
            }
            System.out.println(nombre + " ha terminado la carrera!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 class Carrera {
    public static void main(String[] args) {
        Ejercicio1 corredor1 = new Ejercicio1("Corredor 1");
        Ejercicio1 corredor2 = new Ejercicio1("Corredor 2");
        Ejercicio1 corredor3 = new Ejercicio1("Corredor 3");
        Ejercicio1 corredor4 = new Ejercicio1("Corredor 4");
        Ejercicio1 corredor5 = new Ejercicio1("Corredor 5");

        corredor1.start();
        corredor2.start();
        corredor3.start();
        corredor4.start();
        corredor5.start();
    }
}

