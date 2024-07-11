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
    public final static String MESSAGE_TYPE_END_TURN = "EndTurn";                      // Тип сообщения "Конец хода".
    public final static String MESSAGE_TYPE_ADD_STAT = "AddStat";                      // Тип сообщения "Добавить стат".
    public final static String MESSAGE_TYPE_REQUEST_GAME_LIST = "RequestGameList";     // Тип сообщения "Запрос списка игр".
    public final static String MESSAGE_TYPE_GAME_LIST = "GameList";                    // Тип сообщения "Список игр".
    public final static String MESSAGE_TYPE_JOIN_GAME = "JoinGame";                    // Тип сообщения "Присоединиться к игре".

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
    private static Element GenerateHeader(String MessageType_in, String GameGUID_in, String ClientID_in)
    {
        Element rez = new Element("Header");
        // -Тип сообщения.
        AddSimpleTextElementXML("MessageType", MessageType_in, rez);
        // -ГУИД игры.
        AddSimpleTextElementXML("Game", GameGUID_in, rez);
        // -ClientID игры.
        AddSimpleTextElementXML("ClientID", ClientID_in, rez);
        return rez;
    }

    /** Генерирует xml сыгранной карты.*/
    public static String GenerateCardPlaying(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, WarcastleDuelCard Card_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_CARD_PLAYING, Game_in.getGUID(), ClientID_in);
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
    public static String GenerateCreateGame(String PlayerName, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_CREATE_GAME, "", ClientID_in);
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
    public static String GenerateFullGameXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_FULL_GAME, Game_in.getGUID(), ClientID_in);
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
    public static String GenerateRequestFullGameXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_REQUEST_FULL_GAME, Game_in.getGUID(), ClientID_in);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("Player1", Player_in.getGUID(), BodyXML);
        AddSimpleTextElementXML("Game", Game_in.getGUID(), BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для завершения хода.*/
    public static String GenerateEndTurnXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_END_TURN, Game_in.getGUID(), ClientID_in);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("Player1", Player_in.getGUID(), BodyXML);
        AddSimpleTextElementXML("Game", Game_in.getGUID(), BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для добавления стата. StatID_in указывает на ID стата, Value_in - добавляемое количество.*/
    public static String GenerateAddStatXML(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in, int StatID_in, int Value_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_ADD_STAT, Game_in.getGUID(), ClientID_in);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // -Игрок.
        AddSimpleTextElementXML("Player", Player_in.getGUID(), BodyXML);
        AddSimpleTextElementXML("Stat", String.valueOf(StatID_in), BodyXML);
        AddSimpleTextElementXML("Value", String.valueOf(Value_in), BodyXML);
        AddSimpleTextElementXML("Game", Game_in.getGUID(), BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для получения списка игр.*/
    public static String GenerateRequestGameListXML(String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_REQUEST_GAME_LIST, "", ClientID_in);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }

    /** Генерирует XML для получения списка игр.*/
    public static String GenerateJoinGameXML(String GameGUID_in, String ClientID_in)
    {
        String rez = "";
        // Создаем корневой элемент.
        Element rootElement = new Element("Message");
        // Создаем документ.
        Document doc = new Document(rootElement);
        // Заголовок.
        Element HeaderXML = GenerateHeader(MESSAGE_TYPE_JOIN_GAME, "", ClientID_in);
        // Тело сообщения.
        Element BodyXML = new Element("Body");
        AddSimpleTextElementXML("TargetGame", GameGUID_in, BodyXML);
        // Выводим XML
        rez = EndXML(rootElement, doc, HeaderXML, BodyXML);
        return rez;
    }


    public static Integer StringToInteger(String str_in)
    {
        Integer rez = 0;
        if (!str_in.isEmpty())
            rez = Integer.parseInt(str_in);
        else
            rez = 0;
        return rez;
    }
}
