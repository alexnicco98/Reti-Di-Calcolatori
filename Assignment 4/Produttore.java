import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Produttore extends Thread {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private String name;
    private Gestore gestore;
    private File[] insideDirectory;
    private ConcurrentLinkedQueue<String> codaInterna;  // Coda interna  che il usa il produttore
    private final String veleno;
    private int numConsumatori;

    public Produttore(String nome,Gestore G, String Veleno,int k){
        this.numConsumatori=k;
        this.name=nome;
        this.gestore=G;
        this.veleno =Veleno;
        this.codaInterna=new ConcurrentLinkedQueue<>();
        this.codaInterna.add(G.directoryPath());
    }

    public void run(){
        try{
            String head =codaInterna.poll();
            visitDirectory(head);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            while (true){
                try {
                    System.out.println(ANSI_GREEN+name+
                            " ha finito di visitare la directory "+gestore.getName()+ANSI_RESET);
                    gestore.lock.lock();
                    try{

                        // mando un veleno per ogni consumatore in modo da terminarli tutti
                        for(int i=0;i<numConsumatori;i++) {
                            gestore.add(veleno);
                            gestore.NotEmpty.signalAll();
                        }
                    }finally {
                        gestore.lock.unlock();
                    }
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    // metodo ricorsivo per navigare nelle cartelle e
    // inserire in coda il nome di ciascuna
    private void visitDirectory(String elemento){
        int i;
        if(elemento==null) return;
        File ele=new File(elemento);
        gestore.lock.lock();
        try {

            // se il file è una cartella lo aggiungo alla coda dei consumatori(LinkedList)
            if (ele.isDirectory()) {
                insideDirectory = ele.listFiles();
                for (i = 0; i < insideDirectory.length; i++) {
                    codaInterna.add(insideDirectory[i].getPath());
                    gestore.add(insideDirectory[i].getPath());
                }
            }

            // se ho aggiunto qualcosa avviso i consumatori
            if (!codaInterna.isEmpty())
                gestore.NotEmpty.signalAll();
        }finally {
            gestore.lock.unlock();
        }
        visitDirectory(codaInterna.poll());
    }

}
