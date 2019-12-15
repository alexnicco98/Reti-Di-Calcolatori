import java.util.Scanner;

public class MainClass {
    public static void main(String []args){
        ProntoSoccorso Ps= new ProntoSoccorso();

        int b,g,r;
        // b,g,r (int) numero rispettivamente di clienti bianchi, gialli e rossi
        int[] parametri=new int[3];
        Scanner scanner=new Scanner(System.in);
        for(int i=0;i<3;i++) {
            parametri[i] = scanner.nextInt();
            System.out.println(parametri[i]);
        }
        b=parametri[0];
        g=parametri[1];
        r=parametri[2];
        InizializzaPazienti(Ps,b,g,r);

    }

    public static void InizializzaPazienti(ProntoSoccorso Ps,int b, int g, int r)throws IllegalArgumentException{
        if(b<0) throw new IllegalArgumentException();
        if(g<0) throw new IllegalArgumentException();
        if(r<0) throw new IllegalArgumentException();

        for(int i=0;i<r;i++){
            PazienteRosso nuovo= new PazienteRosso("Rosso-"+i,Ps);
            nuovo.start();
        }

        for(int i=0;i<g;i++){
            PazienteGiallo nuovo= new PazienteGiallo("Giallo-"+i,Ps);
            nuovo.start();
        }

        for(int i=0;i<b;i++){
            PazienteBianco nuovo = new PazienteBianco("Bianco-"+i,Ps);
            nuovo.start();
        }

    }
}
