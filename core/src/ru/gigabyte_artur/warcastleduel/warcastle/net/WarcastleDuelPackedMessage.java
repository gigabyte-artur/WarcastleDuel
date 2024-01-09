package ru.gigabyte_artur.warcastleduel.warcastle.net;

public class WarcastleDuelPackedMessage
{
    private String message;             // Текст сообщения.
    private String ProtocolVersion;     // Версия протокола.
    private long TimeSend = 0;          // Время отправки сообщения (в миллисекундах).

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
}
