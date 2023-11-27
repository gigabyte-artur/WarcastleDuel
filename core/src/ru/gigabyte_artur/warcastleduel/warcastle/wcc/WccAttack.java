package ru.gigabyte_artur.warcastleduel.warcastle.wcc;

import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelGame;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastlePlayer;

public class WccAttack extends WarcastleCard {

    public WccAttack()
    {
        this.setSellPrice(this.getStandardSellPrice());
    }

    // Возвращает кодовое имя.
    @Override
    public String GetCodeName()
    {
        String rez = "Attck";
        return rez;
    }

    @Override
    public int getStandardSellPrice() {
        return 300;
    }

    @Override
    public void Effect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        Player_in.IncrementHorses(1);
    }

    @Override
    public String getStandardTexturePath()
    {
        return "CardTextures/attack.jpg";
    }

    @Override
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        String rez = "Player " + Player_in.getName() + " attacked enemy.";
        return rez;
    }
}