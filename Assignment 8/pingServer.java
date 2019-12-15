import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class pingServer {
    private int port;                 // porta su cui è attivo il server
    private long seed=0;              // se presente deve valere tra 0 e 100
    private Selector selector;
    private DatagramChannel channel;
    private InetSocketAddress address;
    private SelectionKey clientKey;
    private int numReceivedMessage=0;  // numero di messaggi ricevuti

    public class Resources{
        ByteBuffer receive;
        ByteBuffer send;
        SocketAddress sa;

        public Resources(){
            receive= ByteBuffer.allocateDirect(256);
            send= ByteBuffer.allocateDirect(256);
        }
    }

    public pingServer(int p,long s)throws IOException{
        this.port=p;
        this.seed=s;
        this.selector=Selector.open();
        this.channel=DatagramChannel.open();
        this.address=new InetSocketAddress(port);
        channel.socket().bind(address);
        channel.configureBlocking(false);
        clientKey=channel.register(selector,SelectionKey.OP_READ);
        clientKey.attach(new Resources());
        System.out.println("Server ready on port: "+port+" ......");
    }

    public pingServer(int p)throws IOException {
        this.port=p;
        this.selector=Selector.open();
        this.channel=DatagramChannel.open();
        this.address=new InetSocketAddress(port);
        channel.socket().bind(address);
        channel.configureBlocking(false);
        clientKey=channel.register(selector,SelectionKey.OP_READ);
        clientKey.attach(new Resources());
        System.out.println("Server ready on port: "+port+" ......");
    }

    public void process()throws IOException{
        for(;;){
            if(numReceivedMessage==10){
                close();
                break;
            }
            selector.select();
            Iterator selectedKeys=selector.selectedKeys().iterator();

            while(selectedKeys.hasNext()){
                SelectionKey key=(SelectionKey) selectedKeys.next();
                selectedKeys.remove();

                if(!key.isValid()){
                    continue;
                }
                try {
                    if (key.isReadable())
                        read(key);
                    else if (key.isWritable())
                        write(key);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // leggo il messaggio ricevuto dal client
    private void read(SelectionKey key)throws IOException,InterruptedException{
        DatagramChannel clientChannel=(DatagramChannel) key.channel();
        Resources res=(Resources) key.attachment();
        res.sa=clientChannel.receive(res.receive);
        res.receive.flip();
        int limit=res.receive.limit();
        byte []data = new byte[limit];
        res.receive.get(data);
        String dati=new String(data);

        // aumento il numero di messaggi ricevuti dal client
        numReceivedMessage++;

        int prob=25;
        // probabilità di NON perdere il pacchetto
        // di default è 75%, mentre se è presente
        // il valore seed è 100-seed
        if(seed!=-1){
            prob=(int)seed;
        }

        // Simulazione perdita del pacchetto
        int lost=(int) (Math.random()*100);
        if(lost>prob){
            int delay=(int)(Math.random()*101)+(100-(int)seed);
            Thread.sleep(delay);
            System.out.println(res.sa+", ricevuto: "
                    +dati+", PING ritardato di "+delay+" ms");
            res.send=res.receive;
            res.receive.clear();
            key.interestOps(SelectionKey.OP_WRITE);
        }
        else{
            res.receive.clear();
            System.out.println(res.sa+", ricevuto: "
                    +dati+", PING non inviato");
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    // invio la risposta al client
    private void write(SelectionKey key)throws IOException{
        DatagramChannel clientChannel=(DatagramChannel) key.channel();
        Resources res=(Resources) key.attachment();
        clientChannel.send(res.send, res.sa);
        res.send.clear();
        key.interestOps(SelectionKey.OP_READ);
    }

    // chiudo la connessione
    private void close()throws IOException{
        selector.close();
        channel.close();
    }

}
