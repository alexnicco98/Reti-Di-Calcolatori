import java.io.IOException;

public class MainClass2 {
    // main class per il server
    private static String name="localhost";
    public static void main(String []args)throws IOException {
        EchoServer server= new EchoServer(name);
        server.start();
    }
}
