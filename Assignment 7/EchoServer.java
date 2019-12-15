import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EchoServer {
    private static int port=8086;
    private Map<SocketChannel,byte[]> dataMap;  // struttura in cui mi salvo <client,messaggio del client>
    private Selector selector;
    private InetSocketAddress listenAdd;
    private ServerSocketChannel server;
    private String echo=" echoed by server";    // messaggio che aggiungo a quello inviato dai client
    private int count;

    public EchoServer(String address) throws IOException {
        this.count=0;
        this.listenAdd=new InetSocketAddress(address,port);
        this.dataMap=new HashMap<>();
        this.server=ServerSocketChannel.open();
        server.socket().bind(listenAdd);
        server.configureBlocking(false);
        this.selector=Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server pronto sulla porta: "+port);
    }

    public void start() throws IOException {
        for(;;) {
            // se il server ha finito di mandare i 5 messaggi modificati
            // possiamo chiudere il server
            if (count == 5) {
                close();
                break;
            }
            int ready = selector.select();

            // se non ci sono operazioni esce da questa iterazione e aspetta almeno 1 operazione
            if (ready == 0)
                continue;

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();


            // scorro le keys che sono pronte per un'attività
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();

                if (!key.isValid())
                    continue;
                if (key.isAcceptable())
                    accept(key);
                else if (key.isReadable())
                    read(key);
                else if (key.isWritable())
                    write(key);
            }
        }
    }

    // accettare la connessione del cliente
    public void accept(SelectionKey key) throws IOException{
        ServerSocketChannel socketChannel=(ServerSocketChannel) key.channel();
        SocketChannel client= socketChannel.accept();
        client.configureBlocking(false);
        ByteBuffer buffer=ByteBuffer.allocate(256);
        SelectionKey key1=client.register(selector,SelectionKey.OP_READ);
        key1.attach(buffer);
    }

    // leggere i dati dalla socket del cliente
    public void read(SelectionKey key)throws IOException{
        SocketChannel client=(SocketChannel)key.channel();
        ByteBuffer buffer=(ByteBuffer) key.attachment();
        int num=client.read(buffer);

        // non ho più dati da leggere
        if(num== -1 ){
            Socket socket=client.socket();
            SocketAddress address=socket.getRemoteSocketAddress();
            System.out.println("Connessione chiusa con il cliente: "+address);
            client.close();
            count++;
            key.cancel();
            return;
        }

        byte []data=new byte[num];
        buffer.flip();
        buffer.get(data);
        buffer.clear();
        dataMap.put(client,data);
        System.out.println("Sto leggendo: "+new String(data));
        key.interestOps(SelectionKey.OP_WRITE);
    }

    // modifico la stringa ricevuta e la mando al cliente
    public void write(SelectionKey key)throws IOException{
        SocketChannel client=(SocketChannel) key.channel();
        byte []data=dataMap.get(client);
        dataMap.remove(client);

        String newdata=new String(data)+echo;
        System.out.println("Sto scrivendo: "+newdata);
        client.write(ByteBuffer.wrap(newdata.getBytes()));
        key.interestOps(SelectionKey.OP_READ);
    }

    // chiudo il server
    public void close() throws IOException {
        selector.close();
        server.close();
        System.out.println("Server chiuso, i client sono stati serviti");
    }
}
