package examen1;

class Monitor {

    // Uso tres variables booleanas para simular la disponibilidad de los recursos
    private boolean r1Disponible = true;
    private boolean r2Disponible = true;
    private boolean r3Disponible = true;

    /**
     * Este método se ejecuta cuando un hilo quiere usar el recurso R1, el método comprueba con un while si el recurso
     * está disponible, si no es así, el hilo se queda esperando hasta que el recurso se libere., despues muestra un
     * mensaje por pantalla y cambia el estado del recurso a no disponible. Con el finally se notifica a todos los hilos
     * que estén esperando en el recurso.
     * @param nombre
     * @throws InterruptedException
     */
    synchronized void requiereR1(String nombre) throws InterruptedException {
        while (!r1Disponible) {
            wait();
        }
        System.out.println(nombre + " comienza a usar R1.");
        r1Disponible = false;
    }

    /**
     * Este método es igual que el anterior pero para los hilos que quieren usar los recursos R2 y R3.
     * @param nombre
     * @throws InterruptedException
     */
    synchronized void requiereR2_R3(String nombre) throws InterruptedException {
        while (!r2Disponible || !r3Disponible) {
            wait();
        }
        System.out.println(nombre + " comienza a usar R2 y R3.");
        r2Disponible = false;
        r3Disponible = false;
    }

    /**
     * Este método es igual que el anterior pero para los hilos que quieren usar los recursos R1, R2 y R3.
     * @param nombre
     * @throws InterruptedException
     */
    synchronized void requiereR1_R2_R3(String nombre) throws InterruptedException {
        while (!r1Disponible || !r2Disponible || !r3Disponible) {
            wait();
        }
        System.out.println(nombre + " comienza a usar R1, R2 y R3.");
        r1Disponible = false;
        r2Disponible = false;
        r3Disponible = false;
    }

    /**
     * Este método se ejecuta cuando un hilo deja de usar el recurso R1, muestra un mensaje por pantalla y cambia el
     * estado del recurso a disponible. Después notifica a todos los hilos que estén esperando en el recurso.
     * @param nombre
     */
    synchronized void liberaR1(String nombre) {
        System.out.println(nombre + " deja de usar R1.");
        r1Disponible = true;
        notifyAll();
    }

    /**
     * Este método es igual que el anterior pero para los hilos que dejan de usar los recursos R2 y R3.
     * @param nombre
     */
    synchronized void liberaR2_R3(String nombre) {
        System.out.println(nombre + " deja de usar R2 y R3.");
        r2Disponible = true;
        r3Disponible = true;
        notifyAll();
    }

    /**
     * Este método es igual que el anterior pero para los hilos que dejan de usar los recursos R1, R2 y R3.
     * @param nombre
     */
    synchronized void liberaR1_R2_R3(String nombre) {
        System.out.println(nombre + " deja de usar R1, R2 y R3.");
        r1Disponible = true;
        r2Disponible = true;
        r3Disponible = true;
        notifyAll();
    }
}

class Hilo implements Runnable {
    private Monitor monitor;
    private String nombre;
    private int recurso;

    Hilo(Monitor monitor, String nombre, int recurso) {
        this.monitor = monitor;
        this.nombre = nombre;
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            if (recurso == 1) {
                monitor.requiereR1(nombre);
                // Simula el uso de R1
                Thread.sleep(1000);
                monitor.liberaR1(nombre);
            } else if (recurso == 2) {
                monitor.requiereR2_R3(nombre);
                // Simula el uso de R2 y R3
                Thread.sleep(1500);
                monitor.liberaR2_R3(nombre);
            } else if (recurso == 3) {
                monitor.requiereR1_R2_R3(nombre);
                // Simula el uso de R1, R2 y R3
                Thread.sleep(2000);
                monitor.liberaR1_R2_R3(nombre);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Ejercicio2 {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Thread t1 = new Thread(new Hilo(monitor, "T1", 1));
        Thread t2 = new Thread(new Hilo(monitor, "T2", 2));
        Thread t3 = new Thread(new Hilo(monitor, "T3", 3));

        t1.start();
        t2.start();
        t3.start();
    }
}
