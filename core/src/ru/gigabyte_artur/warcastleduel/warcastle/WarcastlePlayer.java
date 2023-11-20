package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.gaming.Player;
import ru.gigabyte_artur.warcastleduel.card_game.Hand;

import java.util.ArrayList;

public class WarcastlePlayer extends Player
{

    private int Morale;          // Боевой дух.
    private int Amount;          // Сумма в золоте.
    private int Swords;          // Мечники.
    private int Priests;          // Священники.
    private int Instructors;          // Инструкторы.
    private int Peasants;          // Крестьяне.
    private int Horses;          // Конница.
    private int Armor;              // Броня.
    private Hand PrivateHand;           // Рука игрока.
    private Hand Deck;           // Персональная колода игрока.
    private Hand DiscardHand;       // Сброс.

    private ArrayList<DefenceBlockDirection> Blocs = new ArrayList<DefenceBlockDirection>();         // Блоки игрока.
    private ArrayList<DefenceBlockDirection> Attacks = new ArrayList<DefenceBlockDirection>();       // Атаки игрока.

    private int ROUNDS_NUMBER = 4;                // Количество раундов атаки.
    private int START_MORALE = 10000;             // Начальное количество боевого духа.
    private static final int TAX_RATES = 100;    // Минимальная величина налога.

    public WarcastlePlayer()
    {
        this.Morale = 0;
        this.Amount = 0;
        InitBlocks();
        this.PrivateHand = new Hand();
        this.Deck = new Hand();
        this.Swords = 0;
        this.Priests = 0;
        this.Instructors = 0;
        this.Peasants = 0;
        this.Horses = 0;
        this.Armor = 0;
    }

    public int getAmount()
    {
        return Amount;
    }

    public void setAmount(int amount)
    {
        Amount = amount;
    }

    public int getArmor()
    {
        return Armor;
    }

    public int getMorale()
    {
        return Morale;
    }

    public int getPriests()
    {
        return Priests;
    }

    public int getInstructors()
    {
        return Instructors;
    }

    public int getPeasants()
    {
        return Peasants;
    }

    public int getHorses()
    {
        return Horses;
    }

    public Hand getPrivateHand()
    {
        return PrivateHand;
    }

    public void setPriests(int priests) {
        Priests = priests;
    }

    public void setInstructors(int insructors) {
        Instructors = insructors;
    }

    public void setHorses(int horses) {
        Horses = horses;
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

    public void setPeasants(int peasants)
    {
        Peasants = peasants;
    }

    public ArrayList<DefenceBlockDirection> getAttacks()
    {
        return Attacks;
    }

    public ArrayList<DefenceBlockDirection> getBlocs()
    {
        return Blocs;
    }

    public Hand getDeck()
    {
        return Deck;
    }

    // Инициализирует текущего игрока.
    public void Init()
    {
        this.Morale = START_MORALE;
        this.Amount = 0;
        InitBlocks();
        this.PrivateHand = new Hand();
        this.Deck = new Hand();
        this.DiscardHand = new Hand();
        ArrayList<WarcastleCard> NewDeck = WarcastleDuelGame.GeneratePrivateDeck();
        for (WarcastleCard CurrCard:NewDeck)
        {
            this.Deck.AddCard(CurrCard);
        }
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.Swords      = 1;
        this.Priests     = 1;
        this.Instructors  = 1;
        this.Peasants    = 1;
        this.Horses      = 1;
        this.Armor       = 0;
    }

    // Инициализирует блоки игрока.
    private void InitBlocks()
    {
        this.Blocs.clear();
        for (int c = 0; c < ROUNDS_NUMBER; c++)
        {
            DefenceBlockDirection NewDefenceDirection = DefenceBlockDirection.NewRandomDefenceDirection();
            this.Blocs.add(NewDefenceDirection);
        }
        this.Attacks.clear();
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

    // Увеличивает количество Священников текущего игрока на величину Delta_in.
    public void IncrementPriests(int Delta_in)
    {
        this.setPriests(this.getPriests() + Delta_in);
    }

    // Увеличивает количество Крестьян текущего игрока на величину Delta_in.
    public void IncrementPeasants(int Delta_in)
    {
        this.setPeasants(getPeasants() + Delta_in);
    }

    // Увеличивает количество Инструкторов текущего игрока на величину Delta_in.
    public void IncrementInstructors(int Delta_in)
    {
        this.setInstructors(this.getInstructors() + Delta_in);
    }

    // Увеличивает количество Конницы текущего игрока на величину Delta_in.
    public void IncrementHorses(int Delta_in)
    {
        this.setHorses(this.getHorses() + Delta_in);
    }

    // Увеличивает количество Золота текущего игрока на величину Delta_in.
    public void IncrementAmount(int Delta_in)
    {
        this.setAmount(this.getAmount() + Delta_in);
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
        String ShownText;
        // Аттрибуты.
        ShownText = "Morale: ";
        ShownText = ShownText + getMorale();
        ShownText = ShownText + " Armor: ";
        ShownText = ShownText + getArmor();
        ShownText = ShownText + " Amount: ";
        ShownText = ShownText + getAmount();
        System.out.println(ShownText);
        // Блоки.
        ShowBlocksAttacks();
        // Статы.
        ShownText = "Swords: ";
        ShownText = ShownText + getSwords();
        ShownText = ShownText + "   Priests: ";
        ShownText = ShownText + getPriests();
        ShownText = ShownText + "   Insructors: ";
        ShownText = ShownText + getInstructors();
        ShownText = ShownText + "   Peasants: ";
        ShownText = ShownText + getPeasants();
        ShownText = ShownText + "   Horses: ";
        ShownText = ShownText + getHorses();
        System.out.println(ShownText);
        // Карты.
        ShownText = "Cards: ";
        System.out.println(ShownText);
        for (Card CurrCard : this.getPrivateHand().getCards())
        {
            CurrCard.Show();
        }
    }

    // Выносит карту Card_in из руки в сброс.
    public void PrivateHandCardToDiscard(Card Card_in)
    {
        Hand PrivateHand = this.getPrivateHand();
        PrivateHand.MoveCard(Card_in, this.DiscardHand);
    }

    // Вычисляет величину налогов.
    public int CalculateTaxes()
    {
        int rez = 0;
        rez = this.getPeasants() * TAX_RATES;
        return rez;
    }
}
