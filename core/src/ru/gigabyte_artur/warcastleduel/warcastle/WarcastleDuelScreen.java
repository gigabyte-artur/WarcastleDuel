package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.*;

import java.util.ArrayList;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ScreenCard PrivateDeckCover;
    private Texture background;
    private Texture buttonUpTexture;
    private int dragOffsetX, dragOffsetY;
    private ScreenSoundList SoundList;
    private WarcastleDuelGame GamePlaying;
    private BitmapFont DeckFont;
    private ImageButton ButtonEndTurn;
    private ScreenStatusBar StatusBar1;
    private ScreenGroupSlotsRectangled GroupSlot1;
    private ScreenWarcastlePlyerInfo Player1Info, Player2Info;

    public void setGamePlaying(WarcastleDuelGame game1)
    {
        GamePlaying = game1;
    }

    public WarcastleDuelGame getGamePlaying()
    {
        return GamePlaying;
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
        // Выводим карту.
        for (ScreenCard curr_card:ScreenCards)
        {
            curr_card.draw(batch);
        }
        // Выводим колоду.
        PrivateDeckCover.draw(batch);
        CurrentPlayer = ((WarcastlePlayer)this.getGamePlaying().getPlayer1());
        DeckFont.draw(batch, "" + CurrentPlayer.getDeck().Size(), 830, 280);
        // Тексты.
        Player1Info.draw(batch);
        Player2Info.draw(batch);
        // Строка состояния.
        StatusBar1.draw(batch);
        // Кнопка Конец хода.
        ButtonEndTurn.draw(batch,1);
        batch.end();
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
        for (ScreenCard curr_card:ScreenCards)
        {
            if (curr_card.getCardSprite().getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY))
            {
                curr_card.setCardDragged(true);
                dragOffsetX = screenX - (int) curr_card.getX();
                dragOffsetY = (int) curr_card.getY() - (Gdx.graphics.getHeight() - screenY);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (ScreenCard curr_card:ScreenCards) {
            if (curr_card.isCardDragged())
            {
                curr_card.setPosition(screenX - dragOffsetX, Gdx.graphics.getHeight() - screenY + dragOffsetY);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // Перетаскивание карт.
        DragCardTouchUp(screenX, screenY);
        // Кнопки.
        ButtonsTouchUp(screenX, screenY);
        return true;
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
        }
    }

    /** Выполняет начальную инициализацию экрана. */
    private void InitScreen()
    {
        // Инициализация.
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/BG.jpg"));
        ScreenCards = new ArrayList<>();
        StatusBar1 = new ScreenStatusBar();
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
        // Отображение колоды.
        PrivateDeckCover = new ScreenCard();
        PrivateDeckCover.setPosition(1150, 350);
        PrivateDeckCover.setDimensions(ScreenCard.STANDARD_WIDTH, ScreenCard.STANDARD_HEIGHT);
        PrivateDeckCover.setCovered(true);
        // Статы игроков.
        Player1Info = new ScreenWarcastlePlyerInfo();
        Player1Info.setLinkedPlayer((WarcastlePlayer)GamePlaying.getPlayer1());
        Player1Info.setPosition(100, 400);
        Player2Info = new ScreenWarcastlePlyerInfo();
        Player2Info.setLinkedPlayer((WarcastlePlayer)GamePlaying.getPlayer2());
        Player2Info.setPosition(100, 200);
        // Шрифт надписи количества карт в колоде.
        DeckFont = new BitmapFont();
        DeckFont.setColor(Color.BROWN);
        // Кнопка окончания хода.
        buttonUpTexture = new Texture("Interface/EndTurnButton.png");
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        ButtonEndTurn = new ImageButton(buttonUp);
        ButtonEndTurn.setPosition(900, 350);
        ButtonEndTurn.setWidth(220);
        ButtonEndTurn.setHeight(60);
        // Состояние.
        StatusBar1.AddText("Begin game");
    }

    // Проверяет, что координаты ScreenX_in и ScreenY_in находятся внутри кнопки Button_in.
    private boolean ButtonPressedCoord(ImageButton Button_in, int ScreenX_in, int ScreenY_in)
    {
        boolean rez = false;
        float ButtonX, ButtonY;
        float ButtonWidth, ButtonHeight;
        int ScreenHeight = Gdx.graphics.getHeight();
        ButtonX = Button_in.getX();
        ButtonY = Button_in.getY();
        ButtonWidth = ButtonEndTurn.getWidth();
        ButtonHeight = ButtonEndTurn.getHeight();
        rez = (ButtonX < ScreenX_in) & (ButtonX + ButtonWidth > ScreenX_in) & (ButtonY < ScreenHeight - ScreenY_in) & (ButtonY + ButtonHeight > ScreenHeight - ScreenY_in);
        return rez;
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
    }

    // Обработка окончания перетаскивания карт.
    private void DragCardTouchUp(int screenX_in, int screenY_in)
    {
        WarcastleCard CurrentWarcastleCard;
        // Поиск перетаскиваемых карт и выполнение действий.
        for (ScreenCard curr_card:ScreenCards)
        {
            if (curr_card.isCardDragged())
            {
                if (Gdx.graphics.getHeight() - screenY_in > 200)
                {
                    CurrentWarcastleCard = (WarcastleCard)curr_card.getLinkedCard();
                    if (CurrentWarcastleCard != null)
                    {
                        EffectCard(CurrentWarcastleCard);
                        GroupSlot1.EmptySlotByElement(curr_card);
                    }
                }
                else
                {
                    GroupSlot1.RestoreLinkedListElementsPositions();
                }
                break;
            }
        }
        // Обновление карт на столе.
        ReadCardToScreenCard(GetPlayer1Cards());
        // Очистка перетаскиваемых карт.
        for (ScreenCard curr_card:ScreenCards)
        {
            curr_card.setCardDragged(false);
        }
    }

    // Обработчиик нажатия на кнопку Конец хода.
    private void ButtonEndTurnAction()
    {
        WarcastlePlayer Player1 = (WarcastlePlayer)GamePlaying.getPlayer1();
        GamePlaying.EndPlayerTurn(Player1);
        SoundList.PlaySound("PapperWrapping");
        ReadCardToScreenCard(GetPlayer1Cards());
        StatusBar1.AddText("End turn");
    }

    // Обработка нажатия кнопок.
    private void ButtonsTouchUp(int screenX_in, int screenY_in)
    {
        if (ButtonPressedCoord(ButtonEndTurn, screenX_in, screenY_in))
        {
            ButtonEndTurnAction();
        }
    }
}
