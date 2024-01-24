package ru.gigabyte_artur.warcastleduel.warcastle.net;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class WcnXmlBuilder
{
    public final static String MESSAGE_TYPE_CARD_PLAYING = "CardPlaying";       // Тип сообщения "Сыграна карта".
    public final static String MESSAGE_TYPE_CREATE_GAME = "CreateGame";         // Тип сообщения "Создать игру".

    /** Генерирует xml сыгранной карты.*/
    public static String GenerateCardPlaying(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, WarcastleDuelCard Card_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = new Element("Header");
        // -Тип сообщения.
        Element MessageTypeXML = new Element("MessageType");
        MessageTypeXML.setText(MESSAGE_TYPE_CARD_PLAYING);
        HeaderXML.addContent(MessageTypeXML);
        // -ГУИД игры.
        Element GamePlayingXML = new Element("Game");
        GamePlayingXML.setText(Game_in.getGUID());
        HeaderXML.addContent(GamePlayingXML);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        Element PlayerXML = new Element("Player");
        PlayerXML.setText(Player_in.getGUID());
        BodyXML.addContent(PlayerXML);
        // -Сыгранная карта.
        Element CardXML = new Element("Card");
        CardXML.setText(Card_in.getGUID());
        BodyXML.addContent(CardXML);
        // Добавляем элементы к корневому элементу
        rootElement.addContent(HeaderXML);
        rootElement.addContent(BodyXML);
        // Выводим XML
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        rez = xmlOutput.outputString(doc);
        return rez;
    }

    /** Генерирует xml для создания игры.*/
    public static String GenerateCreateGame(String PlayerName)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = new Element("Header");
        // -Тип сообщения.
        Element MessageTypeXML = new Element("MessageType");
        MessageTypeXML.setText(MESSAGE_TYPE_CREATE_GAME);
        HeaderXML.addContent(MessageTypeXML);
        // -ГУИД игры.
        Element GamePlayingXML = new Element("Game");
        GamePlayingXML.setText("");
        HeaderXML.addContent(GamePlayingXML);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        Element PlayerXML = new Element("PlayerName");
        PlayerXML.setText(PlayerName);
        BodyXML.addContent(PlayerXML);
        // Добавляем элементы к корневому элементу
        rootElement.addContent(HeaderXML);
        rootElement.addContent(BodyXML);
        // Выводим XML
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        rez = xmlOutput.outputString(doc);
        return rez;
    }
}
