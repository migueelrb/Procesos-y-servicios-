package ProductorConsumidor;

import java.util.Random;

public class ProductorMensajes extends Thread { ///////////////////////////// CLASE ProductorMensajes
        Monitor objetoMonitor;
        public ProductorMensajes(Monitor parametroMonitor) {
            objetoMonitor = parametroMonitor;
        }
        @Override
        public void run() {
            int mensajeQueSePoneEnElBuffer;
            do {
                mensajeQueSePoneEnElBuffer = (new Random().nextInt(140)); // Elije un numero al azar
                System.out.println("++ Puesto en Monitor el mensaje " + mensajeQueSePoneEnElBuffer + "hay" + objetoMonitor.buffer.size());
                objetoMonitor.poner(mensajeQueSePoneEnElBuffer);
            } while (mensajeQueSePoneEnElBuffer != 0);
            System.out.println("FIN DE ENTRADAS DE MENSAJERIA");
        }

}
