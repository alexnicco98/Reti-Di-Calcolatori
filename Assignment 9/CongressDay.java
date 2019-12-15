import java.rmi.RemoteException;
import java.util.ArrayList;

public class CongressDay {
    private String dayName;
    private ArrayList<Session> SessionList;

    public CongressDay(String name)throws RemoteException {
        this.dayName =name;
        this.SessionList=new ArrayList<>(12);
        for(int i=0;i<12;i++)
            SessionList.add(i,new Session("S"+(i+1)));
    }

    // se l'intervento è libero aggiunge il nome dello speaker,
    // e restituisce true, altrimenti restituisce false
    public boolean setIntervention(String speakerName, int sessionNum, int intNum){
        return SessionList.get(sessionNum).setIntervention(speakerName,intNum);
    }

    // restituisce numero sessione e numero intervento se c'è spazio libero,
    // -1 altrimenti
    public String setInterventionFreeSpace(String name){
        String date="";
        for(int i=0;i<12;i++){
            int index=SessionList.get(i).setInterventionFreeSpace(name);
            if(index!=-1){
                date+=i+" "+index;
                return date;
            }
        }
        return "-1";
    }

    // restituisce "" se la rimozione dello speaker dall'intervento
    // è andata a buon fine, "default" se non ci sono speaker registrati,
    // il nome dello speaker registrato all'intervento altrimenti
    public String remove(String speakerName, int sessionNum, int intNum){
        return SessionList.get(sessionNum).remove(speakerName,intNum);
    }

    // restituisce la stringa contenente la iornata di congresso
    public String getAll(){
        String congressDay="                  "+ dayName+"\n"+"                    Intervent-1:       Intervent-2:" +
                "       Intervent-3:       Intervent-4:       Intervent-5:\n";
        for(int i=0;i<12;i++)
           congressDay+=SessionList.get(i).getAll();
        return congressDay;
    }
}
