package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastlePlayer;

public class WccAddInstructors extends WarcastleCard {

    public WccAddInstructors()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = "AddIntsrcs";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        Player_in.IncrementInstructors(1);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "instructor.jpg";
    }
}
