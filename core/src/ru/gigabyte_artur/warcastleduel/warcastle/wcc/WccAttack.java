package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccAttack extends WarcastleDuelCard
{

    public WccAttack()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    /** Возвращает текстовое значение кодового имени класса.*/
    public static String GetClassCodeName()
    {
        String rez = "Attck";
        return rez;
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = WccAttack.GetClassCodeName();
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 300;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        WarcastleDuelPlayer PlayerOpponent;
        PlayerOpponent = (WarcastleDuelPlayer) Game_in.GetOpponent(Player_in);
        Game_in.AttackPlayer(Player_in, PlayerOpponent);
        PlayerOpponent.CalculateStats();
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/attack.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        WarcastleDuelPlayer PlayerOpponent;
        PlayerOpponent = (WarcastleDuelPlayer) Game_in.GetOpponent(Player_in);
        String rez = "Player " + Player_in.getName() + " attacked enemy " + PlayerOpponent.getName() + ".";
        return rez;
    }
}