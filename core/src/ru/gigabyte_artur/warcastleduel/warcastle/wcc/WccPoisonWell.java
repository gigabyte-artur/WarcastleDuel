package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccPoisonWell extends WarcastleDuelCard
{

    public WccPoisonWell()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = "PsnWll";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        WarcastleDuelPlayer PlayerOpponent;
        int Damage = 0;
        PlayerOpponent = (WarcastleDuelPlayer) Game_in.GetOpponent(Player_in);
        Damage = (int)Math.ceil(PlayerOpponent.getMorale() * 0.15);
        if (Damage < 100)
            Damage = 100;
        PlayerOpponent.IncrementMorale(-Damage);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/PoisonWell.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "Poisoned the Well";
        return rez;
    }
}
