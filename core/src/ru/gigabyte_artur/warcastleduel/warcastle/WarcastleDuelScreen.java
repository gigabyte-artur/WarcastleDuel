package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gigabyte_artur.warcastleduel.card_game.Card;
import java.util.ArrayList;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ScreenCard PrivateDeckCover;
    private Texture background;
    private int dragOffsetX, dragOffsetY;
    private Sound soundDrawSword;
    private WarcastleDuelGame GamePlaying;
    private BitmapFont StatsFont;
    private BitmapFont DeckFont;

    public void setGamePlaying(WarcastleDuelGame game1)
    {
        GamePlaying = game1;
    }

    public WarcastleDuelGame getGamePlaying()
    {
        return GamePlaying;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("BG.jpg"));
        ScreenCards = new ArrayList<>();
        int counter;
        ScreenCard NewScreenCard;
        counter = 1;
        for (WarcastleCard curr_card:GetPlayer1Cards())
        {
            NewScreenCard = new ScreenCard(curr_card, curr_card.getStandardTexturePath());
            NewScreenCard.setPosition(counter * 100, 100);
            NewScreenCard.setCovered(false);
            ScreenCards.add(NewScreenCard);
            counter = counter + 1;
        }
        Gdx.input.setInputProcessor(this);
        soundDrawSword = Gdx.audio.newSound(Gdx.files.internal("DrawSword.ogg"));
        PrivateDeckCover = new ScreenCard();
        PrivateDeckCover.setPosition(600, 100);
        PrivateDeckCover.setDimensions(ScreenCard.STANDARD_WIDTH,ScreenCard.STANDARD_HEIGHT);
        PrivateDeckCover.setCovered(true);
        StatsFont = new BitmapFont();
        StatsFont.setColor(Color.WHITE); // Устанавливаем цвет текста
        DeckFont = new BitmapFont();
        DeckFont.setColor(Color.BROWN); // Устанавливаем цвет текста
    }

    @Override
    public void render(float delta) {
        WarcastlePlayer CurrentPlayer;
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        WarcastleDuelGame CurrentGame;
        WarcastleCard CurrentWarcastleCard;
        WarcastlePlayer CurrentPlayer;
        ScreenCard FoundCard = new ScreenCard();
        boolean isCardFound = false;
        // Поиск перетаскиваемых карт и выполнение действий.
        for (ScreenCard curr_card:ScreenCards)
        {
            if (curr_card.isCardDragged())
            {
                if (Gdx.graphics.getHeight() - screenY > 150)
                {
                    CurrentWarcastleCard = (WarcastleCard)curr_card.getLinkedCard();
                    if (CurrentWarcastleCard != null)
                    {
                        CurrentGame = this.getGamePlaying();
                        CurrentPlayer = (WarcastlePlayer)CurrentGame.getPlayer1();
                        CurrentWarcastleCard.Effect(CurrentGame, CurrentPlayer);
                        CurrentPlayer.PrivateHandCardToDiscard(CurrentWarcastleCard);
                        soundDrawSword.play();
                        soundDrawSword.resume();
                        isCardFound = true;
                        FoundCard = curr_card;
                    }
                }
                break;
            }
        }
        if (isCardFound)
        {
            this.ScreenCards.remove(FoundCard);
        }
        // Очистка перетаскиваемых карт.
        for (ScreenCard curr_card:ScreenCards)
        {
            curr_card.setCardDragged(false);
        }
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
}
