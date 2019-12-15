import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ThreadConto extends Thread{
    private Gestore gestore;

    public ThreadConto(Gestore g){
        this.gestore=g;
    }

    // Bonifico=0, Accredito=1, Bollettino=2, F24=3, PagoBancomat=4;
    public void run(){
        JSONArray listaMovimenti= (JSONArray) gestore.remove().get("Lista Movimenti");
        for(int i=0;i<listaMovimenti.size();i++){
            JSONObject mov= (JSONObject) listaMovimenti.get(i);
            String causal = mov.get("Causale").toString();
            gestore.addCount(gestore.getindex(causal));
        }
        //System.out.println("Il thread "+Thread.currentThread().getName()+" ha controllato tutti i movimenti");
    }

}
