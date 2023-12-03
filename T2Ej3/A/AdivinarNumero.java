package T2Ej3.A;

import java.util.Random;

class NumeroOculto {
    private int numeroOculto;
    private boolean juegoTerminado;

    public NumeroOculto() {
        Random rand = new Random();
        numeroOculto = rand.nextInt(101); // Genera un número aleatorio entre 0 y 100
        juegoTerminado = false;
    }

    public synchronized int propuestaNumero(int num) {
        if (juegoTerminado) {
            return -1; // Juego terminado
        }

        if (num == numeroOculto) {
            juegoTerminado = true;
            return 1; // Número adivinado
        }

        return 0; // Número incorrecto
    }
}

class Adivinador extends Thread {
    private NumeroOculto numeroOculto;

    public Adivinador(NumeroOculto numeroOculto) {
        this.numeroOculto = numeroOculto;
    }

    @Override
    public void run() {
        while (true) {
            int num = new Random().nextInt(101); // Genera un número aleatorio para adivinar
            int resultado = numeroOculto.propuestaNumero(num);

            if (resultado == 1) {
                System.out.println("¡Hilo " + getId() + " adivinó el número!");
                break;
            } else if (resultado == -1) {
                System.out.println("¡Hilo " + getId() + " finaliza porque el juego terminó!");
                break;
            }
        }
    }
}

public class AdivinarNumero {
    public static void main(String[] args) {
        NumeroOculto numeroOculto = new NumeroOculto();

        Thread generador = new Thread(() -> {
            while (true) {
                // Simplemente espera hasta que un adivinador adivine el número
                if (numeroOculto.propuestaNumero(0) == 1) {
                    System.out.println("El número ha sido adivinado. Juego terminado.");
                    break;
                }
            }
        });

        generador.start();

        Adivinador[] adivinadores = new Adivinador[10];

        for (int i = 0; i < 10; i++) {
            adivinadores[i] = new Adivinador(numeroOculto);
            adivinadores[i].start();
        }

        try {
            generador.join();
            for (Adivinador adivinador : adivinadores) {
                adivinador.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

