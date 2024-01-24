package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnClient;
import ru.gigabyte_artur.warcastleduel.warcastle.net.WcnXmlBuilder;
import ru.gigabyte_artur.warcastleduel.warcastle.screen_interface.ScreenCreateGameButton;

public class WarcastleDuelJoinGameScreen  implements Screen, InputProcessor
{
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private ScreenCreateGameButton ButtonCreatGame;
    private WarcastleDuelApplication MainApplication;
    private WcnClient Client1;

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

    public WarcastleDuelJoinGameScreen(WarcastleDuelApplication mainApplication)
    {
        MainApplication = mainApplication;
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

    @Override
    public void show()
    {
        try
        {
            InitScreen();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
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

    private void InitScreen() throws Exception
    {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(this);
        background = new Texture(Gdx.files.internal("Interface/JoinGameBG.jpg"));
        // Кнопка окончания хода.
        ButtonCreatGame = new ScreenCreateGameButton(400, 70, 400, 100, stage);
        ButtonCreatGame.setAfterActButton(ActionCreateGame_Finish);
        // Клиент.
        Client1 = new WcnClient("localhost", 27960,27960);
        Client1.StartClient();
    }

    private void CreateGame_Finish()
    {
        String CreateGameXML = WcnXmlBuilder.GenerateCreateGame("Lucas");
        Client1.SendStringMessage(CreateGameXML);
        this.hide();
        WarcastleDuelScreen GameScreen = new WarcastleDuelScreen();
        WarcastleDuelGame Game1 = new WarcastleDuelGame();
        Game1.Init();
        GameScreen.setGamePlaying(Game1);
        MainApplication.setScreen(GameScreen);
    }

}
