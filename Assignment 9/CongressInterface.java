import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CongressInterface extends Remote {

    // registarsi ad un intervento, indicando giorno, sessione e intervento
    public String toSignUp(String speakerName, int dayNum, int sessionNum, int intNum)throws RemoteException,IllegalArgumentException;

    // registrarsi nel primo slot libero
    public String toSignUpFirstFree(String speakerName)throws RemoteException,IllegalArgumentException;

    // rimuovere speaker da una sessione, indicando giorno, sessione e intervento
    public String remove(String speakerName, int dayNum, int sessionNum, int intNum)throws RemoteException,IllegalArgumentException;

    // visionare tutte le giornate del congresso
    public String getPrograms()throws RemoteException;

    // visionare la giornata day del congresso
    public String getProgramDay(int day)throws RemoteException,IllegalArgumentException;
}
