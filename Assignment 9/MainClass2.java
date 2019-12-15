import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClass2 {
    private static int port=8080;

    // main del server
    public static void main(String []args)throws IllegalArgumentException{
        try{
            // (int) porta, se si vuole cambiare la porta di default
            if(args.length>1)throw new IllegalArgumentException("Numero di argomenti non corretto");
            if(args.length==1)
                port=Integer.parseInt(args[0]);

            Congress cong=new Congress();

            // creazione registry
            LocateRegistry.createRegistry(port);
            Registry r=LocateRegistry.getRegistry(port);

            r.rebind("Server",cong);

            System.out.println("Server ready   ......");
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }
}
