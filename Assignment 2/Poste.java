import java.util.concurrent.*;

public class Poste{
    private ThreadPoolExecutor ufficio;
    private ArrayBlockingQueue q;

    // inizzializzo il Thread pool in modo che ha 4 sportelli (thread)
    public Poste(int k){
        q= new ArrayBlockingQueue(k);
        ufficio = new ThreadPoolExecutor(4,4,60L,TimeUnit.MILLISECONDS,q);
        System.out.println("Poste aperte");
    }

    // il cliente viene messo nella 2° fila, quando tocca a lui
    // e si è liberato uno sportello esegue la sua commissione
    public  void executeTask(Cliente task){
            ufficio.execute(task);
    }

    // chiude le poste quando non ci sono più clienti negli sportelli
    public void endUfficio(){
        ufficio.shutdown();
    }

    // controlla se ci sono clienti negli sportelli
    public boolean isTerminated(){
        return ufficio.isTerminated();
    }

    //restituisce il numero di persone nella seconda fila
    public int getSize(){
        return q.size();
    }

}
