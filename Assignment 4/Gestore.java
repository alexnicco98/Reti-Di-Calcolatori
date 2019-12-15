import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Gestore {
    private LinkedList<String> Coda;
    public ReentrantLock lock;
    public Condition NotEmpty;
    private File directory;

    public Gestore(File file){
        this.Coda=new LinkedList<>();
        this.lock=new ReentrantLock();
        this.NotEmpty=lock.newCondition();
        this.directory=file;
        Coda.add(directory.getPath());
    }

    public String getName(){
        return directory.getName();
    }

    public String directoryPath(){
        return directory.getPath();
    }

    // aggiunge l'elemento in coda
    public void add(String n){
        Coda.add(n);
    }

    //restituisce e rimuove il primo elemento
    public String remove(){
        return Coda.pollFirst();
    }

    //restituisce la dimensione di Coda
    public int size(){
        return Coda.size();
    }

}
