package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccCupOfWine extends WarcastleDuelCard
{

    public WccCupOfWine()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    /** Возвращает кодовое имя. */
    @Override
    public String GetCodeName()
    {
        String rez = "CpWn";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        Player_in.IncrementMorale(1000);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/CupOfWine.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "Added 1000 morale";
        return rez;
    }
}