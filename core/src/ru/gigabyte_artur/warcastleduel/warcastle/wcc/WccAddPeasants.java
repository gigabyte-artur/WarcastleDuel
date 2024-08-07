package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccAddPeasants extends WarcastleDuelCard
{

    public WccAddPeasants()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    /** Возвращает текстовое значение кодового имени класса.*/
    public static String GetClassCodeName()
    {
        String rez = "AddPsnts";
        return rez;
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = WccAddPeasants.GetClassCodeName();
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        Player_in.IncrementPeasants(1);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/peasant.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "Added 1 peasant";
        return rez;
    }
}
