public class MainClass2 {
    private static String dategroup ="224.0.0.1";
    private static int port=8080;

    // main del server
    public static void main(String args[]) throws IllegalArgumentException{
        //(String) indirizzo ip di dategroup
        //(int) (opzionale) se si vuole cambiare la porta di default 8080
        if (args.length > 2) throw new IllegalArgumentException("Numero di argomenti non valido");
        if (args.length == 1)
            dategroup = args[0];
        else if(args.length==2){
            dategroup = args[0];
            port=Integer.parseInt(args[1]);
        }

        TimeServer server=new TimeServer(dategroup,port);
        server.run();
    }
}
