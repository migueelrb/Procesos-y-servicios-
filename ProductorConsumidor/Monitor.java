package ProductorConsumidor;

import java.util.ArrayList;

public class Monitor { ///////////////////////////// CLASE MONITOR
    ArrayList<Integer> buffer = new ArrayList<>();
    int tamMaximo = 5;

    // —————————————-—— METODO SACAR
    public synchronized int sacar() {
        // Se mira si el buffer esta vacio, si lo esta, espera
        while (buffer.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int sacado = buffer.get(0);
        buffer.remove(0);
        notifyAll();
        return (sacado); // 5- Se devuelve el mensaje del buffer al consumidor
    }

    // ——————————————— METODO PONER
    public synchronized void poner(int recibido) {
        // Se mira si el buffer esta lleno, si lo esta, se espera a que haya hueco
        while (buffer.size() >= tamMaximo) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.add(recibido);

        notifyAll();

    }
}

