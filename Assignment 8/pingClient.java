import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class pingClient {
    private String hostName;
    private int port;
    private DatagramSocket clientSocket;
    private int lostPacket=0;
    private int min=1000;
    private double avg;
    private int max=0;
    private long start,end;

    public pingClient(String name, int p)throws IOException {
        this.hostName=name;
        this.port=p;
        clientSocket=new DatagramSocket();
        clientSocket.setSoTimeout(2000);
    }

    public void process()throws IOException {

        InetAddress address=InetAddress.getByName(hostName);
        byte []sendData=new byte[256];
        byte []receiveData=new byte[256];

        for(int i=0;i<10;i++){

            // invio 10 messaggi al server
            start=System.currentTimeMillis();
            String message="PING "+i+" "+start;
            System.out.print(message+" RTT: ");
            sendData=message.getBytes();
            DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,address,port);
            clientSocket.send(sendPacket);

            // aspetto l'eco del messaggio oppure il timeout
            DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
            try{
                clientSocket.receive(receivePacket);
            }
            // se il pacchetto non viene ricevuto
            catch (SocketTimeoutException e){
                lostPacket++;
                System.out.println("*");
                continue;
            }
            end=System.currentTimeMillis();

            // se il pacchetto viene ricevuto
            if(receivePacket.getLength()!=0){
                end=end-start;
                System.out.println(end+" ms");
                avg+=end;
                if(end<min)
                    min=(int) end;
                else if(end>max)
                    max=(int) end;
            }
        }

        // statistiche finali
        avg= avg/(10-lostPacket);

        // stampo le statistiche del PING
        System.out.println("----PING Statistics-----");
        System.out.println("Transfer packet: "+10+", received packet: "
                +(10-lostPacket)+", "+10*lostPacket+"% lost packet");
        NumberFormat nf=new DecimalFormat("0.00");
        System.out.println("RTT (ms) min/avg/max : "+min+" / "+nf.format(avg)+" / "+max);

        clientSocket.close();
    }

}
