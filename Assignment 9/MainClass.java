import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClass {
    private static int port=8080;

    // main del client
    public static void main(String []args)throws IllegalArgumentException{
        // (int) porta, se si vuole cambiare la porta di default
        if(args.length>1)throw new IllegalArgumentException("Numero di argomenti non corretto");
        if(args.length==1)
            port=Integer.parseInt(args[0]);

        CongressInterface serverObject;
        Remote remoteObject;

        try{
            Registry r=LocateRegistry.getRegistry(port);
            remoteObject=r.lookup("Server");
            serverObject=(CongressInterface)remoteObject;
            String prog;

            int count=1,i;
            String name="Speaker-";

            // resistrazioni in data specifica
            for(i=0;i<3;i++){
                for(int j=0;j<30;j++){
                    String speaker=name+count;
                    count++;
                    int sessionNum=(int)(Math.random()*11)+1;
                    int intNum=(int)(Math.random()*4)+1;
                    String res=serverObject.toSignUp(speaker,(i+1),sessionNum,intNum);
                    System.out.println(res);
                }
            }

            // registrazioni al primo slot libero
            for (i=0;i<20;i++){
                String speaker=name+count;
                count++;
                String res=serverObject.toSignUpFirstFree(speaker);
                System.out.println(res);
            }

            count=1;

            // rimozioni
            for(i=0;i<20;i++){
                String speaker=name+count;
                count++;
                int dayNum=(int)(Math.random()*2)+1;
                int sessionNum=(int)(Math.random()*11)+1;
                int intNum=(int)(Math.random()*4)+1;
                String res=serverObject.remove(speaker,dayNum,sessionNum,intNum);
                System.out.println(res);
            }

            prog=serverObject.getPrograms();
            System.out.println(prog);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
