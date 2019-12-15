import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MainClass{
    static final int PORT = 8080;

    public static void main(String[] args) {
        Socket clientSocket=null;
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            // rimango in attesa di clienti
            while (true) {
                clientSocket=serverConnect.accept();
                serverHTTP myServer = new serverHTTP(clientSocket);

                System.out.println("Connecton opened (" + new Date() + ")");

                // creo un thread dedicato alla gestione della connessione con il cliente
                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

}