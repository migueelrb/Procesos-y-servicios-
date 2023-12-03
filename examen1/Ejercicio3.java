package examen1;

class Sincronizador {

    // Uso dos constantes para indicar el número máximo de patas y tableros
    private static final int MAX_NUM_PATAS = 16;
    private static final int MAX_NUM_TABLEROS = 4;

    // Uso dos variables para indicar el número de patas y tableros disponibles
    private int numPatas = 0;
    private int numTableros = 0;

    /**
     * Este método se ejecuta cuando un hilo quiere producir una pata, el método comprueba con un while si el número de
     * patas disponibles es menor que el máximo, si no es así, el hilo se queda esperando hasta que se libere alguna pata,
     * despues muestra un mensaje por pantalla y cambia el estado del recurso a no disponible. Con el finally se notifica
     * a todos los hilos que estén esperando en el recurso.
     * @throws InterruptedException
     */
    synchronized void ponPata() throws InterruptedException {
        while (numPatas >= MAX_NUM_PATAS) {
            wait();
        }
        numPatas++;
        System.out.println("Pata producida. Total: " + numPatas);
        notifyAll();
    }

    /**
     * Este método es igual que el anterior pero para los hilos que quieren producir tableros.
     * @throws InterruptedException
     */
    synchronized void ponTablero() throws InterruptedException {
        while (numTableros >= MAX_NUM_TABLEROS) {
            wait();
        }
        numTableros++;
        System.out.println("Tablero producido. Total: " + numTableros);
        notifyAll();
    }

    /**
     * Este método se ejecuta cuando un hilo quiere ensamblar una mesa, el método comprueba con un while si el número de
     * patas y tableros disponibles es menor que el máximo, si no es así, el hilo se queda esperando hasta que se libere
     * alguna pata o tablero, despues muestra un mensaje por pantalla y cambia el estado del recurso a no disponible.
     * Con el finally se notifica a todos los hilos que estén esperando en el recurso.
     * @throws InterruptedException
     */
    synchronized void cogePatasyTablero() throws InterruptedException {
        while (numPatas < 4 || numTableros < 1) {
            wait();
        }
        numPatas -= 4;
        numTableros--;
        System.out.println("Mesa ensamblada. Patas restantes: " + numPatas + ", Tableros restantes: " + numTableros);
        notifyAll();
    }
}

class FabricantePatas implements Runnable {
    private Sincronizador sincronizador;

    FabricantePatas(Sincronizador sincronizador) {
        this.sincronizador = sincronizador;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sincronizador.ponPata();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class FabricanteTableros implements Runnable {
    private Sincronizador sincronizador;

    FabricanteTableros(Sincronizador sincronizador) {
        this.sincronizador = sincronizador;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sincronizador.ponTablero();
                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Ensamblador implements Runnable {
    private Sincronizador sincronizador;

    Ensamblador(Sincronizador sincronizador) {
        this.sincronizador = sincronizador;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sincronizador.cogePatasyTablero();
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Ejercicio3 {
    public static void main(String[] args) {
        Sincronizador sincronizador = new Sincronizador();

        Thread fabricantePatasThread = new Thread(new FabricantePatas(sincronizador));
        Thread fabricanteTablerosThread = new Thread(new FabricanteTableros(sincronizador));
        Thread ensambladorThread = new Thread(new Ensamblador(sincronizador));

        fabricantePatasThread.start();
        fabricanteTablerosThread.start();
        ensambladorThread.start();
    }
}
