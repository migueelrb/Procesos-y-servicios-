package ProductorConsumidor;

public class Inicio { ///////////////////////////// CLASE INICIO
        public static void main(String args[]) {
            Monitor miMonitor = new Monitor();
            ConsumidorMensajes conMen = new ConsumidorMensajes(miMonitor);
            ProductorMensajes proMen = new ProductorMensajes(miMonitor);
            conMen.start();
            proMen.start();
        }

}
