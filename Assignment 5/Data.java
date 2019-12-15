import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Data  {
    private long day;
    private long month;
    private long year;


    public Data(){
        Date now= new Date(2019,10,25);  // Data massima
        Date init=new Date(2017,1,1);    // Data minima
        Date randomDate = new Date(ThreadLocalRandom.current().nextLong(init.getTime(), now.getTime()));
        this.day=randomDate.getDay();
        this.month=randomDate.getMonth();
        this.year=randomDate.getYear();
    }

    public Data(long d, long m, long y){
        this.day=d;
        this.month=m;
        this.year=y;
    }

    // restituisce un JSONObject di questa classe
    public JSONObject toJson(){
        JSONObject json=new JSONObject();
        json.put("day",day);
        json.put("month",month);
        json.put("year",year);
        return json;
    }

    // setta i valori privati della classe secondo il JSONObject
    public void toJava(JSONObject jsonObject) throws ParseException {
        JSONParser parser =new JSONParser();
        JSONObject json=(JSONObject) parser.parse(String.valueOf(jsonObject));
        day=(long)json.get("day");
        month=(long)json.get("month");
        year=(long)json.get("year");
    }

    public void print(){
        System.out.println("Data-->   day: "+day+" month: "+month+" year: "+year);
    }

}
