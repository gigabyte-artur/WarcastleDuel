package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.wcc.*;

import java.util.Random;

public class WarcastleCard extends Card
{

    private int SellPrice;      // Стоимость в золоте.

    public int getSellPrice()
    {
        return SellPrice;
    }

    public void setSellPrice(int price) {
        SellPrice = price;
    }

    // Возвращает стандартную цену продажи карты.
    public int getStandardSellPrice()
    {
        int rez = 0;
        return rez;
    }

    // Вовзращает стандуртный путь к текстуре.
    public String getStandardTexturePath()
    {
        String rez = "king.jpg";
        return rez;
    }

    // Возвращает кодовое имя.
    public String GetCodeName()
    {
        String rez = "<...some card>";
        return rez;
    }

    // Применяет эффект карты.
    public void Effect(WarcastleDuelGame Game_in, WarcastlePlayer Player_in)
    {
        // Будет переопределено в потомках.
    }

    public static WarcastleCard GenerateRandomCard()
    {
        WarcastleCard rez = new WarcastleCard();
        final Random random = new Random();
        int Generated;
        Generated = random.nextInt(6) + 1;
        if (Generated == 1)
        {
            rez = new WccAddSwords();
        }
        if (Generated == 2)
        {
            rez = new WccAddPriests();
        }
        if (Generated == 3)
        {
            rez = new WccAddInstructors();
        }
        if (Generated == 4)
        {
            rez = new WccAddPeasants();
        }
        if (Generated == 5)
        {
            rez = new WccAddHorses();
        }
        if (Generated == 6)
        {
            rez = new WccMissionArenaForYoung();
        }
        return rez;
    }

    @Override
    public void Show()
    {
        String ShownText = "";
        ShownText = ShownText + this.GetCodeName();
        ShownText = ShownText + "[";
        ShownText = ShownText + this.getSellPrice();
        ShownText = ShownText + "]";
        System.out.println(ShownText);
    }
}
