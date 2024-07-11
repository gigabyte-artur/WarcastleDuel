package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnClient;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnPackedMessage;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnXmlBuilder;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ScreenCard PrivateDeckCover;
    private Texture background;
    private ScreenSoundList SoundList;
    private WarcastleDuelGame GamePlaying;
    private BitmapFont DeckFont;
    private ScreenEndTurnButton ButtonEndTurn;
    private ScreenStatusBar StatusBar1;
    private ScreenGroupSlotsRectangled GroupSlot1;
    private ScreenWarcastlePlyerInfo Player1Info, Player2Info;
    private ScreenAddStatButton ButtonAddSword1;
    private ScreenAddStatButton ButtonAddPriest1;
    private ScreenAddStatButton ButtonAddInstructor1;
    private ScreenAddStatButton ButtonAddPeasant1;
    private ScreenAddStatButton ButtonAddHorse1;
    private Stage stage;
    private WcnClient Client1;
    private boolean LockedInterface = false;               // Признак, что интерфейс формы заблокирован.
    private Timer TimerUpdateGame = new Timer();           // Таймер обновления информации об игре.
    private String ClientID = "";                          // ID клиента сетевой игры.
    private Integer CurrentPlayerNumber = 0;               // Номер текущего игрока.

    public boolean isLockedInterface()
    {
        return LockedInterface;
    }

    public void setLockedInterface(boolean lockedInterface)
    {
        LockedInterface = lockedInterface;
    }

    private Action ActionEndTurn_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            try
            {
                EndTurn_Finish();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return false;
        }
    };

    private void SendAddStatMessage(int StatID_in)
    {
        WarcastleDuelGame CurrentGame = this.getGamePlaying();
        WarcastleDuelPlayer CurrentPlayer = new WarcastleDuelPlayer();
        if (getCurrentPlayerNumber() == 1)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer1();
        else if (getCurrentPlayerNumber() == 2) {
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer2();
        }
        String EndTurnXML = WcnXmlBuilder.GenerateAddStatXML(CurrentGame, CurrentPlayer, StatID_in, 1, getClientID());
        Client1.SendStringMessage(EndTurnXML);
    }

    private Action ActionAddStatSword_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            if (isActivePlayer1())
                SendAddStatMessage(WarcastleDuelPlayer.STAT_ID_SWORD);
            AfterUserAction();
            return false;
        }
    };

    private Action ActionAddStatPriest_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            if (isActivePlayer1())
                SendAddStatMessage(WarcastleDuelPlayer.STAT_ID_PRIEST);
            AfterUserAction();
            return false;
        }
    };

    private Action ActionAddStatInstructor_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            if (isActivePlayer1())
                SendAddStatMessage(WarcastleDuelPlayer.STAT_ID_INSTRUCTOR);
            AfterUserAction();
            return false;
        }
    };

    private Action ActionAddStatPeasant_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            if (isActivePlayer1())
                SendAddStatMessage(WarcastleDuelPlayer.STAT_ID_PEASANT);
            AfterUserAction();
            return false;
        }
    };

    private Action ActionAddStatHorse_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            if (isActivePlayer1())
                SendAddStatMessage(WarcastleDuelPlayer.STAT_ID_HORSE);
            AfterUserAction();
            return false;
        }
    };

    public Integer getCurrentPlayerNumber()
    {
        return CurrentPlayerNumber;
    }

    public void setCurrentPlayerNumber(Integer currentPlayerNumber)
    {
        CurrentPlayerNumber = currentPlayerNumber;
    }

    public String getClientID()
    {
        return ClientID;
    }

    public void setClientID(String clientID)
    {
        ClientID = clientID;
    }

    public void setGamePlaying(WarcastleDuelGame game1)
    {
        GamePlaying = game1;
    }

    public WarcastleDuelGame getGamePlaying()
    {
        return GamePlaying;
    }

    public Stage getStage()
    {
        return stage;
    }

    private void SendRequestFullGame(WarcastleDuelGame game_in, WcnClient client_in)
    {
        String RequestFullGameXML = "";
        if (getCurrentPlayerNumber() == 1)
            RequestFullGameXML = WcnXmlBuilder.GenerateRequestFullGameXML(game_in, (WarcastleDuelPlayer) game_in.getPlayer1(), getClientID());
        else if (getCurrentPlayerNumber() == 2)
            RequestFullGameXML = WcnXmlBuilder.GenerateRequestFullGameXML(game_in, (WarcastleDuelPlayer) game_in.getPlayer2(), getClientID());
        client_in.SendStringMessage(RequestFullGameXML);
    }

    // Добавляет и стартует таймер обновления игры.
    private void StartTimerUpdateGame()
    {
        TimerUpdateGame.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                SendRequestFullGame(GamePlaying, Client1);
            }
        }, 0, 5000);
    }

    @Override
    public void show()
    {
        try
        {
            // Инициализация экрана.
            InitScreen();
            InitClient();
            StartTimerUpdateGame();
            SendRequestFullGame(GamePlaying, Client1);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /** Устанавливает активных пользователей в карточки пользователей.*/
    private void SetActivePlayerToInfo()
    {
        boolean isActive = isActivePlayer1();
        if (getCurrentPlayerNumber() == 1)
        {
            Player1Info.setActivePlayer(isActive);
            Player2Info.setActivePlayer(!isActive);
        }
        if (getCurrentPlayerNumber() == 2)
        {
            Player1Info.setActivePlayer(!isActive);
            Player2Info.setActivePlayer(isActive);
        }
    }

    @Override
    public void render(float delta)
    {
        // Инициализация.
        Integer PlayerNumber = getCurrentPlayerNumber();
        batch.begin();
        UnblockActivePlayerInterface();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Выводим колоду.
        PrivateDeckCover.draw(batch);
        if (PlayerNumber == 1)
        {
            DeckFont.draw(batch, "" + this.getGamePlaying().getPlayer1PrivateDeckSize(), 1180, 330);
            DeckFont.draw(batch, "" + this.getGamePlaying().getPlayer2PrivateDeckSize(), 1180, 630);
        }
        else if (PlayerNumber == 2)
        {
            DeckFont.draw(batch, "" + this.getGamePlaying().getPlayer2PrivateDeckSize(), 1180, 330);
            DeckFont.draw(batch, "" + this.getGamePlaying().getPlayer1PrivateDeckSize(), 1180, 630);
        }
        // Тексты.
        SetActivePlayerToInfo();
        Player1Info.draw(batch);
        Player2Info.draw(batch);
        // Строка состояния.
        StatusBar1.draw(batch);
        // Карты.
        if (PlayerNumber == 1)
            ReadCardToScreenCard(GetPlayer1Cards());
        else if (PlayerNumber == 2)
            ReadCardToScreenCard(GetPlayer2Cards());
        // Кнопка Следующего хода.
        for (ScreenCard Curr_Card:ScreenCards)
        {
            Curr_Card.setParentScreen(this);
        }
        // Кнопка Конец хода.
        ButtonEndTurn.draw(batch,1);
        // Client ID.
        DeckFont.draw(batch, "" + ClientID, 100, 130);
        batch.end();
        // Кнопка Добавить мечников.
        ButtonAddSword1.Render(delta);
        ButtonAddPriest1.Render(delta);
        ButtonAddInstructor1.Render(delta);
        ButtonAddPeasant1.Render(delta);
        ButtonAddHorse1.Render(delta);
        // Блокировнаие неактивного игрока.
        BlockUnactivePlayerInterface();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        batch.dispose();
        stage.dispose();
        TimerUpdateGame.cancel();
//        Client1.stop();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    // Считывает карты из массива Cards_in в карты на столе.
    private void ReadCardToScreenCard(ArrayList<WarcastleDuelCard> Cards_in)
    {
        ScreenCard NewScreenCard;
        boolean isFound = false;
        ArrayList <ScreenCard> RemovableCadrs = new ArrayList<>();
        // Добавление новых карт.
        for (WarcastleDuelCard Curr_WarcastleCard:Cards_in)
        {
            isFound = false;
            for (ScreenCard Curr_ScreenCard: ScreenCards)
            {
                if (Curr_ScreenCard.getLinkedCard().getGUID().equals(Curr_WarcastleCard.getGUID()))
                {
                    isFound = true;
                    break;
                }
            }
            if (!isFound)
            {
                NewScreenCard = new ScreenCard(Curr_WarcastleCard, Curr_WarcastleCard.getStandardTexturePath());
                GroupSlot1.LinkElementToFirstEmptySlot(NewScreenCard);
                NewScreenCard.setCovered(false);
                ScreenCards.add(NewScreenCard);
                NewScreenCard.setScreenStage(stage);
                NewScreenCard.AddDragListener();
                NewScreenCard.setParentScreen(this);
            }
        }
        // Удаление незадействованных карт.
        for (ScreenCard Curr_ScreenCard: ScreenCards)
        {
            isFound = false;
            for (WarcastleDuelCard Curr_WarcastleCard:Cards_in)
            {
                if (Curr_ScreenCard.getLinkedCard().getGUID().equals(Curr_WarcastleCard.getGUID()))
                {
                    isFound = true;
                    break;
                }
            }
            if (!isFound)
            {
                RemovableCadrs.add(Curr_ScreenCard);
            }
        }
        for (ScreenCard Curr_RemovableCard:RemovableCadrs)
        {
            this.ScreenCards.remove(Curr_RemovableCard);
            Curr_RemovableCard.RemoveFromStage(stage);
        }
    }

    /** Размещает кнопки добавления статов для игрока Player_in на сцену stage.*/
    private void PutAddButtons(WarcastleDuelPlayer Player_in, Stage stage)
    {
        // ИНициализация.
        int StatIdSword          = WarcastleDuelPlayer.STAT_ID_SWORD;
        int StatIdPriest         = WarcastleDuelPlayer.STAT_ID_PRIEST;
        int StatIdInstructor     = WarcastleDuelPlayer.STAT_ID_INSTRUCTOR;
        int StatIdPeasant        = WarcastleDuelPlayer.STAT_ID_PEASANT;
        int StatIdPHorse         = WarcastleDuelPlayer.STAT_ID_HORSE;
        // Размещение кнопок.
        ButtonAddSword1          = new ScreenAddStatButton(Player_in, StatIdSword, 70, 270, 30, 30, stage);
        ButtonAddPriest1         = new ScreenAddStatButton(Player_in, StatIdPriest, 185, 270, 30, 30, stage);
        ButtonAddInstructor1     = new ScreenAddStatButton(Player_in, StatIdInstructor, 310, 270, 30, 30, stage);
        ButtonAddPeasant1        = new ScreenAddStatButton(Player_in, StatIdPeasant, 70, 235, 30, 30, stage);
        ButtonAddHorse1          = new ScreenAddStatButton(Player_in, StatIdPHorse, 185, 235, 30, 30, stage);
        // Добавление действий после нажатия кнопки.
        ButtonAddSword1.setAfterActButton(ActionAddStatSword_Finish);
        ButtonAddPriest1.setAfterActButton(ActionAddStatPriest_Finish);
        ButtonAddInstructor1.setAfterActButton(ActionAddStatInstructor_Finish);
        ButtonAddPeasant1.setAfterActButton(ActionAddStatPeasant_Finish);
        ButtonAddHorse1.setAfterActButton(ActionAddStatHorse_Finish);
    }

    /** Возвращает номер игрока по сообщению Message_in, чей ClientID совпадает с переданным.*/
    private Integer GetPlayerNumberByCLientID(String ClientId_in, String Message_in)
    {
        Integer rez = 0;
        String LastText = "";
        LastText = WcnPackedMessage.ExtractTextByXmlPath(Message_in, "Body->Player1->Player->ClientID");
        if (ClientId_in.equals(LastText))
        {
            rez = 1;
        }
        LastText = WcnPackedMessage.ExtractTextByXmlPath(Message_in, "Body->Player2->Player->ClientID");
        if (ClientId_in.equals(LastText))
        {
            rez = 2;
        }
        return rez;
    }

    /** Распреляет соотбщения ReceivedMessage_in по типу сообщения MessageType_in.*/
    private void RouteMessageByType(String ReceivedMessage_in, String MessageType_in)
    {
        if (MessageType_in.equals(WcnXmlBuilder.MESSAGE_TYPE_FULL_GAME))
        {
            Integer NewPlayerNumber = 0;
            GamePlaying.BuildGameByXml(ReceivedMessage_in);
            NewPlayerNumber = GetPlayerNumberByCLientID(getClientID(), ReceivedMessage_in);
            setCurrentPlayerNumber(NewPlayerNumber);
            ReadGameToScreen(GamePlaying);
        }
    }

    /** Выполняет начальную инициализацию сетвеого клиента. */
    private void InitClient() throws Exception {
        // Клиент.
        Client1 = new WcnClient("localhost", 27960,27960);
        Client1.StartClient();
        // Определение слушателя для клиента
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

    /** Считывает игру Game_in на экран.*/
    private void ReadGameToScreen(WarcastleDuelGame Game_in)
    {
        // Получение игроков.
        this.setGamePlaying(Game_in);
        WarcastleDuelPlayer CurrPlayer1 = (WarcastleDuelPlayer)GamePlaying.getPlayer1();
        WarcastleDuelPlayer CurrPlayer2 = (WarcastleDuelPlayer)GamePlaying.getPlayer2();
        if (getCurrentPlayerNumber() == 1) {
            // Вывод статов игроков.
            Player1Info.setLinkedPlayer(CurrPlayer1);
            Player2Info.setLinkedPlayer(CurrPlayer2);
            // Кпоки добавления статов игрока 1.
            ButtonAddSword1.setChangingPlayer(CurrPlayer1);
            ButtonAddPriest1.setChangingPlayer(CurrPlayer1);
            ButtonAddInstructor1.setChangingPlayer(CurrPlayer1);
            ButtonAddPeasant1.setChangingPlayer(CurrPlayer1);
            ButtonAddHorse1.setChangingPlayer(CurrPlayer1);
        }
        else if (getCurrentPlayerNumber() == 2)
        {
            // Вывод статов игроков.
            Player1Info.setLinkedPlayer(CurrPlayer2);
            Player2Info.setLinkedPlayer(CurrPlayer1);
            // Кнопки добавления статов игрока 2.
            ButtonAddSword1.setChangingPlayer(CurrPlayer2);
            ButtonAddPriest1.setChangingPlayer(CurrPlayer2);
            ButtonAddInstructor1.setChangingPlayer(CurrPlayer2);
            ButtonAddPeasant1.setChangingPlayer(CurrPlayer2);
            ButtonAddHorse1.setChangingPlayer(CurrPlayer2);
        }
        // Позиционирование элементов.
        Player1Info.setPosition(100, 400);
        Player2Info.setPosition(100, 200);
        // Действия после активностей игрока.
        AfterUserAction();
    }

    private FileHandle GetBgFile()
    {
        FileHandle rez = Gdx.files.internal("Interface/BG.jpg");
        final Random random = new Random();
        int BgId = random.nextInt(5);
        if (BgId == 0)
            rez = Gdx.files.internal("Interface/BG.jpg");
        else if (BgId == 1)
            rez = Gdx.files.internal("Interface/BG2.jpg");
        else if (BgId == 2)
            rez = Gdx.files.internal("Interface/BG3.jpg");
        else if (BgId == 3)
            rez = Gdx.files.internal("Interface/BG4.jpg");
        else if (BgId == 4)
            rez = Gdx.files.internal("Interface/BG5.jpg");
        return rez;
    }

    /** Выполняет начальную инициализацию экрана. */
    private void InitScreen() throws Exception
    {
        // Инициализация.
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(this);
        background = new Texture(GetBgFile());
        ScreenCards = new ArrayList<>();
        StatusBar1 = new ScreenStatusBar();
        setLockedInterface(false);
        // Звуки.
        SoundList = new ScreenSoundList();
        SoundList.AddSound("DrawSword", "Sounds/DrawSword.ogg");
        SoundList.AddSound("PapperWrapping", "Sounds/PapperWrapping.mp3");
        // Строка состояния.
        StatusBar1.InitText(3);
        StatusBar1.setPosition(50, 0);
        // Карты на экране.
        GroupSlot1 = new ScreenGroupSlotsRectangled(WarcastleDuelPlayer.MAX_CARDS_HAND);
        GroupSlot1.SetListElementsPositions(100, 100, 100, 0);
        // Звук получения карты.
        SoundList.PlaySound("PapperWrapping");
        // Отображение колоды.
        PrivateDeckCover = new ScreenCard();
        PrivateDeckCover.setPosition(1150, 350);
        PrivateDeckCover.setDimensions(ScreenCard.STANDARD_WIDTH, ScreenCard.STANDARD_HEIGHT);
        PrivateDeckCover.setCovered(true);
        PrivateDeckCover.setParentScreen(this);
        PrivateDeckCover.setScreenStage(stage);
        // Шрифт надписи количества карт в колоде.
        DeckFont = new BitmapFont();
        DeckFont.setColor(Color.BROWN);
        // Статы игроков.
        Player1Info = new ScreenWarcastlePlyerInfo();
        Player2Info = new ScreenWarcastlePlyerInfo();
        // Кнопка окончания хода.
        ButtonEndTurn = new ScreenEndTurnButton(GamePlaying, 870, 360, 300, 100, stage);
        ButtonEndTurn.setAfterActButton(ActionEndTurn_Finish);
        // Состояние.
        StatusBar1.AddText("Begin game");
        // Считывание игры.
        if (getCurrentPlayerNumber() == 1)
            PutAddButtons((WarcastleDuelPlayer) GamePlaying.getPlayer1(), stage);
        else if (getCurrentPlayerNumber() == 2) {
            PutAddButtons((WarcastleDuelPlayer) GamePlaying.getPlayer2(), stage);
        }
        // Обновление экрана.
        AfterUserAction();
    }

    // Возвращает массив карт в руке игрока 1.
    private ArrayList<WarcastleDuelCard> GetPlayer1Cards()
    {
        ArrayList<WarcastleDuelCard> rez = new ArrayList<>();
        WarcastleDuelPlayer Player1 = (WarcastleDuelPlayer)this.getGamePlaying().getPlayer1();
        for (Card Curr_Card:Player1.getPrivateHand().getCards())
        {
            rez.add((WarcastleDuelCard)Curr_Card);
        }
        return rez;
    }

    // Возвращает массив карт в руке игрока 2.
    private ArrayList<WarcastleDuelCard> GetPlayer2Cards()
    {
        ArrayList<WarcastleDuelCard> rez = new ArrayList<>();
        WarcastleDuelPlayer Player2 = (WarcastleDuelPlayer)this.getGamePlaying().getPlayer2();
        for (Card Curr_Card:Player2.getPrivateHand().getCards())
        {
            rez.add((WarcastleDuelCard)Curr_Card);
        }
        return rez;
    }

    // Использует карту WarcastleCard_in.
    private void EffectCard(WarcastleDuelCard WarcastleCard_in) throws Exception
    {
        WarcastleDuelGame CurrentGame;
        WarcastleDuelPlayer CurrentPlayer = new WarcastleDuelPlayer();
        String XmlString = "";
        CurrentGame = this.getGamePlaying();
        if (getCurrentPlayerNumber() == 1)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer1();
        else if (getCurrentPlayerNumber() == 2) {
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer2();
        }
        StatusBar1.AddText(WarcastleCard_in.GenerateStatusBarTextEffect(CurrentGame, CurrentPlayer));
        SoundList.PlaySound("DrawSword");
        XmlString = WcnXmlBuilder.GenerateCardPlaying(CurrentGame, CurrentPlayer, WarcastleCard_in, getClientID());
        Client1.SendStringMessage(XmlString);
        AfterUserAction();
    }

    /** Окончание нажатия на кнопку.*/
    private void EndTurn_Finish() throws Exception
    {
        if (isActivePlayer1())
        {
            SoundList.PlaySound("PapperWrapping");
            WarcastleDuelGame CurrentGame;
            WarcastleDuelPlayer CurrentPlayer = new WarcastleDuelPlayer();
            CurrentGame = this.getGamePlaying();
            if (getCurrentPlayerNumber() == 1)
                CurrentPlayer = (WarcastleDuelPlayer) CurrentGame.getPlayer1();
            else if (getCurrentPlayerNumber() == 2)
                CurrentPlayer = (WarcastleDuelPlayer) CurrentGame.getPlayer2();
            String EndTurnXML = WcnXmlBuilder.GenerateEndTurnXML(CurrentGame, CurrentPlayer, getClientID());
            Client1.SendStringMessage(EndTurnXML);
            StatusBar1.AddText("End turn");
        }
        else
        {
            // Текущий игрок неактивен. Ничего не делаем.
        }
        AfterUserAction();
    }

    public void CardDrag_Finish(ScreenCard Card_in, int DragX_in, int DragY_in) throws Exception
    {
        // Инициализация.
        WarcastleDuelCard CurrentWarcastleCard;
        // Выполнение действие карты.
        if (DragY_in > 300)
        {
            CurrentWarcastleCard = Card_in.getLinkedCard();
            if (CurrentWarcastleCard != null)
            {
                EffectCard(CurrentWarcastleCard);
                GroupSlot1.EmptySlotByElement(Card_in);
            }
        }
        else
        {
            GroupSlot1.RestoreLinkedListElementsPositions();
        }
        // Обновление карт на столе.
        if (getCurrentPlayerNumber() == 1)
            ReadCardToScreenCard(GetPlayer1Cards());
        else if (getCurrentPlayerNumber() == 2)
            ReadCardToScreenCard(GetPlayer2Cards());
    }

    /** Обновляетя доступность кнопок Добавить статы.*/
    private void UpdateAddStatEnabled()
    {
        // Инициализация.
        WarcastleDuelGame CurrentGame;
        WarcastleDuelPlayer CurrentPlayer = new WarcastleDuelPlayer();
        int CurrAmount, CurrSwordPrice, CurrPriestPrice, CurrInstructorPrice, CurrPeasantPrice, CurrHorsePrice;
        boolean EnoughtSwordPrice, EnoughtPriestPrice, EnoughtInstructorPrice, EnoughtPeasantPrice, EnoughtHorsePrice;
        // Получение размера счёта игрока.
        CurrentGame = this.getGamePlaying();
        if (getCurrentPlayerNumber()== 1)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer1();
        else if (getCurrentPlayerNumber()== 2)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer2();
        CurrAmount = CurrentPlayer.getAmount();
        // Получение цен статов.
        CurrSwordPrice       = CurrentPlayer.SwordPrice();
        CurrPriestPrice      = CurrentPlayer.PriestPrice();
        CurrInstructorPrice  = CurrentPlayer.InstructorPrice();
        CurrPeasantPrice     = CurrentPlayer.PeasantPrice();
        CurrHorsePrice       = CurrentPlayer.HorsePrice();
        // Определение достаточности денег для покупки.
        EnoughtSwordPrice        = (CurrSwordPrice <= CurrAmount);
        EnoughtPriestPrice       = (CurrPriestPrice <= CurrAmount);
        EnoughtInstructorPrice   = (CurrInstructorPrice <= CurrAmount);
        EnoughtPeasantPrice      = (CurrPeasantPrice <= CurrAmount);
        EnoughtHorsePrice        = (CurrHorsePrice <= CurrAmount);
        // Установка доступности кнопок покупки.
        ButtonAddSword1.setDisabled(!EnoughtSwordPrice);
        ButtonAddPriest1.setDisabled(!EnoughtPriestPrice);
        ButtonAddInstructor1.setDisabled(!EnoughtInstructorPrice);
        ButtonAddPeasant1.setDisabled(!EnoughtPeasantPrice);
        ButtonAddHorse1.setDisabled(!EnoughtHorsePrice);
    }

    /** Вызывается после действий пользователя.*/
    private void AfterUserAction()
    {
        WarcastleDuelGame CurrentGame;
        WarcastleDuelPlayer CurrentPlayer = new WarcastleDuelPlayer();
        CurrentGame = this.getGamePlaying();
        if (getCurrentPlayerNumber() == 1)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer1();
        else if (getCurrentPlayerNumber() == 2)
            CurrentPlayer = (WarcastleDuelPlayer)CurrentGame.getPlayer2();
        CurrentPlayer.CalculateStats();
        UpdateAddStatEnabled();
    }

    private boolean isActivePlayer1()
    {
        boolean rez = GamePlaying.getPlayer1().equals(GamePlaying.getActivePlayer());
        return rez;
    }

    /** Блокирует интерфейс пользователя, если пользователь неактивный.*/
    private void BlockUnactivePlayerInterface()
    {
        boolean isActive = isActivePlayer1();
        if (!isActive)
        {
            // Кнопки добавления статов.
            ButtonAddSword1.setDisabled(true);
            ButtonAddPriest1.setDisabled(true);
            ButtonAddInstructor1.setDisabled(true);
            ButtonAddPeasant1.setDisabled(true);
            ButtonAddHorse1.setDisabled(true);
            // Кнопка окончания хода.
            ButtonEndTurn.setDisabled(true);
            // Карты игрока.
            for (ScreenCard Curr_ScreenCard:this.ScreenCards)
            {
                Curr_ScreenCard.setActive(false);
            }
        }
        else
        {
            // Ничего не делаем.
        }
    }

    /** Блокирует интерфейс пользователя, если пользователь неактивный.*/
    private void UnblockActivePlayerInterface()
    {
        boolean isActive = (GamePlaying.getPlayer1().equals(GamePlaying.getActivePlayer()));
        if (isActive)
        {
            // Кнопки добавления статов.
            ButtonAddSword1.setDisabled(false);
            ButtonAddPriest1.setDisabled(false);
            ButtonAddInstructor1.setDisabled(false);
            ButtonAddPeasant1.setDisabled(false);
            ButtonAddHorse1.setDisabled(false);
            // Кнопка окончания хода.
            ButtonEndTurn.setDisabled(false);
            // Карты игрока.
            for (ScreenCard Curr_ScreenCard:this.ScreenCards)
            {
                Curr_ScreenCard.setActive(true);
            }
        }
        else
        {
            // Ничего не делаем.
        }
    }
}
