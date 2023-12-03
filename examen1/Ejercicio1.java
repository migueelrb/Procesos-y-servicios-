package examen1;

import java.util.Random;

class GestorCruce {
    private boolean semaforoNorteVerde = true;
    private boolean semaforoOesteVerde = false;
    private Object lock = new Object();

    public void llegaNorte() {
        synchronized (lock) {
            while (!semaforoNorteVerde) {
                System.out.println("Coche del Norte esperando.");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Coche del Norte pasa.");
        }
    }

    public void llegaOeste() {
        synchronized (lock) {
            while (!semaforoOesteVerde) {
                System.out.println("Coche del Oeste esperando.");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Coche del Oeste pasa.");
        }
    }

    public void sale() {
        synchronized (lock) {
            System.out.println("Coche sale del cruce.");
            lock.notifyAll();
        }
    }

    public void cambiaSemaforos() {
        synchronized (lock) {
            semaforoNorteVerde = !semaforoNorteVerde;
            semaforoOesteVerde = !semaforoOesteVerde;

            if (semaforoNorteVerde) {
                System.out.println("Semaforo del Norte en verde.");
            } else {
                System.out.println("Semaforo del Norte en rojo.");
            }

            if (semaforoOesteVerde) {
                System.out.println("Semaforo del Oeste en verde.");
            } else {
                System.out.println("Semaforo del Oeste en rojo.");
            }

            lock.notifyAll();
        }
    }
}

class Coche implements Runnable {
    private String direccion;
    private GestorCruce gestor;

    public Coche(String direccion, GestorCruce gestor) {
        this.direccion = direccion;
        this.gestor = gestor;
    }

    @Override
    public void run() {
        if (direccion.equals("Norte")) {
            gestor.llegaNorte();
        } else if (direccion.equals("Oeste")) {
            gestor.llegaOeste();
        }

        try {
            Thread.sleep(4000); // Simula el tiempo que tarda en pasar el cruce
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gestor.sale();
    }
}

public class Ejercicio1 {

    public static void main(String[] args) {
        GestorCruce gestor = new GestorCruce();

        Thread controlador = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000); // Cambia los semáforos cada 10 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gestor.cambiaSemaforos();
            }
        });

        controlador.start();

        // Crear y arrancar varios coches mezclados desde el Norte y el Oeste
        for (int i = 0; i < 60; i++) {
            String direccion = (i % 2 == 0) ? "Norte" : "Oeste";
            Thread coche = new Thread(new Coche(direccion, gestor));
            coche.start();

            // Introduce un pequeño retraso aleatorio antes de crear el siguiente coche
            try {
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
