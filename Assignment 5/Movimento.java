import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Movimento{
    public enum Causale{
        Bonifico,Accredito,Bollettino,F24,PagoBancomat;

        public static Causale getCausale(String s){
            switch (s){
                case "Bonifico": return Bonifico;
                case "Accredito": return Accredito;
                case "Bollettino": return Bollettino;
                case "F24": return F24;
                case "PagoBancomat": return PagoBancomat;
            }
            return null;
        }
    }
    private Data date;
    private Causale causal;

    public Movimento(){
        this.date=new Data();
        Causale list[]=Causale.values();
        int i=(int) (Math.random()*5);
        this.causal=list[i];
    }

    // restituisce un JSONObject di questa classe
    public JSONObject toJson(){
        JSONObject json=new JSONObject();
        JSONObject data=date.toJson();
        json.put("Data",data);
        json.put("Causale",causal.toString());
        return json;
    }

    // setta i valori privati della classe secondo il JSONObject
    public void toJava(JSONObject jsonObject) throws ParseException {
        JSONParser parser =new JSONParser();
        JSONObject json=(JSONObject) parser.parse(String.valueOf(jsonObject));
        JSONObject data1 =(JSONObject) json.get("Data");
        Data data=new Data();
        data.toJava(data1);
        date=data;
        causal=Causale.getCausale(json.get("Causale").toString());
    }

    public void print(){
        System.out.printf("Movimento-->   Causale: %s , ",causal);
        date.print();
    }

}
