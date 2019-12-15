import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Congress extends UnicastRemoteObject implements CongressInterface {
    private ArrayList<CongressDay> dayList;

    public Congress()throws RemoteException {
        this.dayList=new ArrayList<>(3);
        for(int i=0;i<3;i++)
            dayList.add(i,new CongressDay("Congress day-"+(i+1)));

    }

    // restituisce true se speakerName si è registrato all' intervento
    // indicato, false altrimenti
    public synchronized String toSignUp(String speakerName, int dayNum,
                                      int sessionNum, int intNum)throws RemoteException,IllegalArgumentException{
        if(speakerName.length()>18)throw new IllegalArgumentException("Nome troppo lungo");
        if(dayNum<1 || dayNum>3)throw new IllegalArgumentException("Giorno non valido");
        if(sessionNum<1 || sessionNum>12)throw new IllegalArgumentException("Numero sessione non valido");
        if(intNum<1 || intNum>5)throw new IllegalArgumentException("Numero intervento non valido");
        boolean ok= dayList.get(dayNum-1).setIntervention(speakerName,sessionNum-1,intNum-1);
        String res;
        if(ok){
            res="Aggiornamento:------>   "+speakerName+" aggiunto correttamente il" +
            " (giorno/sessione/intervento): "+dayNum+"/"+sessionNum+"/"+intNum;
            System.out.println(res);
            return res;
        }else {
            res="Aggiornamento NON RIUSCITO:------>   "+speakerName+" non registrato" +
                    " (giorno/sessione/intervento): "+dayNum+"/"+sessionNum+"/"+intNum+
                    " ,posto già occupato";
            System.out.println(res);
            return res;
        }
    }

    // restituisce true se speakerName si è registrato all' intervento
    // indicato, false altrimenti
    public synchronized String toSignUpFirstFree(String speakerName)throws RemoteException,IllegalArgumentException{
        if(speakerName.length()>18)throw new IllegalArgumentException("Nome troppo lungo");
        String date="",res;
        for(int i=0;i<3;i++){
            date=dayList.get(i).setInterventionFreeSpace(speakerName);
            StringTokenizer parse=new StringTokenizer(date);
            int sessionNum=Integer.parseInt(parse.nextToken());
            if(sessionNum!=-1){
                sessionNum+=1;
                int intNum=Integer.parseInt(parse.nextToken())+1;
                res="Aggiornamento:------>   "+speakerName+" aggiunto correttamente il" +
                        " (giorno/sessione/intervento): "+(i+1)+"/"+sessionNum+"/"+intNum;
                System.out.println(res);
                return res;
            }
        }
        res="Aggiornamento NON RIUSCITO:------>   "+speakerName+
                " non registrato, tutti i posti sono già occupati";
        System.out.println(res);
        return res;
    }

    // restituisce true se la rimozione è andata a buon fine,
    // false altrimenti
    public synchronized String remove(String speakerName, int dayNum, int sessionNum, int intNum)throws RemoteException,IllegalArgumentException{
        if(speakerName.length()>18)throw new IllegalArgumentException("Nome troppo lungo");
        if(dayNum<1 || dayNum>3)throw new IllegalArgumentException("Giorno non valido");
        if(sessionNum<1 || sessionNum>12)throw new IllegalArgumentException("Numero sessione non valido");
        if(intNum<1 || intNum>5)throw new IllegalArgumentException("Numero intervento non valido");
        String nome=dayList.get(dayNum-1).remove(speakerName,sessionNum-1,intNum-1);
        String res;
        if(nome.equals("")){
            res="Aggiornamento:------>   "+speakerName+" rimosso correttamente il" +
                    " (giorno/sessione/intervento): "+dayNum+"/"+sessionNum+"/"+intNum;
            System.out.println(res);
            return res;
        }
        else {
            if (nome.equals("default")) {
                res = "Aggiornamento NON RIUSCITO:------>   " + speakerName + " non rimosso" +
                        " (giorno/sessione/intervento): " + dayNum + "/" + sessionNum + "/" + intNum +
                        " ,intervento libero, nessuno speaker registrato";
                System.out.println(res);
                return res;
            }
            else {
                res="Aggiornamento NON RIUSCITO:------>   " + speakerName + " non rimosso" +
                        " (giorno/sessione/intervento): " + dayNum + "/" + sessionNum + "/"
                        + intNum + " ,intervento occupato dallo speaker: " + nome;
                System.out.println(res);
                return res;
            }
        }
    }

    // restituisce la stringa di tutti i giorni del congresso
    public String getPrograms(){
        String all="";
        for(int i=0;i<3;i++)
            all+=dayList.get(i).getAll()+"\n\n\n";
        return all;
    }

    // restituisce la stringa del giorno day del congresso
    public String getProgramDay(int day)throws RemoteException,IllegalArgumentException{
        if(day<1 || day>3)throw new IllegalArgumentException("Giorno non valido");
        String all="";
        all+=dayList.get(day-1).getAll();
        return all+"\n\n\n";
    }

}
