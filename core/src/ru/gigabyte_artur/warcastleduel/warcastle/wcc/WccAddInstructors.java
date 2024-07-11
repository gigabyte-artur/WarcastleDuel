package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelPlayer;

public class WccAddInstructors extends WarcastleDuelCard
{

    public WccAddInstructors()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    /** Возвращает текстовое значение кодового имени класса.*/
    public static String GetClassCodeName()
    {
        String rez = "AddIntsrcs";
        return rez;
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = WccAddInstructors.GetClassCodeName();
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        Player_in.IncrementInstructors(1);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/instructor.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "Added 1 instructors";
        return rez;
    }
}
