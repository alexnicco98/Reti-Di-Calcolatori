import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoClient {
    private static String hostName="localhost";
    private static int port=8086;
    private String data;

    public EchoClient(String n){
        this.data=n;
    }

    public void start() throws IOException,InterruptedException{
        InetSocketAddress address = new InetSocketAddress(hostName,port);
        SocketChannel client = SocketChannel.open(address);
        ByteBuffer buffer=ByteBuffer.allocateDirect(256);

        // Mando il messaggio al server
        buffer.put(data.getBytes());
        buffer.flip();
        while(buffer.hasRemaining())
            client.write(buffer);
        System.out.println("Ho inviato: "+data);
        buffer.clear();
        Thread.sleep(100);

        // Leggo dal server
        int num=client.read(buffer);
        byte []data=new byte[num];
        buffer.flip();
        buffer.get(data);
        System.out.println("Ho ricevuto: "+new String(data));
        buffer.clear();
        client.close();
    }

}
