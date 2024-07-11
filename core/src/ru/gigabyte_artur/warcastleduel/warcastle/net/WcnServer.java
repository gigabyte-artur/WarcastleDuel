package ru.gigabyte_artur.warcastleduel.warcastle.net;

import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class WcnServer extends Listener
{

    static Server server;
    private static String ProtocolVersion = "0.0.0.1";
    int udpPort, tcpPort; // Порт на котором будет работать наш сервер

    public int getUdpPort()
    {
        return udpPort;
    }

    public void setUdpPort(int udpPort)
    {
        this.udpPort = udpPort;
    }

    public int getTcpPort()
    {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort)
    {
        this.tcpPort = tcpPort;
    }

    public WcnServer(int tcpPort_in, int udtPort_in)
    {
        setTcpPort(tcpPort_in);
        setUdpPort(udtPort_in);
    }

    public void StartServer() throws Exception
    {
        System.out.println("Creating Server");
        //Создаем сервер
        server = new Server();
        //Регистрируем пакет класс
        server.getKryo().register(WcnPackedMessage.class);
        //Регистрируем порт
        server.bind(tcpPort, udpPort);
        //Запускаем сервер
        server.start();
        server.addListener(this);
    }

    //Используется когда клиент подключается к серверу
    public void connected(Connection c)
    {
        System.out.println("Connetced to server: "+c.getRemoteAddressTCP().getHostString());
//        //Создаем сообщения пакета.
//        String NewMessage;
//        NewMessage = "Now time: " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();
//        SendStringMessage(c, NewMessage);
    }

    /** Отправляет клиенту с коннектом Connection_in текстовое сообщение Message_in*/
    public void SendStringMessage(Connection Connection_in, String Message_in)
    {
        //Создаем сообщения пакета.
        WcnPackedMessage packetMessage = new WcnPackedMessage();
        //Пишем текст который будем отправлять клиенту.
        packetMessage.setMessage(Message_in);
        packetMessage.setProtocolVersion(this.ProtocolVersion);
        long timeSend = System.currentTimeMillis();
        packetMessage.setTimeSend(timeSend);
        //Отправляем текст
        Connection_in.sendTCP(packetMessage); // Так же можно отправить через UDP c.sendUDP(packetMessage);
    }

    //Используется когда клиент отправляет пакет серверу
    public void received(Connection c, Object p)
    {
        //Проверяем какой отправляется пакет
        if (p instanceof WcnPackedMessage)
        {
            //Если мы получили PacketMessage .
            WcnPackedMessage packet = (WcnPackedMessage) p;
            if (packet.getProtocolVersion().equals(this.ProtocolVersion))
            {
                System.out.println("Answer from client: " + packet.getMessage());
            }
            else
            {
                System.out.println("Protocol version not compares. Update client, please.");
            }
        }
    }

    //Используется когда клиент покидает сервер.
    public void disconnected(Connection c)
    {
        System.out.println("Clien leaves server");
    }
}

