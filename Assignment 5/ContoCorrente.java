import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContoCorrente {
    private String currentAccountName;
    private CopyOnWriteArrayList<Movimento> MovementsList;

    public ContoCorrente(String name){
        this.currentAccountName=name;
        this.MovementsList =new CopyOnWriteArrayList<Movimento>();
        int rand=(int)(Math.random()*30)+1;
        for(int i=0;i<rand;i++)
            MovementsList.add(new Movimento());
    }

    public ContoCorrente(){
        this.MovementsList =new CopyOnWriteArrayList<Movimento>();;
    }

    // restituisce un JSONObject di questa classe
    public JSONObject toJson() {
        JSONObject json=new JSONObject();
        JSONArray movimenti=new JSONArray();
        for(int i=0;i<MovementsList.size();i++)
            movimenti.add(MovementsList.get(i).toJson());
        json.put("Nome Correntista",currentAccountName);
        json.put("Lista Movimenti",movimenti);
        return json;
    }

    // setta i valori privati della classe secondo il JSONObject
    public void toJava(JSONObject jsonObject) throws IOException, ParseException {
        JSONParser parser=new JSONParser();
        JSONObject json=(JSONObject) parser.parse(String.valueOf(jsonObject));
        JSONArray listamovimenti= (JSONArray) json.get("Lista Movimenti");
        CopyOnWriteArrayList<Movimento> movimenti=new CopyOnWriteArrayList();
        for (int i=0;i<listamovimenti.size();i++) {
            Movimento movimento=new Movimento();
            movimento.toJava((JSONObject) listamovimenti.get(i));
            movimenti.add(movimento);
        }
        currentAccountName=json.get("Nome Correntista").toString();
        MovementsList=movimenti;
    }

    public void print(){
        System.out.printf("Conto Corrente-->  Nome Correntista: %s  ",currentAccountName);
        for (int i=0;i<MovementsList.size();i++)
            MovementsList.get(i).print();
    }

}
