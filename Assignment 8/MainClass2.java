import java.io.IOException;

public class MainClass2 {
    private static int port;
    private static long seed=-1;

    // main del pingServer
    public static void main(String []args)throws IllegalArgumentException, IOException {
        // (int) numero di porta
        // (long) OPZIONALE valore per la generazione di latenze e perdita di pacchetti

        int argWrong=100;
        try {
            port = Integer.parseInt(args[0]);
            if(port<1025 || port>65535)throw new IllegalArgumentException("Porta non valida");
        }catch (Exception e){
            argWrong=1;
            System.out.println(e.getMessage());
        }
        if (args.length == 2)
            try {
                seed = Long.parseLong(args[1]);
                if(seed<0 || seed>100)throw new IllegalArgumentException(
                        "Il valore di seed deve essere compreso tra 0 e 100");
            }catch (Exception e){
                argWrong=2;
                System.out.println(e.getMessage());
            }
        else if(args.length>=3)
            throw new IllegalArgumentException("Numero di argomenti non valido");

        if(argWrong!=100)
            System.out.println("ERR -arg "+argWrong);
        else{
            if(seed==-1) {
                pingServer server = new pingServer(port);
                server.process();
            }else{
                pingServer server = new pingServer(port,seed);
                server.process();
            }
        }
    }
}
