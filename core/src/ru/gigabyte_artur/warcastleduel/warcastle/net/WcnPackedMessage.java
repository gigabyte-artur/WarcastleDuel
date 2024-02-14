package ru.gigabyte_artur.warcastleduel.warcastle.net;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.io.StringReader;

public class WcnPackedMessage
{
    private String message;             // Текст сообщения.
    private String ProtocolVersion;     // Версия протокола.
    private long TimeSend = 0;          // Время отправки сообщения (в миллисекундах).
    private String GameGUID = "";       // GUID игры.

    public String getGameGUID()
    {
        return GameGUID;
    }

    public void setGameGUID(String gameGUID)
    {
        GameGUID = gameGUID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getProtocolVersion()
    {
        return ProtocolVersion;
    }

    public void setProtocolVersion(String protocolVersion)
    {
        ProtocolVersion = protocolVersion;
    }

    public long getTimeSend()
    {
        return TimeSend;
    }

    public void setTimeSend(long timeSend)
    {
        TimeSend = timeSend;
    }

    /** Получаает текст внутри тега XML Message_in по его пути XmlPath_in.*/
    public static String ExtractTextByXmlPath(String Message_in, String XmlPath_in)
    {
        String rez= "";
        try
        {
            // Создаем объект SAXBuilder.
            SAXBuilder builder = new SAXBuilder();
            // Создаем документ из строки.
            Document document = builder.build(new StringReader(Message_in));
            // Получаем корневой элемент.
            Element LastElement = document.getRootElement();
            // Получаем дочерние элементы.
            for(String Curr_path: XmlPath_in.split("->"))
            {
                Element NewELement = LastElement.getChild(Curr_path);
                LastElement = NewELement;
            }
            rez = LastElement.getText();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rez;
    }

    /** Возвращает тип сообщения по XML.*/
    public static String ExtractMessageType(String Message_in)
    {
        String rez= "";
        rez = ExtractTextByXmlPath(Message_in, "Header->MessageType");
        return rez;
    }
}
