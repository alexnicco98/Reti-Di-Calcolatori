import java.io.IOException;

public class MainClass {
    // main class per i client
    public static void main(String []args) throws IOException, InterruptedException,IllegalArgumentException {
        // (String) prendo 5 stringhe, quindi 5 client manderanno il messaggio al server
        if(args.length!=5)throw new IllegalArgumentException("Numero di messaggi non valido");
        char let='A';
        String name, message;
        int numLet=(int)let;
        for(int i=0;i<5;i++){
            message=args[i];
            name=" Client-"+(char)(numLet+i);
            EchoClient client=new EchoClient(message+name);
            client.start();
        }

    }
}
