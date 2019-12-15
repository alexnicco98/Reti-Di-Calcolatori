import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringTokenizer;

public class HttpMirror {

    // restituisce il formato del file
    public static String FileType(final String fileName) {
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try {
            fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileType;
    }

    public static void main(String args[]) {
        try {
            int port = 8080;

            // Crea un ServerSocket in ascolto su quella porta
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server started.\nListening for connections on port: " + port + " ...\n");

            // Rimango in attesa di una richiesta di connessione
            for (;;) {
                Socket client = ss.accept();
                System.out.println("New connection");

                //buffer per leggere i dati della richiesta
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                // prima linea della richiesta del cliente
                String input = in.readLine();
                if(input==null) return;

                // Uso un StringTokenizer per "spezzare" la stringa in token
                StringTokenizer token = new StringTokenizer(input);
                // metodo HTTP che mi manda il cliente
                String method = token.nextToken();

                // stringa corrispondente alla destinazione del file da inviare
                String queryString = token.nextToken();

                // il browser mi ha mandato una richiesta di favicon
                // che però io ignoro
                if(queryString.equals("/favicon.ico")){
                    in.close();
                    client.close();
                }
                System.out.println("first line: "+input+" \nString file "+queryString+" \nmetodo: "+method+"\n");
                File file = new File(queryString);
                if(!file.exists()) throw new FileNotFoundException("Il path inserito non corrisponde ad un file");
                int fileLength = (int) file.length();
                String content= FileType(file.getName());
                if(content.equals("Undetermined"))throw new IllegalArgumentException("Il path porta ad un file" +
                        " che non è possibile inviare");

                FileInputStream fis = new FileInputStream(file);
                OutputStream outS = client.getOutputStream();
                byte[] data = new byte[fileLength];
                fis.read(data);
                fis.close();

                DataOutputStream binaryOut = new DataOutputStream(outS);
                binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
                binaryOut.writeBytes("Content-Type: "+content+"\r\n");
                binaryOut.writeBytes("Content-Length: " + data.length);
                binaryOut.writeBytes("\r\n\r\n");
                binaryOut.write(data);

                binaryOut.close();

                /** Se voglio posso stampare tutte le informazione del
                 *  client dal quale sto facendo la richiesta*/
               /* String line;
                while ((line = in.readLine()) != null) {
                    if (line.length() == 0)
                        break;
                    //System.out.println(line );
                }*/

                // Chiudo input, output e la connessione con il cliente
                outS.close();
                in.close();
                client.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

