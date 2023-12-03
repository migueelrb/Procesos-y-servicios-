package Tarea_Hilos_Repaso;

import java.util.Random;

class Parking2 {
    private int capacidad;
    private String[] plazas;
    private Object lock = new Object();

    public Parking2(int capacidad) {
        this.capacidad = capacidad;
        this.plazas = new String[capacidad];
    }

    public int entrarParking(String matricula) {
        synchronized (lock) {
            for (int i = 0; i < capacidad; i++) {
                if (plazas[i] == null) {
                    System.out.println("Coche con matrícula " + matricula + " entró en la plaza " + i);
                    plazas[i] = matricula;
                    return i;
                }
            }
            return -1;
        }
    }

    public void salirParking(int plaza) {
        synchronized (lock) {
            System.out.println("Coche con matrícula " + plazas[plaza] + " salió de la plaza " + plaza);
            plazas[plaza] = null;
        }
    }
}

class Coche extends Thread {
    private String matricula;
    private Parking2 parking;

    public Coche(String matricula, Parking2 parking) {
        this.matricula = matricula;
        this.parking = parking;
    }

    @Override
    public void run() {
        Random rand = new Random();

        // Circulando
        int tiempoCirculando = rand.nextInt(5000) + 1; // Milisegundos
        try {
            sleep(tiempoCirculando);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Intentando entrar al parking
        int plaza = parking.entrarParking(matricula);

        if (plaza != -1) {
            // Dentro del parking
            int tiempoDentro = rand.nextInt(3000) + 1000; // Milisegundos
            try {
                sleep(tiempoDentro);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Espera antes de abandonar
            int tiempoEsperaAbandono = rand.nextInt(11000) + 10000; // Milisegundos
            try {
                sleep(tiempoEsperaAbandono);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Saliendo del parking
            parking.salirParking(plaza);
        } else {
            System.out.println("Coche con matrícula " + matricula + " no pudo entrar al parking y se va.");
        }
    }
}

public class Ejercicio3_Parte1 {
    public static void main(String[] args) {
        int capacidadParking = 10;
        Parking2 parking = new Parking2(capacidadParking);

        Coche[] coches = new Coche[40];

        for (int i = 0; i < 40; i++) {
            String matricula = "ABC-" + (i + 1);
            coches[i] = new Coche(matricula, parking);
        }

        // Iniciar todos los coches
        for (Coche coche : coches) {
            coche.start();
        }

        // Esperar a que todos los coches terminen
        for (Coche coche : coches) {
            try {
                coche.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



