import java.util.*;
public class Cliente implements Runnable {
    private String name;           // nome cliente

    // assegna il nome al cliente
    public Cliente(String t){
        this.name=t;
    }

    // simula l'operazione che il cliente deve eseguire allo sportello
    public void run(){
        Random rand = new Random();
        int t = rand.nextInt(500)+1;
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lo sportello " + Thread.currentThread().getName()
                + " ha finito di servire il " + name);
    }

}
