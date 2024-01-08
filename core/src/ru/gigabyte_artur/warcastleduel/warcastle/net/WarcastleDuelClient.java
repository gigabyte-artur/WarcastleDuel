package ru.gigabyte_artur.warcastleduel.warcastle.net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class WarcastleDuelClient extends Listener
{
    static Client client;
    String ip = "localhost";    //IP сервера для подключения
    int tcpPort, udpPort;    //Порт к которому мы будем подключатся
    static boolean messageReceived = false;

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
        //Клиент начинает подключатся к серверу

        //Клиент подключается к серверу
        client.connect(5000, ip, tcpPort, udpPort);
        //client.addListener(new WarcastleDuelClient(this.getIp(), this.getTcpPort(), this.getUdpPort()));
        client.addListener(this);
        WarcastleDuelPackedMessage packet = new WarcastleDuelPackedMessage();
        packet.message = "My Message";
        client.sendTCP(packet);
        System.out.println("You have connected to server! Client waiting for package...n");
        while(!messageReceived){
            Thread.sleep(1000);
        }
        System.out.println("Client leaves server");
    }

    public void received(Connection c, Object p)
    {
        //Проверяем какой отправляется пакет
        if(p instanceof WarcastleDuelPackedMessage)
        {
            //Если мы получили PacketMessage .
            WarcastleDuelPackedMessage packet = (WarcastleDuelPackedMessage) p;
            System.out.println("Answer from server: "+packet.message);

            //Мы получили сообщение
            messageReceived = true;
        }
    }
}

