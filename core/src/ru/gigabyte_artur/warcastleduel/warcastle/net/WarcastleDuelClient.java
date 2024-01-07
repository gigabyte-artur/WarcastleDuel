package ru.gigabyte_artur.warcastleduel.warcastle.net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class WarcastleDuelClient extends Listener
{
    static Client client;
    static String ip = "localhost";    //IP сервера для подключения
    static int tcpPort = 27960, udpPort = 27960;    //Порт к которому мы будем подключатся
    static boolean messageReceived = false;

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
        client.addListener(new WarcastleDuelClient());
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

