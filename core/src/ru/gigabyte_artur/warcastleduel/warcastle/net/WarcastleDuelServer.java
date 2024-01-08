package ru.gigabyte_artur.warcastleduel.warcastle.net;

import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class WarcastleDuelServer extends Listener
{

    static Server server;

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

    public WarcastleDuelServer(int tcpPort_in, int udtPort_in)
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
        server.getKryo().register(WarcastleDuelPackedMessage.class);
        //Регистрируем порт
        server.bind(tcpPort, udpPort);
        //Запускаем сервер
        server.start();
//        server.addListener(new WarcastleDuelServer());
        server.addListener(this);
    }

    //Используется когда клиент подключается к серверу
    public void connected(Connection c)
    {
        System.out.println("Connetced to server: "+c.getRemoteAddressTCP().getHostString());
        //Создаем сообщения пакета.
        WarcastleDuelPackedMessage packetMessage = new WarcastleDuelPackedMessage();
        //Пишем текст который будем отправлять клиенту.
        packetMessage.message = "Now time: "+new Date().getHours()+": "+new Date().getMinutes();
        //Отправляем текст
        c.sendTCP(packetMessage); // Так же можно отправить через UDP c.sendUDP(packetMessage);
    }

    //Используется когда клиент отправляет пакет серверу
    public void received(Connection c, Object p)
    {
        //Проверяем какой отправляется пакет
        if(p instanceof WarcastleDuelPackedMessage)
        {
            //Если мы получили PacketMessage .
            WarcastleDuelPackedMessage packet = (WarcastleDuelPackedMessage) p;
            System.out.println("Answer from client: "+packet.message);
        }
    }

    //Используется когда клиент покидает сервер.
    public void disconnected(Connection c)
    {
        System.out.println("Clien leaves server");
    }
}

