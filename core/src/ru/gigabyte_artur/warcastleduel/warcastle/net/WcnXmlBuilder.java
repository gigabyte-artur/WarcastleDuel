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
    public final static String MESSAGE_TYPE_CARD_PLAYING = "CardPlaying";              // Тип сообщения "Сыграна карта".
    public final static String MESSAGE_TYPE_CREATE_GAME = "CreateGame";                // Тип сообщения "Создать игру".
    public final static String MESSAGE_TYPE_FULL_GAME = "FullGame";                    // Тип сообщения "Полная игра".
    public final static String MESSAGE_TYPE_REQUEST_FULL_GAME = "RequestFullGame";     // Тип сообщения "Запросить полную игру".

    /** Добавляет простой элемент-текст с именем ElementName и текстом Text_in
     * в родительский узел ParentElement_chng*/
    private static void AddSimpleTextElementXML(String ElementName, String Text_in, Element ParentElement_chng)
    {
        Element PlayerXML = new Element(ElementName);
        PlayerXML.setText(Text_in);
        ParentElement_chng.addContent(PlayerXML);
    }

    /** Завершает вывод XML и выводит его в виде строки.*/
    private static String EndXML(Element rootElement, Document doc, Element HeaderXML, Element BodyXML)
    {
        String rez = "";
        // Добавляем элементы к корневому элементу
        rootElement.addContent(HeaderXML);
        rootElement.addContent(BodyXML);
        // Выводим XML
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        rez = xmlOutput.outputString(doc);
        return rez;
    }

    /** Генерирует заголовок для сообщения.*/
    private static Element GenerateHeader(String MessageType_in, String GameGUID_in)
    {
        Element rez = new Element("Header");
        // -Тип сообщения.
        AddSimpleTextElementXML("MessageType", MessageType_in, rez);
        // -ГУИД игры.
        AddSimpleTextElementXML("Game", GameGUID_in, rez);
        return rez;
    }

    /** Генерирует xml сыгранной карты.*/
    public static String GenerateCardPlaying(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, WarcastleDuelCard Card_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_CARD_PLAYING, Game_in.getGUID());
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("Player", Player_in.getGUID(), BodyXML);
        // -Сыгранная карта.
        AddSimpleTextElementXML("Card", Card_in.getGUID(), BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
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
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_CREATE_GAME, "");
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("PlayerName", PlayerName, BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для минимальной информации об игроке.*/
    private static Element GenerateMinPlayerXML(WarcastleDuelPlayer Player_in)
    {
        Element rez = new Element("Player");
        AddSimpleTextElementXML("Name", Player_in.getName(), rez);
        AddSimpleTextElementXML("GUID", Player_in.getGUID(), rez);
        return rez;
    }

    /** Генерирует xml для отображения игры Game_in для игрока Player_in.*/
    public static String GenerateFullGameXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_FULL_GAME, Game_in.getGUID());
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        Element PlayerXML = GenerateMinPlayerXML(Player_in);
        BodyXML.addContent(PlayerXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для получения полной игры.*/
    public static String GenerateRequestFullGameXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_REQUEST_FULL_GAME, Game_in.getGUID());
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("Player", Player_in.getGUID(), BodyXML);
        AddSimpleTextElementXML("Game", Game_in.getGUID(), BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }
}
