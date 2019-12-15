import java.util.ArrayList;

public class Session {
    public String sessionName;
    public ArrayList<Intervention> IntList;

    public Session(String name){
        this.sessionName =name;
        this.IntList=new ArrayList<>(5);
        for(int i=0;i<5;i++){
            IntList.add(i,new Intervention());
        }
    }

    // se l'intervento è libero aggiunge il nome dello speaker,
    // e restituisce true, altrimenti restituisce false
    public boolean setIntervention(String name,int i){
       if(!IntList.get(i).getName().equals("default"))
            return false;
        IntList.get(i).setSpeakerName(name);
        return true;
    }

    // resituisce il primo indice dell'intervento libero per la registrazione
    // -1 altrimenti
    public int setInterventionFreeSpace(String name){
        for(int i=0;i<5;i++){
            if(IntList.get(i).getName().equals("default")){
                IntList.get(i).setSpeakerName(name);
                return i;
            }
        }
        return -1;
    }

    // restituisce "" se la rimozione dello speaker dall'intervento
    // è andata a buon fine, "default" se non ci sono speaker registrati,
    // il nome dello speaker registrato all'intervento altrimenti
    public String remove(String name, int i){
        String nome=IntList.get(i).getName();
        if(nome.equals(name)){
            IntList.get(i).removeSpeaker();
            return "";
        }else{
            if(nome.equals("default"))
                return "default";
            else
                return nome;
        }
    }

    // restituisce una stringa contenente la sessione
    public String getAll(){
        int dist=19;
        String sp="";
        String session="Session: "+ sessionName +"         ";
        for(int i=0;i<5;i++){
            sp=IntList.get(i).getName();
            if(sp.equals("default"))
                sp = "//                 ";
            else {
                int distanza=dist-sp.length();
                sp+=addSpace(distanza);
            }
            session+=sp;
        }
        session+="\n";
        return session;
    }

    // aggiunge num spazi alla stringa e la restituisce
    private String addSpace(int num){
        String space="";
        for(int i=0;i<num;i++){
            space+=" ";
        }
        return  space;
    }

}
