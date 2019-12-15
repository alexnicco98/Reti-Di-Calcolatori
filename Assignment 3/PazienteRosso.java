import java.util.concurrent.TimeUnit;

public class PazienteRosso extends  Thread{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private String name;          // nome del paziente
    private int visite;           // numero di visite che deve fare
    private String codice;        // codice del paziente
    private ProntoSoccorso Ps;

    public PazienteRosso(String nome, ProntoSoccorso newProntoSoccorso) {
        this.name = nome;
        this.codice = "rosso";
        this.Ps = newProntoSoccorso;
        Thread.currentThread().setPriority(MAX_PRIORITY);
        this.visite = (int) (Math.random() * 2) + 1;
    }

    public void run(){
        int i;
        while (visite>0){
            Ps.lock.lock();
            try {
                try {
                    System.out.println(ANSI_RED + "                                 "+
                            "Il paziente "+name+" è pronto"+ANSI_RESET);

                    // se qualcuno sta lavorando aumento il n° della coda Rossi
                    if(Ps.nobodyWork()==0)
                        Ps.addRosso();

                    while(Ps.nobodyWork()==0)
                        Ps.Empty.await();

                    Ps.setMedici(false);
                    if(Ps.RossiInFila()!=0)
                        Ps.subRosso();
                    System.out.println(ANSI_RED+"Il paziente " + name +
                            " ha iniziato la visita "+ANSI_RESET);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }finally {
                    Ps.lock.unlock();
            }

            try {
                System.out.println(ANSI_RED+"                Il paziente " + name +
                        " sta effettuando la visita "+ANSI_RESET);
                int k = (int)(Math.random() * 300) + 1;
                sleep(k);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            Ps.lock.lock();
            try{
                System.out.println(ANSI_RED+"Il paziente " + name +
                        " ha terminato la visita "+ANSI_RESET);
                Ps.setMedici(true);
                Ps.Empty.signalAll();
            }finally {
                Ps.lock.unlock();
            }

            this.visite--;
            try{
                TimeUnit.MILLISECONDS.sleep(1500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
