package Tarea_Hilos_Repaso;

import java.util.Random;

class Parking {
    private int capacidad;
    private String[] plazas;
    private Object lock = new Object();

    public Parking(int capacidad) {
        this.capacidad = capacidad;
        this.plazas = new String[capacidad];
    }

    public int entrarParking(String matricula, boolean esFurgoneta) {
        synchronized (lock) {
            int ocupadas = 0;
            int inicio = esFurgoneta ? 0 : 1;
            int paso = esFurgoneta ? 2 : 1;

            for (int i = inicio; i < capacidad; i += paso) {
                if (plazas[i] == null && (esFurgoneta || (i + 1 < capacidad && plazas[i + 1] == null))) {
                    System.out.println("Vehículo con matrícula " + matricula + " entró en la plaza " + i);
                    plazas[i] = matricula;
                    if (esFurgoneta) {
                        plazas[i + 1] = matricula;
                    }
                    return i;
                }
            }
            return -1;
        }
    }

    public void salirParking(int plaza, boolean esFurgoneta) {
        synchronized (lock) {
            System.out.println("Vehículo con matrícula " + plazas[plaza] + " salió de la plaza " + plaza);
            plazas[plaza] = null;
            if (esFurgoneta) {
                plazas[plaza + 1] = null;
            }
        }
    }
}

class Vehiculo extends Thread {
    private String matricula;
    private Parking parking;
    private boolean esFurgoneta;

    public Vehiculo(String matricula, Parking parking, boolean esFurgoneta) {
        this.matricula = matricula;
        this.parking = parking;
        this.esFurgoneta = esFurgoneta;
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
        int plaza = parking.entrarParking(matricula, esFurgoneta);

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
            parking.salirParking(plaza, esFurgoneta);
        } else {
            System.out.println("Vehículo con matrícula " + matricula + " no pudo entrar al parking y se va.");
        }
    }
}

public class Ej3Parte2 {
    public static void main(String[] args) {
        int capacidadParking = 20;
        Parking parking = new Parking(capacidadParking);

        Vehiculo[] vehiculos = new Vehiculo[50]; // 40 coches + 10 furgonetas

        for (int i = 0; i < 40; i++) {
            String matricula = "ABC-" + (i + 1);
            vehiculos[i] = new Vehiculo(matricula, parking, false);
        }

        for (int i = 40; i < 50; i++) {
            String matricula = "FGH-" + (i - 39);
            vehiculos[i] = new Vehiculo(matricula, parking, true);
        }

        // Iniciar todos los vehículos
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.start();
        }

        // Esperar a que todos los vehículos terminen
        for (Vehiculo vehiculo : vehiculos) {
            try {
                vehiculo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

