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
import java.util.ArrayDeque;
import java.util.ArrayList;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ScreenCard PrivateDeckCover;
    private Texture background;
    Texture buttonUpTexture;
    private int dragOffsetX, dragOffsetY;
    private ScreenSoundList SoundList;
    private WarcastleDuelGame GamePlaying;
    private BitmapFont StatsFont;
    private BitmapFont StatusBarFont;
    private BitmapFont DeckFont;
    private ImageButton ButtonEndTurn;
    private ArrayDeque<String> StatusBarText = new ArrayDeque<>();

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
        DeckFont.draw(batch, "" + CurrentPlayer.getDeck().Size(), 630, 90);
        // Тексты.
        StatsFont.draw(batch, "Amount: " + CurrentPlayer.getAmount(), 100, 400);
        StatsFont.draw(batch, "Swords: " + CurrentPlayer.getSwords(), 200, 400);
        StatsFont.draw(batch, "Priests: " + CurrentPlayer.getPriests(), 300, 400);
        StatsFont.draw(batch, "Instructors: " + CurrentPlayer.getInstructors(), 100, 350);
        StatsFont.draw(batch, "Peasants: " + CurrentPlayer.getPeasants(), 200, 350);
        StatsFont.draw(batch, "Horses: " + CurrentPlayer.getHorses(), 300, 350);
        StatsFont.draw(batch, "Morale: " + CurrentPlayer.getMorale(), 400, 350);
        // Строка состояния.
        count = 1;
        for (String curr_StatusBarText: StatusBarText)
        {
            StatusBarFont.draw(batch, curr_StatusBarText, 50, 30*count);
            count = count + 1;
        }
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
            if (curr_card.getCardSprite().getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
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
        int counter;
        boolean isFound = false;
        ArrayList <ScreenCard> RemovableCadrs = new ArrayList<>();
        // Добавление новых карт.
        counter = 1;
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
                NewScreenCard.setPosition(counter * 100, 100);
                NewScreenCard.setCovered(false);
                ScreenCards.add(NewScreenCard);
            }
            counter = counter + 1;
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

    // Выполняет начальную инициализацию экрана.
    private void InitScreen()
    {
        // Инициализация.
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/BG.jpg"));
        ScreenCards = new ArrayList<>();
        StatusBarText.add("");
        StatusBarText.add("");
        StatusBarText.add("");
        // Звуки.
        SoundList = new ScreenSoundList();
        SoundList.AddSound("DrawSword", "Sounds/DrawSword.ogg");
        SoundList.AddSound("PapperWrapping", "Sounds/PapperWrapping.mp3");
        // Карты на экране.
        ReadCardToScreenCard(GetPlayer1Cards());
        SoundList.PlaySound("PapperWrapping");
        // Отображение колоды.
        PrivateDeckCover = new ScreenCard();
        PrivateDeckCover.setPosition(600, 100);
        PrivateDeckCover.setDimensions(ScreenCard.STANDARD_WIDTH, ScreenCard.STANDARD_HEIGHT);
        PrivateDeckCover.setCovered(true);
        // Шрифт надписей статов.
        StatsFont = new BitmapFont();
        StatsFont.setColor(Color.WHITE);
        // Шрифт надписи количества карт в колоде.
        DeckFont = new BitmapFont();
        DeckFont.setColor(Color.BROWN);
        // Шрифт строки состояния.
        StatusBarFont = new BitmapFont();
        StatusBarFont.setColor(Color.GOLD);
        // Кнопка окончания хода.
        buttonUpTexture = new Texture("Interface/EndTurnButton.png");
        TextureRegionDrawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        ButtonEndTurn = new ImageButton(buttonUp);
        ButtonEndTurn.setPosition(550, 300);
        ButtonEndTurn.setWidth(220);
        ButtonEndTurn.setHeight(60);
    }

    // Проверяет, что координаты ScreenX_in и ScreenY_in находятся внутри кнопки Button_in.
    private boolean ButtonPressedCoord(ImageButton Button_in, int ScreenX_in, int ScreenY_in)
    {
        boolean rez = false;
        float ButtonX, ButtonY;
        float ButtonWidth, ButtonHeight;
        ButtonX = Button_in.getX();
        ButtonY = Button_in.getY();
        ButtonWidth = ButtonEndTurn.getWidth();
        ButtonHeight = ButtonEndTurn.getHeight();
        rez = (ButtonX < ScreenX_in) & (ButtonX + ButtonWidth > ScreenX_in) & (ButtonY < Gdx.graphics.getHeight() - ScreenY_in) & (ButtonY + ButtonHeight > Gdx.graphics.getHeight() - ScreenY_in);
        return rez;
    }

    // Возвращает массив карт в руке игрока 1.
    private ArrayList<WarcastleCard> GetPlayer1Cards()
    {
        ArrayList<WarcastleCard> rez = new ArrayList<>();
        for (Card Curr_Card:((WarcastlePlayer)this.getGamePlaying().getPlayer1()).getPrivateHand().getCards())
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
        AddTextToStatusBar(WarcastleCard_in.GenerateStatusBarTextEffect(CurrentGame, CurrentPlayer));
        SoundList.PlaySound("DrawSword");
    }

    // Обработка окончания перетаскивания карт.
    private void DragCardTouchUp(int screenX_in, int screenY_in)
    {
        WarcastleDuelGame CurrentGame;
        WarcastleCard CurrentWarcastleCard;
        WarcastlePlayer CurrentPlayer;
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
                    }
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
        GamePlaying.EndPlayerTurn((WarcastlePlayer)GamePlaying.getPlayer1());
        SoundList.PlaySound("PapperWrapping");
        ReadCardToScreenCard(GetPlayer1Cards());
        AddTextToStatusBar("End turn");
    }

    // Обработка нажатия кнопок.
    private void ButtonsTouchUp(int screenX_in, int screenY_in)
    {
        if (ButtonPressedCoord(ButtonEndTurn, screenX_in, screenY_in))
        {
            ButtonEndTurnAction();
        }
    }

    // Добавляет в статус-бар текст  text_in.
    private void AddTextToStatusBar(String text_in)
    {
        StatusBarText.poll();
        StatusBarText.add(text_in);
    }
}
