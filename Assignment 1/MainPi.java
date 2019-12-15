import java.util.Scanner;
public class MainPi {

    public static void main(String[] args){
        // MainClass precision maxDelay
        // precision (double) in decimale
        // maxDelay  (int) tempo in millisecondi
        Scanner scanner = new Scanner(System.in);
        double precision = scanner.nextDouble();
        int maxDelay = scanner.nextInt();
        PiGreco pi = new PiGreco(precision);
        try{
            pi.start();
            Thread.sleep(maxDelay);

            if(pi.isAlive()){     // se dopo il maxDelay il thread sta
                                  // ancora calcolando interrompiamolo
                pi.interrupt();
                System.out.println("Interrotto");
            }
            else
                System.out.println("Differenza tra valore stimato e valore su" +
                        " Math.pi minore del grado di accuratezza");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
} 