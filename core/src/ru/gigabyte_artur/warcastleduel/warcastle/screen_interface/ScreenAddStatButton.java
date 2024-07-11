package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;
import static ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer.*;

public class ScreenAddStatButton extends ScreenButton
{
    WarcastleDuelPlayer ChangingPlayer;         // Игрок, чьи статы меняет кнопка.
    int StatId;                             // Id изменяемого стата.

    public void setChangingPlayer(WarcastleDuelPlayer changingPlayer)
    {
        ChangingPlayer = changingPlayer;
    }

    public void setStatId(int statId)
    {
        StatId = statId;
    }

    public int getStatId()
    {
        return StatId;
    }

    public ScreenAddStatButton()
    {
        this.ChangingPlayer = new WarcastleDuelPlayer();
        this.setStatId(STAT_ID_NONE);
    }

    public ScreenAddStatButton(WarcastleDuelPlayer Player_in, int Stat_in, int x_in, int y_in, int width_in, int height_in, Stage Stage_in)
    {
        this();
        SetTextureByPath("Interface/AddButton.png");
        SetDisabledTextureByPath("Interface/AddButton_Disabled.png");
        setStatId(Stat_in);
        setChangingPlayer(Player_in);
        setScreensStage(Stage_in);
        setPosition(x_in, y_in);
        setWidth(width_in);
        setHeight(height_in);
    }

    /** Выполняет действие кнопки.*/
    @Override
    public void ActButton()
    {
//        int CurrStatId = this.getStatId();
//        int Price;
//        Price = ChangingPlayer.PriceStatById(CurrStatId);
//        if (Price <= ChangingPlayer.getAmount())
//        {
//            ChangingPlayer.IncrementStatById(CurrStatId, 1);
//            ChangingPlayer.IncrementAmount(-Price);
//        }
//        else
//        {
//            System.out.println("Not enought amount. Need " + Price + " gold.");
//        }
    }
}
