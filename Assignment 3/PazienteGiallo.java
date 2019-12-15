import java.util.concurrent.TimeUnit;

public class PazienteGiallo extends Thread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private String name;
    private int visite;  // numero di visite che deve fare
    private int NumMedico;
    private String codice;
    private ProntoSoccorso Ps;

    public PazienteGiallo(String nome, ProntoSoccorso newProntoSoccorso) {
        this.name = nome;
        this.codice = "giallo";
        this.Ps = newProntoSoccorso;
        Thread.currentThread().setPriority(NORM_PRIORITY);
        this.NumMedico = (int) (Math.random() * 9);    // Assegno al paziente il medico
                                                       // specifico di cui ha bisogno
        this.visite = (int) (Math.random() * 3) + 1;
    }

    public void run() {
        while (visite > 0) {
            Ps.lock.lock();
            try {
                try {
                    System.out.println(ANSI_YELLOW+"                                 "+
                            "Il paziente "+name+" è pronto, Medico richiesto: "+NumMedico+ANSI_RESET);
                    while (Ps.RossiInFila()!=0)
                        Ps.Empty.await();

                    if (Ps.MedicoDisponibile(NumMedico) == 0)
                        Ps.addGiallo(NumMedico);
                    while (Ps.MedicoDisponibile(NumMedico) == 0 )
                        Ps.Empty.await();
                    Ps.setMedico(NumMedico, false);
                    if(Ps.GialliInFila(NumMedico)!=0)
                        Ps.subGiallo(NumMedico);
                    System.out.println(ANSI_YELLOW+"Il paziente " + name +
                            " ha iniziato la visita "+ANSI_RESET);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                Ps.lock.unlock();
            }
            try {
                System.out.println(ANSI_YELLOW+"                Il paziente " + name +
                        " sta effettuando la visita "+ANSI_RESET);
                int k = (int) (Math.random() * 200) + 1;
                sleep(k);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Ps.lock.lock();
            try {
                System.out.println(ANSI_YELLOW+"Il paziente " + name +
                        " ha terminato la visita "+ANSI_RESET);
                Ps.setMedico(NumMedico,true);
                Ps.Empty.signalAll();
            }finally {
                Ps.lock.unlock();
            }
            this.visite--;
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
