import org.json.simple.JSONArray;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MainClass {

    public static void main(String []args)throws IOException,IllegalArgumentException {
        // (String) path del file contenente i nomi (è nella cartella dove sono salvati i file delle classi)
        // (String) path di dove si vuole salvare il file Json
        if(args.length!=2)throw new IllegalArgumentException("Numero di argomenti non valido");
        String pathNomi=args[0],pathFileJson=args[1];
        createNewFileJson(pathNomi,pathFileJson);
        Gestore gestore=new Gestore();
        Produttore producer= new Produttore(gestore,pathFileJson);
        producer.start();

        while (producer.isAlive()){}
        gestore.endThreadPool();

        while (!gestore.isTerminated()){}
        gestore.finalPrint();


        /*
        // Prova
        ContoCorrente conto1=new ContoCorrente(name[0]);
        JSONObject json1=conto1.toJson();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\ale_n\\Dropbox\\università\\" +
                "3°anno\\Reti-di-Calcolatori\\lab\\Assignment5\\src\\user.json")) {
            fileWriter.write(json1.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        conto1.print();

        //JSONParser parser =new JSONParser();
        try(FileReader fileReader=new FileReader("C:\\Users\\ale_n\\Dropbox\\università\\" +
                "3°anno\\Reti-di-Calcolatori\\lab\\Assignment5\\src\\user.json")){
            // Facendo questo tipo di codifica passo da Json a Object
            // ma io voglio da Json a Data
            //Object obj= parser.parse(fileReader);
            ContoCorrente conto2=new ContoCorrente();
            conto2.toJava(fileReader);
            conto2.print();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


    }

    public static void createNewFileJson(String pathNomi, String pathFileJson) throws IOException {
        int i,k=30;
        String names[];
        File nameList= new File(pathNomi);
        if(!nameList.isFile()) throw new IllegalArgumentException("Il path non porta ad un file");
        JSONArray listaConti =new JSONArray();
        names=getName(nameList,k);

        // trasformo la lista di conti correnti in un JSONArray
        for(i=0;i<k;i++){
            ContoCorrente conto= new ContoCorrente(names[i]);
            listaConti.add(conto.toJson());
        }

        // trasformo il JSONArray in un file .json
        String json=listaConti.toJSONString();
        byte [] inputBytes= json.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(inputBytes);
        File J=new File(pathFileJson);
        //Path p= Paths.get("C:\\Users\\ale_n\\Dropbox\\università\\3°anno\\Reti-di-Calcolatori\\lab\\Assignment5\\src\\user.json");
        FileOutputStream fos = new FileOutputStream(J.getPath());
        FileChannel fileChannel = fos.getChannel();
        fileChannel.write(buffer);
        fileChannel.close();
        fos.close();
    }

    // aggiustare con NIO
    // restituisce la lista di k nomi di correntisti
    public static String[] getName(File nameList,int k){
        String[] names=new String[k];
        try {
            // Apro il file contenente i possibili nomi dei correntisti
            // e setto l'indice in modo casuale
            RandomAccessFile file = new RandomAccessFile(nameList, "r");
            for(int i=0;i<k;i++) {
                int rand = (int) (Math.random() * file.length());
                file.seek(rand);
                file.readLine();        // butto la riga perchè potrei non essere all'inizio
                names[i] = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

}
