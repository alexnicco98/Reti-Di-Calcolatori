import java.lang.Math.*;
public class PiGreco extends Thread {
    private double precision;

    // inizzializzo il grado di accuratezza
    public PiGreco(double newPrecision){
        this.precision = newPrecision;
    }

    // ciclo per il calcolo di Pigreco
    public void run() {
        int denominatore = 1;
        int k = 1;
        double result = 0;
        while(Math.abs(Math.PI - result) > precision && !Thread.currentThread().isInterrupted()){
            result +=k*4.0/denominatore;
            k=k*(-1);
            denominatore +=2;

        }
        System.out.println(Thread.currentThread().getName() + ", Valore calcolato: " + result);
    }
} 