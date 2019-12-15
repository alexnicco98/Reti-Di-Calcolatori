import java.io.File;

public class MainClass {
    public static void main(String []args){
        int k; // (int)numero di consumatori

        File dir=new File(args[0]); // (String) directory per il produttore
        if(!dir.isDirectory()) throw new IllegalArgumentException();
        Gestore gestore=new Gestore(dir);
        k= Integer.valueOf(args[1]);
        String Veleno="veleno";
        Produttore Producer=new Produttore("Producer",gestore,Veleno,k);
        Producer.start();
        for(int i=0;i<k;i++) {
            Consumatore consumatore = new Consumatore("Consumatore-" + i, gestore,Veleno);
            consumatore.start();
        }

    }
}
