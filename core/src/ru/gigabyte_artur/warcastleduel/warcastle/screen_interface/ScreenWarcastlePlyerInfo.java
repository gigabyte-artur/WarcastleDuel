package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastlePlayer;

public class ScreenWarcastlePlyerInfo extends ScreenRectangledElement
{
    private WarcastlePlayer LinkedPlayer;
    private BitmapFont StatsFont;

    public ScreenWarcastlePlyerInfo()
    {
        StatsFont = new BitmapFont();
        StatsFont.setColor(Color.WHITE);
    }

    public void setLinkedPlayer(WarcastlePlayer linkedPlayer)
    {
        LinkedPlayer = linkedPlayer;
    }

    public WarcastlePlayer getLinkedPlayer()
    {
        return this.LinkedPlayer;
    }

    /** Выводит информацию об игроке.*/
    public void draw(SpriteBatch batch)
    {
        int CurrentX, CurrentY;
        CurrentX = this.getX();
        CurrentY = this.getY();
        int ScreenHeight = Gdx.graphics.getHeight();
        StatsFont.draw(batch, "Amount: " + LinkedPlayer.getAmount(), CurrentX, ScreenHeight - (CurrentY + 50));
        StatsFont.draw(batch, "Swords: " + LinkedPlayer.getSwords(), CurrentX + 100, ScreenHeight - (CurrentY + 50));
        StatsFont.draw(batch, "Priests: " + LinkedPlayer.getPriests(), CurrentX +200, ScreenHeight - (CurrentY + 50));
        StatsFont.draw(batch, "Instructors: " + LinkedPlayer.getInstructors(), CurrentX, ScreenHeight - CurrentY);
        StatsFont.draw(batch, "Peasants: " + LinkedPlayer.getPeasants(), CurrentX + 100, ScreenHeight - CurrentY);
        StatsFont.draw(batch, "Horses: " + LinkedPlayer.getHorses(), CurrentX + 200, ScreenHeight - CurrentY);
        StatsFont.draw(batch, "Morale: " + LinkedPlayer.getMorale(), CurrentX + 300, ScreenHeight - CurrentY);
    }
}
