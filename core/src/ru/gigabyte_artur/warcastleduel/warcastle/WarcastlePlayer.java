package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.gaming.Player;
import ru.gigabyte_artur.warcastleduel.card_game.Hand;

import java.util.ArrayList;

public class WarcastlePlayer extends Player
{

    private int Morale;          // Боевой дух.
    private int Amount;          // Сумма в золоте.
    private int Swords;          // Мечники.
    private int Priests;          // Священники.
    private int Insructors;          // Инструкторы.
    private int Peasants;          // Крестьяне.
    private int Horses;          // Конница.
    private int Armor;              // Броня.
    private Hand PrivateHand;           // Рука игрока.
    private Hand Deck;           // Персональная колода игрока.

    private ArrayList<DefenceBlockDirection> Blocs = new ArrayList<DefenceBlockDirection>();         // Блоки игрока.

    private ArrayList<DefenceBlockDirection> Attacks = new ArrayList<DefenceBlockDirection>();       // Атаки игрока.

    private int ROUNDS_NUMBER = 4;          // Количество раундов атаки.

    public WarcastlePlayer()
    {
        this.Morale = 0;
        this.Amount = 0;
        InitBlocks();
        this.PrivateHand = new Hand();
        this.Deck = new Hand();
        this.Swords = 0;
        this.Priests = 0;
        this.Insructors = 0;
        this.Peasants = 0;
        this.Horses = 0;
        this.Armor = 0;
    }

    public int getMorale()
    {
        return Morale;
    }

    public void setMorale(int morale)
    {
        Morale = morale;
    }

    public int getSwords()
    {
        return Swords;
    }

    public void setSwords(int swords)
    {
        Swords = swords;
    }

    public ArrayList<DefenceBlockDirection> getAttacks()
    {
        return Attacks;
    }

    public ArrayList<DefenceBlockDirection> getBlocs()
    {
        return Blocs;
    }

    // Инициализирует текущего игрока.
    public void Init()
    {
        this.Morale = 0;
        this.Amount = 0;
        InitBlocks();
        this.PrivateHand = new Hand();
        this.Deck = new Hand();
        ArrayList<WarcastleCard> NewDeck = WarcastleDuelGame.GeneratePrivateDeck();
        for (WarcastleCard CurrCard:NewDeck)
        {
            this.Deck.AddCard(CurrCard);
        }
        this.Swords = 0;
        this.Priests = 0;
        this.Insructors = 0;
        this.Peasants = 0;
        this.Horses = 0;
        this.Armor = 0;
    }

    // Инициализирует блоки игрока.
    private void InitBlocks()
    {
        for (int c = 0; c < ROUNDS_NUMBER; c++)
        {
            DefenceBlockDirection NewDefenceDirection = DefenceBlockDirection.NewRandomDefenceDirection();
            this.Blocs.add(NewDefenceDirection);
        }
        for (int c = 0; c < ROUNDS_NUMBER; c++)
        {
            DefenceBlockDirection NewDefenceDirection = DefenceBlockDirection.NewRandomDefenceDirection();
            this.Attacks.add(NewDefenceDirection);
        }
    }

    // Увеличивает количество Мечников текущего игрока на величину Delta_in.
    public void IncrementSwords(int Delta_in)
    {
        this.setSwords(this.getSwords() + Delta_in);
    }

    // Отображает блоки и атаки.
    private void ShowBlocksAttacks()
    {
        String ShownText;
        // Блоки.
        ShownText = "Blocs: ";
        for (DefenceBlockDirection CurrDirection : this.getBlocs())
        {
            ShownText = ShownText + "[";
            ShownText = ShownText + CurrDirection.toString();
            ShownText = ShownText + "] ";
        }
        System.out.println(ShownText);
        // Атаки.
        ShownText = "Attacs: ";
        for (DefenceBlockDirection CurrDirection : this.getAttacks())
        {
            ShownText = ShownText + "[";
            ShownText = ShownText + CurrDirection.toString();
            ShownText = ShownText + "] ";
        }
        System.out.println(ShownText);
    }

    // Отображает текущего игрока.
    public void Show()
    {
        ShowBlocksAttacks();
    }
}
