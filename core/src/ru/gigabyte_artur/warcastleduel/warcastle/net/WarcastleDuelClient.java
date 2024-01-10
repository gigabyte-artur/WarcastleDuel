package ru.gigabyte_artur.warcastleduel.warcastle.net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;

public class WarcastleDuelClient extends Listener
{
    static Client client;
    String ip = "localhost";    //IP сервера для подключения
    int tcpPort, udpPort;    //Порт к которому мы будем подключатся
    static boolean messageReceived = false;
    private WarcastleDuelGame GamePlaying;
    private static String ProtocolVersion = "0.0.0.1";

    public WarcastleDuelGame getGamePlaying()
    {
        return GamePlaying;
    }

    public void setGamePlaying(WarcastleDuelGame gamePlaying)
    {
        GamePlaying = gamePlaying;
    }

    public WarcastleDuelClient(String ip_in, int tcpPort_in, int udpPort_in)
    {
        this.setIp(ip_in);
        this.setTcpPort(udpPort_in);
        this.setUdpPort(tcpPort_in);
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getTcpPort()
    {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort)
    {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort()
    {
        return udpPort;
    }

    public void setUdpPort(int udpPort)
    {
        this.udpPort = udpPort;
    }

    public void StartClient() throws Exception
    {
        System.out.println("Connecting to server...");
        //
        client = new Client();
        //Регистрируем пакет
        client.getKryo().register(WarcastleDuelPackedMessage.class);
        //Запускаем клиент
        client.start();
        //Клиент подключается к серверу
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(this);
        SendStringMessage("My Message");
        System.out.println("You have connected to server! Client waiting for package...n");
        while(!messageReceived){
            Thread.sleep(1000);
        }
        System.out.println("Client leaves server");
    }

    /** Отправляет на сервер сообщение Message_in.*/
    public void SendStringMessage(String Message_in)
    {
        WarcastleDuelPackedMessage packet = new WarcastleDuelPackedMessage();
        packet.setMessage(Message_in);
        packet.setProtocolVersion(this.ProtocolVersion);
        long timeSend = System.currentTimeMillis();
        packet.setTimeSend(timeSend);
        packet.setGameGUID(getGamePlaying().getGUID().toString());
        client.sendTCP(packet);
    }

    public void received(Connection c, Object p)
    {
        //Проверяем какой отправляется пакет
        if(p instanceof WarcastleDuelPackedMessage)
        {
            //Если мы получили PacketMessage .
            WarcastleDuelPackedMessage packet = (WarcastleDuelPackedMessage) p;
            if (packet.getProtocolVersion().equals(this.ProtocolVersion))
            {
                System.out.println("Answer from server: "+packet.getMessage());
                //Мы получили сообщение
                messageReceived = true;
            }
            else
            {
                System.out.println("Protocol version not compares. Update client, please.");
            }
        }
    }
}