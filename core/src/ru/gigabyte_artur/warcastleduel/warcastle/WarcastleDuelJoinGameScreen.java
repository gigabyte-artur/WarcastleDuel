package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.jdom2.Element;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnClient;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnPackedMessage;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnXmlBuilder;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.ScreenCreateGameButton;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.ScreenJoinGameButton;
import java.util.HashMap;
import java.util.List;

public class WarcastleDuelJoinGameScreen  implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private ScreenCreateGameButton ButtonCreatGame;
    private ScreenJoinGameButton ButtonJoinGame;
    private WarcastleDuelApplication MainApplication;
    private WcnClient Client1;
    private HashMap<String,String> GameList;
    private BitmapFont NameFont = new BitmapFont();
    private String ClientID = "";                       // ID клиента сетевого подключения.

    private Action ActionCreateGame_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            try
            {
                CreateGame_Finish();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return false;
        }
    };

    private Action ActionJoinGame_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            try
            {
                JoinGame_Finish();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return false;
        }
    };

    public WarcastleDuelJoinGameScreen(WarcastleDuelApplication mainApplication)
    {
        MainApplication = mainApplication;
    }


    public String getClientID()
    {
        return ClientID;
    }

    public void setClientID(String clientID)
    {
        ClientID = clientID;
    }

    @Override
    public boolean keyDown(int i)
    {
        return false;
    }

    @Override
    public boolean keyUp(int i)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char c)
    {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3)
    {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3)
    {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1)
    {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1)
    {
        return false;
    }

    /** Отправляет запрос к серверу для обновления списка игр.*/
    private void UpdateGameList()
    {
        String RequestGameListXML = WcnXmlBuilder.GenerateRequestGameListXML(getClientID());
        Client1.SendStringMessage(RequestGameListXML);
    }

    @Override
    public void show()
    {
        try
        {
            InitClient();
            UpdateGameList();
            InitScreen();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void DrawGameList(SpriteBatch batch_in)
    {
        int y = 500;
        NameFont.setColor(Color.WHITE);
        for (HashMap.Entry<String, String> entry : GameList.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            NameFont.draw(batch_in, value, 200, y);
            y = y - 50;
        }
    }

    @Override
    public void render(float v)
    {
        batch.begin();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Кнопка Конец хода.
        ButtonCreatGame.draw(batch,1);
        ButtonJoinGame.draw(batch,1);
        // Список игр.
        DrawGameList(batch);
        batch.end();
    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

    private void InitClient() throws Exception
    {
        // Клиент.
        Client1 = new WcnClient("localhost", 27960,27960);
        setClientID(String.valueOf(java.util.UUID.randomUUID()));
        Client1.StartClient();
        Client1.getInnerClient().addListener(
                new Listener() {
                    public void received(Connection connection, Object object) {
                        String ReceivedMessage;
                        String MessageType;
                        if (object instanceof WcnPackedMessage)
                        {
                            WcnPackedMessage response = (WcnPackedMessage) object;
                            if (response.getProtocolVersion().equals(Client1.getProtocolVersion()))
                            {
                                ReceivedMessage = response.getMessage();
                                System.out.println("Answer from client: " + ReceivedMessage);
                                MessageType = WcnPackedMessage.ExtractMessageType(ReceivedMessage);
                                RouteMessageByType(ReceivedMessage, MessageType);
                            }
                            else
                            {
                                System.out.println("Protocol version not compares. Update client, please.");
                            }
                        }
                    }
                });
    }

    /** Считывает список иг из XML*/
    private void ReadGameListFromXM(String ReceivedMessage_in)
    {
        // Инициализация.
        Element GamesElement = WcnPackedMessage.ExtractElementByXmlPath(ReceivedMessage_in, "Body->Games");
        String GuidText, Player1GuidText, Player1NameText;
        // Чтение XML
        List<Element> CardsElements = GamesElement.getChildren();
        for (Element Curr_CardElements : CardsElements)
        {
            GuidText         = WcnPackedMessage.ExtractStringByXmlPathFromElement(Curr_CardElements, "GUID");
            Player1GuidText  = WcnPackedMessage.ExtractStringByXmlPathFromElement(Curr_CardElements, "Player1Guid");
            Player1NameText  = WcnPackedMessage.ExtractStringByXmlPathFromElement(Curr_CardElements, "Player1Name");
            GameList.put(GuidText, Player1NameText);
        }
    }

    /** Распреляет соотбщения ReceivedMessage_in по типу сообщения MessageType_in.*/
    private void RouteMessageByType(String ReceivedMessage_in, String MessageType_in)
    {
        if (MessageType_in.equals(WcnXmlBuilder.MESSAGE_TYPE_GAME_LIST))
        {
            ReadGameListFromXM(ReceivedMessage_in);
        }
    }

    private void InitScreen() throws Exception
    {
        GameList = new HashMap();
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/JoinGameBG.jpg"));
        // Кнопка создания игры.
        ButtonCreatGame = new ScreenCreateGameButton(300, 70, 400, 100, stage);
        ButtonCreatGame.setAfterActButton(ActionCreateGame_Finish);
        // Кнопка подключения к игре.
        ButtonJoinGame = new ScreenJoinGameButton(600, 70, 400, 100, stage);
        ButtonJoinGame.setAfterActButton(ActionJoinGame_Finish);
    }

    private void CreateGame_Finish()
    {
        String CreateGameXML = WcnXmlBuilder.GenerateCreateGame("Lucas", getClientID());
        Client1.SendStringMessage(CreateGameXML);
        this.hide();
        Client1.StopClient();
        WarcastleDuelScreen GameScreen = new WarcastleDuelScreen();
        GameScreen.setCurrentPlayerNumber(1);       // Первый игрок.
        WarcastleDuelGame Game1 = new WarcastleDuelGame();
        Game1.Init();
        GameScreen.setGamePlaying(Game1);
        GameScreen.setClientID(getClientID());
        MainApplication.setScreen(GameScreen);
    }

    private void JoinGame_Finish()
    {
        String NewGUID = "";
        for (HashMap.Entry<String, String> entry : GameList.entrySet()) {
            NewGUID = entry.getKey();
        }
        String CreateGameXML = WcnXmlBuilder.GenerateJoinGameXML(NewGUID, getClientID());
        Client1.SendStringMessage(CreateGameXML);
        this.hide();
        WarcastleDuelScreen GameScreen = new WarcastleDuelScreen();
        GameScreen.setCurrentPlayerNumber(2);       // Второй игрок.
        WarcastleDuelGame Game1 = new WarcastleDuelGame();
        Game1.Init();
        GameScreen.setGamePlaying(Game1);
        GameScreen.setClientID(getClientID());
        MainApplication.setScreen(GameScreen);
    }
}
