import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeServer implements Runnable {
    private static String dategroup ="224.0.0.1";
    private static int port=8080;

    public TimeServer(String s,int n){
        this.dategroup=s;
        this.port=n;
    }

    public void run() {
        try{
            InetAddress address = InetAddress.getByName(dategroup);
            MulticastSocket ms = new MulticastSocket();
            byte[] data;

            for (;;) {
                LocalDate date = LocalDate.now();
                String date1 = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime time = LocalTime.now();
                int hour = time.getHour();
                System.out.println("Questa è la data: " + date1 + " ,questa è l'ora: " + hour);

                date1 = date1 + " " + hour;
                data = date1.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);

                ms.send(datagramPacket);
                Thread.sleep(500);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
