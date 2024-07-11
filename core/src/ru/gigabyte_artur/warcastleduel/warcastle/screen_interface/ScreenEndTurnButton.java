package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class ScreenEndTurnButton extends ScreenButton
{

    private WarcastleDuelGame GamePlaying;      // Игра, которая играется.

    public ScreenEndTurnButton()
    {
        GamePlaying = new WarcastleDuelGame();
    }

    public ScreenEndTurnButton(WarcastleDuelGame GamePlaying_in, int x_in, int y_in, int width_in, int height_in, Stage Stage_in)
    {
        this();
        SetTextureByPath("Interface/EndTurnButton.png");
        SetDisabledTextureByPath("Interface/EndTurnButton_Disabled.png");
        setGamePlaying(GamePlaying_in);
        setScreensStage(Stage_in);
        setPosition(x_in, y_in);
        setWidth(width_in);
        setHeight(height_in);
    }

    public WarcastleDuelGame getGamePlaying()
    {
        return GamePlaying;
    }

    public void setGamePlaying(WarcastleDuelGame gamePlaying)
    {
        GamePlaying = gamePlaying;
    }

    @Override
    public void ActButton()
    {
        if (!isDisabled())
        {
            WarcastleDuelPlayer Player1 = (WarcastleDuelPlayer) GamePlaying.getPlayer1();
            GamePlaying.EndPlayerTurn(Player1);
        }
        else
        {
            // Кнопка заблокирована. Ничего не делаем.
        }
    }

}
