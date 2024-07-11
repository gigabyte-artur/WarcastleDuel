package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScreenJoinGameButton extends ScreenButton
{

    public ScreenJoinGameButton(int x_in, int y_in, int width_in, int height_in, Stage Stage_in)
    {
        SetTextureByPath("Interface/JoinGameButton.png");
        setScreensStage(Stage_in);
        setPosition(x_in, y_in);
        setWidth(width_in);
        setHeight(height_in);
    }

    @Override
    public void ActButton()
    {

    }
}
