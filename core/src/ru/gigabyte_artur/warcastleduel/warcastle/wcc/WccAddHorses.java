package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastlePlayer;

public class WccAddHorses extends WarcastleCard {

    public WccAddHorses()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = "AddHrs";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 100;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        Player_in.IncrementHorses(1);
    }

}
