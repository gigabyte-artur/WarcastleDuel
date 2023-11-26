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
    private BitmapFont NameFont;

    public ScreenWarcastlePlyerInfo()
    {
        StatsFont = new BitmapFont();
        StatsFont.setColor(Color.WHITE);
        NameFont = new BitmapFont();
        NameFont.setColor(Color.RED);
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
        NameFont.draw(batch, "Name: " + LinkedPlayer.getName(), CurrentX, ScreenHeight - (CurrentY + 0));
        StatsFont.draw(batch, "Instructors: " + LinkedPlayer.getInstructors(), CurrentX, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Peasants: " + LinkedPlayer.getPeasants(), CurrentX + 100, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Horses: " + LinkedPlayer.getHorses(), CurrentX + 200, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Morale: " + LinkedPlayer.getMorale(), CurrentX + 300, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Amount: " + LinkedPlayer.getAmount(), CurrentX, ScreenHeight - (CurrentY + 60));
        StatsFont.draw(batch, "Swords: " + LinkedPlayer.getSwords(), CurrentX + 100, ScreenHeight - (CurrentY + 60));
        StatsFont.draw(batch, "Priests: " + LinkedPlayer.getPriests(), CurrentX +200, ScreenHeight - (CurrentY + 60));

    }
}
