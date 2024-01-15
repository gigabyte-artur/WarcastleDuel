package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class ScreenWarcastlePlyerInfo extends ScreenRectangledElement
{
    private WarcastleDuelPlayer LinkedPlayer;
    private BitmapFont StatsFont;
    private BitmapFont NameFont;

    public ScreenWarcastlePlyerInfo()
    {
        StatsFont = new BitmapFont();
        StatsFont.setColor(Color.WHITE);
        NameFont = new BitmapFont();
        NameFont.setColor(Color.RED);
    }

    public void setLinkedPlayer(WarcastleDuelPlayer linkedPlayer)
    {
        LinkedPlayer = linkedPlayer;
    }

    public WarcastleDuelPlayer getLinkedPlayer()
    {
        return this.LinkedPlayer;
    }

    /** Выводит информацию об игроке.*/
    public void draw(SpriteBatch batch)
    {
        // Инициализация.
        int CurrentX, CurrentY;
        CurrentX = this.getX();
        CurrentY = this.getY();
        int ScreenHeight = Gdx.graphics.getHeight();
        // Строка имени.
        NameFont.draw(batch, "Name: " + LinkedPlayer.getName(), CurrentX, ScreenHeight - (CurrentY + 0));
        StatsFont.draw(batch, "Morale: " + LinkedPlayer.getMorale(), CurrentX + 120, ScreenHeight - (CurrentY + 0));
        StatsFont.draw(batch, "Amount: " + LinkedPlayer.getAmount(), CurrentX + 240, ScreenHeight - (CurrentY + 0));
        // Первая строка.
        // -Войска.
        StatsFont.draw(batch, "Swords: " + LinkedPlayer.getSwords(), CurrentX, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Priests: " + LinkedPlayer.getPriests(), CurrentX + 120, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Instructors: " + LinkedPlayer.getInstructors(), CurrentX + 240, ScreenHeight - (CurrentY + 30));
        // -Статы.
        StatsFont.draw(batch, "Attack: " + LinkedPlayer.getSwordAttack(), CurrentX + 450, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Armor: " + LinkedPlayer.getArmor(), CurrentX + 570, ScreenHeight - (CurrentY + 30));
        StatsFont.draw(batch, "Skill: " + LinkedPlayer.getSwordSkill(), CurrentX + 690, ScreenHeight - (CurrentY + 30));
        // Вторая строка.
        // -Войска.
        StatsFont.draw(batch, "Peasants: " + LinkedPlayer.getPeasants(), CurrentX, ScreenHeight - (CurrentY + 60));
        StatsFont.draw(batch, "Horses: " + LinkedPlayer.getHorses(), CurrentX + 120, ScreenHeight - (CurrentY + 60));
        // -Статы.
        StatsFont.draw(batch, "Horse Attack: " + LinkedPlayer.getHorseAttack(), CurrentX + 450, ScreenHeight - (CurrentY + 60));
        StatsFont.draw(batch, "Dodge: " + LinkedPlayer.getDodge(), CurrentX + 570, ScreenHeight - (CurrentY + 60));
        StatsFont.draw(batch, "Luck: " + LinkedPlayer.getLuck(), CurrentX + 690, ScreenHeight - (CurrentY + 60));
    }
}
