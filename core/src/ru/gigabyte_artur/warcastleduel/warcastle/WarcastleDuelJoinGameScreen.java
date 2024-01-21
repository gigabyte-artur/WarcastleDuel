package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.ScreenCreateGameButton;

public class WarcastleDuelJoinGameScreen  implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private ScreenCreateGameButton ButtonCreatGame;

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

    @Override
    public void show()
    {
        InitScreen();
    }

    @Override
    public void render(float v)
    {
        batch.begin();
        // Рисуем изображение фона на весь экран.
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Кнопка Конец хода.
        ButtonCreatGame.draw(batch,1);
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

    private void InitScreen()
    {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/JoinGameBG.jpg"));
        // Кнопка окончания хода.
        ButtonCreatGame = new ScreenCreateGameButton(400, 70, 400, 100, stage);
//        ButtonCreatGame.setAfterActButton(ActionEndTurn_Finish);
    }

}
