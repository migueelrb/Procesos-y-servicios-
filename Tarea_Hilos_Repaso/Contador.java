package Tarea_Hilos_Repaso;
class Contador {

    private int cuenta = 0;
    private int maximo;

    Contador(int valorInicial, int maximo) {
        this.cuenta = valorInicial;
        this.maximo = maximo;
    }

    synchronized public int getCuenta() {
        return cuenta;
    }

    synchronized public int incrementa() {
        while (cuenta >= maximo) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.cuenta++;
        notifyAll();
        return cuenta;
    }

    synchronized public int decrementa() {
        while (cuenta < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.cuenta--;
        notifyAll();
        return cuenta;
    }

}

class HiloIncr implements Runnable {

    private final String id;
    private final Contador cont;

    HiloIncr(String id, Contador c) {
        this.id = id;
        this.cont = c;
    }

    @Override
    public void run() {
        while (true) {
            int cuenta = this.cont.incrementa();
            System.out.printf("Hilo %s incrementa, valor contador: %d\n", this.id, cuenta);
        }
    }
}

class HiloDecr implements Runnable {

    private final String id;
    private final Contador cont;

    HiloDecr(String id, Contador c) {
        this.id = id;
        this.cont = c;
    }

    @Override
    public void run() {
        while (true) {
            int cuenta = this.cont.decrementa();
            System.out.printf("Hilo %s decrementa, valor contador: %d\n", this.id, cuenta);
        }
    }
}

class HilosIncDec {

    private static final int NUM_HILOS_INC = 10;
    private static final int NUM_HILOS_DEC = 10;
    private static final int MAXIMO = 100;

    public static void main(String[] args) {
        Contador c = new Contador(0, MAXIMO);
        Thread[] hilosInc = new Thread[NUM_HILOS_INC];
        for (int i = 0; i < NUM_HILOS_INC; i++) {
            Thread th = new Thread(new HiloIncr("INC" + i, c));
            hilosInc[i] = th;
        }
        for (int i = 0; i < NUM_HILOS_INC; i++) {
            hilosInc[i].start();
        }
        Thread[] hilosDec = new Thread[NUM_HILOS_DEC];
        for (int i = 0; i < NUM_HILOS_DEC; i++) {
            Thread th = new Thread(new HiloDecr("DEC" + i, c));
            hilosDec[i] = th;
        }
        for (int i = 0; i < NUM_HILOS_DEC; i++) {
            hilosDec[i].start();
        }
    }

}

