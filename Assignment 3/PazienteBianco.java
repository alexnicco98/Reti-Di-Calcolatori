import java.util.concurrent.TimeUnit;

public class PazienteBianco extends Thread{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_WHITE = "\u001B[37m";
        private String name;
        private int visite;  // numero di visite che deve fare
        private String codice;
        private int NumMedico;
        private ProntoSoccorso Ps;

        public PazienteBianco(String nome, ProntoSoccorso newProntoSoccorso) {
            this.name = nome;
            this.codice = "bianco";
            this.Ps = newProntoSoccorso;
            Thread.currentThread().setPriority(MIN_PRIORITY);
            this.visite = (int) (Math.random() * 3) + 1;
        }

        public void run(){
            while(visite>0){
                Ps.lock.lock();
                try{
                    try{
                        System.out.println(ANSI_WHITE+"                                 "+
                                "Il paziente "+name+" è pronto"+ANSI_RESET);
                        // aspetto che i rossi abbiano finito
                        while (Ps.RossiInFila()!=0)
                            Ps.Empty.await();

                        while ((NumMedico=Ps.TrovaMedicoLibero())<0)
                            Ps.Empty.await();
                        Ps.setMedico(NumMedico,false);
                        System.out.println("Il paziente " + name + " ha iniziato la visita ");

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }finally {
                    Ps.lock.unlock();
                }

                try {
                    System.out.println(ANSI_WHITE+"                Il paziente " + name +
                            " sta effettuando la visita "+ANSI_RESET);
                    int k = (int) (Math.random() * 100) + 1;
                    sleep(k);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                Ps.lock.lock();
                try{
                    System.out.println(ANSI_WHITE+"Il paziente " + name +
                            " ha terminato la visita "+ANSI_RESET);
                    Ps.setMedico(NumMedico,true);
                    Ps.Empty.signalAll();
                }finally {
                    Ps.lock.unlock();
                }
                this.visite--;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
