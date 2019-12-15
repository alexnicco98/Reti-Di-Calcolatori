import java.io.*;

public class Consumatore extends Thread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    private String name;
    private Gestore gestore;
    private String[] insideDirectoty;
    private File in;
    private final String veleno;

    public Consumatore(String nome,Gestore G,String Veleno){
        this.name=nome;
        this.veleno=Veleno;
        this.gestore=G;
    }

    public void run(){
        try {
            while (true) {
                gestore.lock.lock();
                try {
                    try {
                        // aspetto che nella cosa ci sia almeno un elemento
                        while (gestore.size() == 0)
                            gestore.NotEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String directory = gestore.remove();
                    process(directory);

                    // se il produttore mi ha mandato questo, vuol dire che ho finito
                    if (directory == veleno)
                        break;
                } finally {
                    gestore.lock.unlock();
                }
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private void process(String s)throws InterruptedException{
        in = new File(s);
        insideDirectoty = in.list();

        // controllo che "in" sia una directory
        if(insideDirectoty!=null){

            // controllo se la directory è vuota oppure no
            if(insideDirectoty.length==0)
                System.out.println("La directory "+in.getName()+" è vuota");
            else {
                System.out.println("Il contenuto della directory " + in.getName() + " è:");
                for (int i = 0; i < insideDirectoty.length; i++)
                    System.out.println(ANSI_CYAN + "            " + insideDirectoty[i] + ANSI_RESET);
            }
        }
    }


}
