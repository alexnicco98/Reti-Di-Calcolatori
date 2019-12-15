import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.StringTokenizer;

public class TimeClient implements Runnable{
    private static String dategroup ="224.0.0.1";
    private static int port=8080;

    public TimeClient(String s,int n){
        this.dategroup=s;
        this.port=n;
    }

    public void run(){
        try{
            InetAddress address=InetAddress.getByName(dategroup);
            MulticastSocket ms=new MulticastSocket(port);
            ms.joinGroup(address);
            byte []buffer=new byte[256];
            int count=0;

            System.out.println("Waiting for multicast message...");
            while(count!=10){
                DatagramPacket datagramPacket=new DatagramPacket(buffer,buffer.length );
                ms.receive(datagramPacket);
                String s=new String(datagramPacket.getData());
                StringTokenizer parse= new StringTokenizer(s);
                String date=parse.nextToken();
                String hour=parse.nextToken();
                System.out.println("Messaggio numero: "+(count+1)+" ,data: " + date + " ,ora: " + hour);
                count++;
            }

            System.out.println("Il client ha ricevuto tutti i messaggi");
            ms.leaveGroup(address);
            ms.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
