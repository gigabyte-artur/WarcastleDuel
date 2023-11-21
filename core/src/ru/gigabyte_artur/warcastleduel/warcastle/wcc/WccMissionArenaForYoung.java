package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastlePlayer;

public class WccMissionArenaForYoung extends WarcastleCard {

    private final static int DELTA_GOLD = 100;

    public WccMissionArenaForYoung()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = "MssnArnFrYng";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        Player_in.IncrementAmount(DELTA_GOLD);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/ArenaYoung.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        String rez = "Added " + DELTA_GOLD + " gold";
        return rez;
    }
}
