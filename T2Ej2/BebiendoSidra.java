package T2Ej2;

class BebiendoSidra {
    public static void main(String[] args) throws InterruptedException {
        MonitorSidra objetoMonitor = new MonitorSidra();
        Camarero hilocam = new Camarero(objetoMonitor);
        Cliente hilocli = new Cliente(objetoMonitor);
        hilocli.start();
        hilocam.start();
    }
}

class Camarero extends Thread {
    private MonitorSidra monitor;

    public Camarero(MonitorSidra monitor) {
        this.monitor = monitor;
    }

    public void run() {
        monitor.camareroEscancia();
    }
}

class Cliente extends Thread {
    private MonitorSidra monitor;

    public Cliente(MonitorSidra monitor) {
        this.monitor = monitor;
    }

    public void run() {
        monitor.beboSidra();
    }
}

class MonitorSidra {
    private int sidraEscanciada = 0;

    public synchronized void camareroEscancia() {
        sidraEscanciada++;
        System.out.println("Sidra escanciada...");
        notifyAll();
    }

    public synchronized void beboSidra() {
        while (sidraEscanciada == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Que rica !");
    }
}
