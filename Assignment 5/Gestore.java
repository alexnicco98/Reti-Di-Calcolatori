import org.json.simple.JSONObject;
import java.util.concurrent.*;

public class Gestore {
    private int[] numGlobale;
    private ThreadPoolExecutor executor;
    private ConcurrentLinkedQueue<JSONObject> listaConti;
    private LinkedBlockingQueue q;

    public Gestore() {
        q=new LinkedBlockingQueue();
        executor = new ThreadPoolExecutor(20,20,50, TimeUnit.MILLISECONDS,q);
        listaConti=new ConcurrentLinkedQueue<>();
        numGlobale=new int[5];
    }

    // restituisce l'indice della Causale,-1 in caso di errore
    public int getindex(String causal){
        switch (causal){
            case "Bonifico": return 0;
            case "Accredito": return 1;
            case "Bollettino": return 2;
            case "F24": return 3;
            case "PagoBancomat": return 4;
        }
        return -1;
    }

    // restituisce la stringa della causale corrispondende a index
    private String getCausal(int index){
        switch (index){
            case 0: return "Bonifico";
            case 1: return "Accredito";
            case 2: return "Bollettino";
            case 3: return "F24";
            case 4: return "PagoBancomat";
        }
        return null;
    }

    // aggiorna il numero di movimenti che hanno quella causale
    public synchronized void addCount(int index){
        numGlobale[index]++;
    }

    // aggiunge un conto corrente alla lista
    public synchronized void add(JSONObject c){
        listaConti.add(c);
    }

    // rimuove un conto corrente dalla lista
    public synchronized JSONObject remove(){
        return listaConti.poll();
    }

    // manda in esecuzione il thread se c'è posto, altrimenti va in coda
    public void execute(ThreadConto tc){
        executor.execute(tc);
    }

    // stampa per ogni causale quanti movimenti hanno quella causale
    public void finalPrint(){
        for(int i=0;i<5;i++)
            System.out.println("Numero di movimenti che hanno "+
                    getCausal(i)+" sono:  "+numGlobale[i]);
    }

    // chiude il thread pool quando non ci sono più thread in esecuzione
    public void endThreadPool(){
        executor.shutdown();
    }

    // controlla se ci sono thread ancora attivi nel thread pool
    public boolean isTerminated(){
        return executor.isTerminated();
    }
}
