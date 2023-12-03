package ProductorConsumidor;
/////////////////////////// CLASE ConsumidoreMensajes
public class ConsumidorMensajes  extends Thread {
    Monitor objetoMonitor;
    public ConsumidorMensajes(Monitor parametroMonitor) {
        objetoMonitor = parametroMonitor;
    }
    @Override
    public void run() {
        int mensajeSacadoDelBuffer ;
        do {
            mensajeSacadoDelBuffer = objetoMonitor.sacar();
            System.out.println("-- Saco del Monitor el mensaje " + mensajeSacadoDelBuffer+ " quedan " + objetoMonitor.buffer.size());
        } while (mensajeSacadoDelBuffer!=0); // seguimos si no se ha enviado un mensaje 0
        System.out.println("FIN DE SALIDAS DE MENSAJERIA");
    }
}