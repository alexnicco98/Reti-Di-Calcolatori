import java.io.IOException;
import java.net.InetAddress;

public class MainClass {
    private static String hostName;
    private static int port;

    // main del pingClient
    public static void main(String []args)throws IllegalArgumentException, IOException {
        // (String) host name
        // (int) numero di porta

        int argWrong=100;
        if (args.length != 2) throw new IllegalArgumentException("Numero di argomenti non valido");
        else {
            try {
                hostName = args[0];
                InetAddress addres=InetAddress.getByName(hostName);
            }catch (Exception e){
                argWrong=1;
                e.printStackTrace();
            }
            try {
                port = Integer.parseInt(args[1]);
                if(port<1025 || port>65535)throw new IllegalArgumentException("Porta non valida");
            }catch (Exception e){
                argWrong=2;
                System.out.println(e.getMessage());
            }
            if(argWrong!=100)
                System.out.println("ERR -arg "+argWrong);
            else {
                pingClient client = new pingClient(hostName, port);
                client.process();
            }
        }
    }
}
