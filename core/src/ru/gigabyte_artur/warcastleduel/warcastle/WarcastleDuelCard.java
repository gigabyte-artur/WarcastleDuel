package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.warcastle.wcc.*;
import java.util.Random;
import java.util.UUID;

public class WarcastleDuelCard extends Card
{

    private int SellPrice;      // Стоимость в золоте.

    public WarcastleDuelCard(String GUID, int sellPrice)
    {
        this.GUID = GUID;
        SellPrice = sellPrice;
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
        String rez = "CardTextures/king.jpg";
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

    public static WarcastleDuelCard GenerateCardByCodeName(String CodeName_in)
    {
        WarcastleDuelCard rez = new WarcastleDuelCard();
        if (CodeName_in.equals(WccAddHorses.GetClassCodeName()))
        {
            rez = new WccAddHorses();
        }
        else if (CodeName_in.equals(WccAddInstructors.GetClassCodeName()))
        {
            rez = new WccAddInstructors();
        }
        else if (CodeName_in.equals(WccAddPeasants.GetClassCodeName()))
        {
            rez = new WccAddPeasants();
        }
        else if (CodeName_in.equals(WccAddSwords.GetClassCodeName()))
        {
            rez = new WccAddSwords();
        }
        else if (CodeName_in.equals(WccAddPriests.GetClassCodeName()))
        {
            rez = new WccAddPriests();
        }
        else if (CodeName_in.equals(WccAttack.GetClassCodeName()))
        {
            rez = new WccAttack();
        }
        else if (CodeName_in.equals(WccCupOfWine.GetClassCodeName()))
        {
            rez = new WccCupOfWine();
        }
        else if (CodeName_in.equals(WccMissionArenaForYoung.GetClassCodeName()))
        {
            rez = new WccMissionArenaForYoung();
        }
        else if (CodeName_in.equals(WccPoisonWell.GetClassCodeName()))
        {
            rez = new WccPoisonWell();
        }
        return rez;
    }
}
