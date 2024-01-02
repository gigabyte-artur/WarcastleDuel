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
import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.*;
import java.util.ArrayList;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ScreenCard PrivateDeckCover;
    private Texture background;
    private int dragOffsetX, dragOffsetY;
    private ScreenSoundList SoundList;
    private WarcastleDuelGame GamePlaying;
    private BitmapFont DeckFont;
    private ScreenEndTurnButton ButtonEndTurn;
    private ScreenStatusBar StatusBar1;
    private ScreenGroupSlotsRectangled GroupSlot1;
    private ScreenWarcastlePlyerInfo Player1Info, Player2Info;
    private ScreenAddStatButton ButtonAddSword1;

    private Stage stage;

    private Action ActionEndTurn_Finish = new Action()
    {
        @Override
        public boolean act(float v)
        {
            EndTurn_Finish();
            return false;
        }
    };

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

    @Override
    public void show()
    {
        InitScreen();
    }

    @Override
    public void render(float delta)
    {
        // Инициализация.
        WarcastlePlayer CurrentPlayer;
        int count = 0;
        batch.begin();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Выводим колоду.
        PrivateDeckCover.draw(batch);
        CurrentPlayer = ((WarcastlePlayer)this.getGamePlaying().getPlayer1());
        DeckFont.draw(batch, "" + CurrentPlayer.getDeck().Size(), 1180, 330);
        // Тексты.
        Player1Info.draw(batch);
        Player2Info.draw(batch);
        // Строка состояния.
        StatusBar1.draw(batch);
        // Кнопка Конец хода.
        ButtonEndTurn.draw(batch,1);
        batch.end();
        // Кнопка Добавить мечников.
        ButtonAddSword1.Render(delta);
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
    public void dispose() {

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
    private void ReadCardToScreenCard(ArrayList<WarcastleCard> Cards_in)
    {
        ScreenCard NewScreenCard;
        boolean isFound = false;
        ArrayList <ScreenCard> RemovableCadrs = new ArrayList<>();
        // Добавление новых карт.
        for (WarcastleCard Curr_WarcastleCard:Cards_in)
        {
            isFound = false;
            for (ScreenCard Curr_ScreenCard: ScreenCards)
            {
                if (Curr_ScreenCard.getLinkedCard().equals(Curr_WarcastleCard))
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
            for (WarcastleCard Curr_WarcastleCard:Cards_in)
            {
                if (Curr_ScreenCard.getLinkedCard().equals(Curr_WarcastleCard))
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

    /** Выполняет начальную инициализацию экрана. */
    private void InitScreen()
    {
        // Инициализация.
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/BG.jpg"));
        ScreenCards = new ArrayList<>();
        StatusBar1 = new ScreenStatusBar();
        WarcastlePlayer CurrPlayer1 = (WarcastlePlayer)GamePlaying.getPlayer1();
        WarcastlePlayer CurrPlayer2 = (WarcastlePlayer)GamePlaying.getPlayer2();
        int StatIdSword = WarcastlePlayer.STAT_ID_SWORD;
        // Звуки.
        SoundList = new ScreenSoundList();
        SoundList.AddSound("DrawSword", "Sounds/DrawSword.ogg");
        SoundList.AddSound("PapperWrapping", "Sounds/PapperWrapping.mp3");
        // Строка состояния.
        StatusBar1.InitText(3);
        StatusBar1.setPosition(50, 0);
        // Карты на экране.
        GroupSlot1 = new ScreenGroupSlotsRectangled(WarcastlePlayer.MAX_CARDS_HAND);
        GroupSlot1.SetListElementsPositions(100, 100, 100, 0);
        ReadCardToScreenCard(GetPlayer1Cards());
        SoundList.PlaySound("PapperWrapping");
        for (ScreenCard Curr_Card:ScreenCards)
        {
            Curr_Card.setParentScreen(this);
        }
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
        Player1Info.setLinkedPlayer(CurrPlayer1);
        Player1Info.setPosition(100, 400);
        Player2Info = new ScreenWarcastlePlyerInfo();
        Player2Info.setLinkedPlayer(CurrPlayer2);
        Player2Info.setPosition(100, 200);
        // Кнопка окончания хода.
        ButtonEndTurn = new ScreenEndTurnButton(GamePlaying, 900, 350, 220, 60, stage);
        ButtonEndTurn.setAfterActButton(ActionEndTurn_Finish);
        // Состояние.
        StatusBar1.AddText("Begin game");
        // Кнопка Добавить мечников.
        ButtonAddSword1 = new ScreenAddStatButton(CurrPlayer1, StatIdSword, 70, 270, 30, 30, stage);
    }

    // Возвращает массив карт в руке игрока 1.
    private ArrayList<WarcastleCard> GetPlayer1Cards()
    {
        ArrayList<WarcastleCard> rez = new ArrayList<>();
        WarcastlePlayer Player1 = (WarcastlePlayer)this.getGamePlaying().getPlayer1();
        for (Card Curr_Card:Player1.getPrivateHand().getCards())
        {
            rez.add((WarcastleCard)Curr_Card);
        }
        return rez;
    }

    // Использует карту WarcastleCard_in.
    private void EffectCard(WarcastleCard WarcastleCard_in)
    {
        WarcastleDuelGame CurrentGame;
        WarcastlePlayer CurrentPlayer;
        CurrentGame = this.getGamePlaying();
        CurrentPlayer = (WarcastlePlayer)CurrentGame.getPlayer1();
        WarcastleCard_in.Effect(CurrentGame, CurrentPlayer);
        CurrentPlayer.PrivateHandCardToDiscard(WarcastleCard_in);
        StatusBar1.AddText(WarcastleCard_in.GenerateStatusBarTextEffect(CurrentGame, CurrentPlayer));
        SoundList.PlaySound("DrawSword");
        CurrentPlayer.CalculateStats();
    }

    /** Окончание нажатия на кнопку.*/
    private void EndTurn_Finish()
    {
        SoundList.PlaySound("PapperWrapping");
        ReadCardToScreenCard(GetPlayer1Cards());
        StatusBar1.AddText("End turn");
    }

    public void CardDrag_Finish(ScreenCard Card_in, int DragX_in, int DragY_in)
    {
        // Инициализация.
        WarcastleCard CurrentWarcastleCard;
        // Выполнение действие карты.
        if (DragY_in > 300)
        {
            CurrentWarcastleCard = (WarcastleCard) Card_in.getLinkedCard();
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
        ReadCardToScreenCard(GetPlayer1Cards());
    }
}
