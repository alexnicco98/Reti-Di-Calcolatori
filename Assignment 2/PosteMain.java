import java.util.*;

public class PosteMain {
    public static void main(String args[])throws IllegalArgumentException{
        int i,n,k;
        LinkedList <Cliente> PrimaCoda=new LinkedList<>();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Inserire lunghezza 1° coda:");
        n = scanner.nextInt();
        if(n<=0) throw new IllegalArgumentException();
        System.out.println("Inserire lunghezza 2° coda:");
        k = scanner.nextInt();
        if(k<=0 || k>=n) throw new IllegalArgumentException();
        Poste PosteUfficio = new Poste(k);

        for(i=0;i<n;i++) {
            PrimaCoda.add(new Cliente("cliente " + i));

            // il cliente i entra nelle Poste; se c'è posto
            // entra direttamente nella 2° fila, sennò aspetta
            // nella prima fila
            while(PosteUfficio.getSize()==k)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            PosteUfficio.executeTask(PrimaCoda.remove());
        }
        PosteUfficio.endUfficio();                  // Quando i clienti sono terminati
                                                    // chiudo Le poste(Il threadpool)
        while(!PosteUfficio.isTerminated()) {         // controllo se ci sono ancora clienti negli sportelli
        }
        System.out.println("Le poste sono chiuse");
    }
}
