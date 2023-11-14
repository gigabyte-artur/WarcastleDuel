package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ArrayList<ScreenCard> ScreenCards;
    private ArrayList<WarcastleCard> PlayersCards;
    private Texture background;
    private boolean isCardDragged = false;
    private int dragOffsetX, dragOffsetY;
    Sound soundDrawSword;

    public void setPlayersCards(ArrayList<WarcastleCard> playersCards)
    {
        PlayersCards = playersCards;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("BG.jpg"));
        ScreenCards = new ArrayList<>();
        int counter;
        ScreenCard NewScreenCard;
        counter = 1;
        for (WarcastleCard curr_card:PlayersCards)
        {
            NewScreenCard = new ScreenCard(curr_card, "king.jpg");
            NewScreenCard.setPosition(counter * 100, 100);
            ScreenCards.add(NewScreenCard);
            counter = counter + 1;
        }
        Gdx.input.setInputProcessor(this);
        soundDrawSword = Gdx.audio.newSound(Gdx.files.internal("DrawSword.ogg"));
    }

    @Override
    public void render(float delta) {
        batch.begin();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Выводим карту.
        for (ScreenCard curr_card:ScreenCards)
        {
            curr_card.draw(batch);
        }
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (ScreenCard curr_card:ScreenCards)
        {
            curr_card.setCardDragged(false);
            System.out.println("Picked. X: " + screenX + " , Y: " + screenY);
        }
        soundDrawSword.play();
        soundDrawSword.resume();
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

}
