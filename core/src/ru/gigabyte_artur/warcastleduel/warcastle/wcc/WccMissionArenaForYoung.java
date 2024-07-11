package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccMissionArenaForYoung extends WarcastleDuelCard
{

    private final static int DELTA_GOLD = 100;

    public WccMissionArenaForYoung()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    /** Возвращает текстовое значение кодового имени класса.*/
    public static String GetClassCodeName()
    {
        String rez = "MssnArnFrYng";
        return rez;
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = WccMissionArenaForYoung.GetClassCodeName();
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        Player_in.IncrementAmount(DELTA_GOLD);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/ArenaYoung.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "Added " + DELTA_GOLD + " gold";
        return rez;
    }
}
