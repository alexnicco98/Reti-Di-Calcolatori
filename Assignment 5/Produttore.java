import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Produttore extends Thread{
    private Gestore gestore;
    private String pathFileJson;

    public Produttore(Gestore g,String path){
        this.gestore=g;
        this.pathFileJson=path;
    }

    public void run(){
        JSONParser parser =new JSONParser();
        try{
            FileChannel fileChannel=new FileInputStream(pathFileJson).getChannel();
            ByteBuffer buffer=ByteBuffer.allocateDirect(512);

            // per ogni while leggo tutto il buffer e lo inserisco in una stringa
            // che resetto all'inizio del while, e aggiungo alla stringa che conterrà l'intero file
            String json="";
            char d = 0;
            while (fileChannel.read(buffer)>0) {
                buffer.flip();
                String toJson="";
                for (int i=0;i<buffer.limit();i++){
                   d=(char) buffer.get();
                   toJson+=Character.toString(d);
                }
                json+=toJson;
                buffer.clear();
            }

            JSONArray list = (JSONArray) parser.parse(json);
            fileChannel.close();
            for(int i=0;i<list.size();i++) {
                JSONObject c =(JSONObject) list.get(i);
                gestore.add(c);
                ContoCorrente conto= new ContoCorrente();
                conto.toJava(c);
                //conto.print();
                ThreadConto tc=new ThreadConto(gestore);
                gestore.execute(tc);
            }
            System.out.println("Il produttore ha finito di aggiungere i conti correnti");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
