package T2Ej3.C;

class Contadores {
    private long cont1 = 0;
    private long cont2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void incrementar1() {
        synchronized (lock1) {
            cont1++;
        }
    }

    public long getContador1() {
        synchronized (lock1) {
            return cont1;
        }
    }

    public void incrementar2() {
        synchronized (lock2) {
            cont2++;
        }
    }

    public long getContador2() {
        synchronized (lock2) {
            return cont2;
        }
    }
}

public class PruebaContadores {
    public static void main(String[] args) {
        Contadores contadores = new Contadores();

        Runnable incrementarContador1 = () -> {
            for (int i = 0; i < 50000000; i++) {
                contadores.incrementar1();
            }
        };

        Runnable incrementarContador2 = () -> {
            for (int i = 0; i < 50000000; i++) {
                contadores.incrementar2();
            }
        };

        Thread[] hilos = new Thread[10];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(incrementarContador1);
            hilos[i + 5] = new Thread(incrementarContador2);
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Contador 1: " + contadores.getContador1());
        System.out.println("Contador 2: " + contadores.getContador2());
    }
}

