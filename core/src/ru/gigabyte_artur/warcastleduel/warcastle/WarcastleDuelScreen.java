package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WarcastleDuelScreen implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private Texture cardTexture;
    private Sprite cardSprite;
    private boolean isCardDragged = false;
    private int dragOffsetX, dragOffsetY;
    Sound soundDrawSword;

    @Override
    public void show() {
        batch = new SpriteBatch();
        cardTexture = new Texture(Gdx.files.internal("king.jpg"));
        cardSprite = new Sprite(cardTexture);
        cardSprite.setPosition(100, 100);
        Gdx.input.setInputProcessor(this);
        soundDrawSword = Gdx.audio.newSound(Gdx.files.internal("DrawSword.ogg"));
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Остальной код отрисовки экрана
        batch.begin();
        cardSprite.draw(batch);
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
        if (cardSprite.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY))
        {
            isCardDragged = true;
            dragOffsetX = screenX - (int)cardSprite.getX();
            dragOffsetY = (int)cardSprite.getY() - (Gdx.graphics.getHeight() - screenY);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isCardDragged) {
            cardSprite.setPosition(screenX - dragOffsetX, Gdx.graphics.getHeight() - screenY + dragOffsetY);
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
