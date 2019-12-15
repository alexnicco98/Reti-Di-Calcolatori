import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ProntoSoccorso {
    private boolean[] medici;  // true se il medico i è libero (non sta lavorando), false altrimenti
    public ReentrantLock lock;
    public Condition Empty;
    private int[] PazientiGialli;
    private int RedVisit = 0;

    public ProntoSoccorso(){
        PazientiGialli=new int[10];
        lock= new ReentrantLock();
        Empty =lock.newCondition();
        medici=new boolean[10];
        for (int i=0;i<10;i++){
            PazientiGialli[i]=0;
            medici[i]=true;
        }
    }

    // setta lo stato del medico i
    public void setMedico(int i,boolean state){
        medici[i]=state;
    }

    // setta lo stato di tutti i medici
    public void setMedici(boolean state){
        for(int i=0;i<10;i++)
            medici[i]=state;
    }

    // restituisce 1 se nessuno lavora, 0 altrimenti
    public int nobodyWork(){
        int ok=1;
        for(int i=0;i<10;i++)
            if(medici[i]==false) ok=0;
        return ok;
    }

    // restituisce il numero di pazienti gialli in fila
    public int GialliInFila(int i){
        return PazientiGialli[i];
    }

    // restituisce il numero di pazienti rossi in coda
    public  int RossiInFila(){
        return RedVisit;
    }

    public void subGiallo(int i){
        PazientiGialli[i]--;
    }

    public void addGiallo(int i){
        PazientiGialli[i]++;
    }

    public void subRosso(){
        RedVisit--;
    }

    public void addRosso(){
        RedVisit++;
    }

    // restituisce 1 se il medico i è disponibile, 0 altrimenti
    public int MedicoDisponibile(int i){
        if(medici[i]==true) return 1;
        return 0;
    }

    // restituisce l'indice del medico libero, -1 altrimenti
    public int TrovaMedicoLibero(){
        for(int i=0;i<10;i++) {
            if (medici[i]) return i;
        }
        return -1;
    }
}
