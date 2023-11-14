package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private ScreenCard Card1;
    private WarcastleCard PalyersCard1;
    private Texture background;
    private boolean isCardDragged = false;
    private int dragOffsetX, dragOffsetY;
    Sound soundDrawSword;

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("BG.jpg"));
        Card1 = new ScreenCard(PalyersCard1, "king.jpg");
        Gdx.input.setInputProcessor(this);
        soundDrawSword = Gdx.audio.newSound(Gdx.files.internal("DrawSword.ogg"));
    }

    @Override
    public void render(float delta) {
        batch.begin();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Выводим карту.
        Card1.draw(batch);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Card1.getCardSprite().getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY))
        {
            isCardDragged = true;
            dragOffsetX = screenX - (int)Card1.getX();
            dragOffsetY = (int)Card1.getY() - (Gdx.graphics.getHeight() - screenY);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isCardDragged) {
            Card1.setPosition(screenX - dragOffsetX, Gdx.graphics.getHeight() - screenY + dragOffsetY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isCardDragged = false;
        System.out.println("Picked. X: " + screenX + " , Y: " + screenY);
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
