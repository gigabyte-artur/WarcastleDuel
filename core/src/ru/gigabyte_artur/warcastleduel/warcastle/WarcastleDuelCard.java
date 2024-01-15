package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.wcc.*;
import java.util.Random;
import java.util.UUID;

public class WarcastleDuelCard extends Card
{
    private String GUID = "";
    private int SellPrice;      // Стоимость в золоте.

    public String getGUID()
    {
        return GUID;
    }

    public void setGUID(String GUID)
    {
        this.GUID = GUID;
    }

    public WarcastleDuelCard()
    {
        setGUID(UUID.randomUUID().toString());
    }

    public int getSellPrice()
    {
        return SellPrice;
    }

    public void setSellPrice(int price) {
        SellPrice = price;
    }

    /** Возвращает стандартную цену продажи карты. */
    public int getStandardSellPrice()
    {
        int rez = 0;
        return rez;
    }

    /** Вовзращает стандуртный путь к текстуре. */
    public String getStandardTexturePath()
    {
        String rez = "king.jpg";
        return rez;
    }

    /** Возвращает кодовое имя. */
    public String GetCodeName()
    {
        String rez = "<...some card>";
        return rez;
    }

    /** Возвращает тект для вывода в статус-бар при использовании карты. */
    public String GenerateStatusBarTextEffect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        String rez = "<...some card effect>";
        return rez;
    }

    /** Применяет эффект карты. */
    public void Effect(WarcastleDuelGame Game_in, WarcastleDuelPlayer Player_in)
    {
        // Будет переопределено в потомках.
    }

    /** Генерирует случайную карту и возращает её. */
    public static WarcastleDuelCard GenerateRandomCard()
    {
        WarcastleDuelCard rez = new WarcastleDuelCard();
        final Random random = new Random();
        int Generated;
        Generated = random.nextInt(9) + 1;
        if (Generated == 1)
            rez = new WccAddSwords();
        if (Generated == 2)
            rez = new WccAddPriests();
        if (Generated == 3)
            rez = new WccAddInstructors();
        if (Generated == 4)
            rez = new WccAddPeasants();
        if (Generated == 5)
            rez = new WccAddHorses();
        if (Generated == 6)
            rez = new WccMissionArenaForYoung();
        if (Generated == 7)
            rez = new WccAttack();
        if (Generated == 8)
            rez = new WccCupOfWine();
        if (Generated == 9)
            rez = new WccPoisonWell();
        return rez;
    }

    /** Выводит карту в консоль. */
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
