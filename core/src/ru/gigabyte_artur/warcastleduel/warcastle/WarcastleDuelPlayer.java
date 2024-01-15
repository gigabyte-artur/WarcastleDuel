package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Card;
import ru.gigabyte_artur.warcastleduel.gaming.Player;
import ru.gigabyte_artur.warcastleduel.card_game.Hand;

import java.util.ArrayList;
import java.util.UUID;

public class WarcastleDuelPlayer extends Player
{
    private String GUID = "";
    private int Morale;                  // Боевой дух (мораль).
    private int Amount;                  // Сумма в золоте.
    private int Swords;                  // Мечники.
    private int Priests;                 // Священники.
    private int Instructors;             // Инструкторы.
    private int Peasants;                // Крестьяне.
    private int Horses;                  // Конница.
    private Hand PrivateHand;            // Рука игрока.
    private Hand Deck;                   // Персональная колода игрока.
    private Hand DiscardHand;            // Сброс.
    private int SwordAttack;             // Атака мечников.
    private int HorseAttack;             // Атака конницы.
    private int Armor;                   // Броня.
    private int SwordSkill;              // Владение мечом.
    private int Dodge;                   // Уворот.
    private int Luck;                    // Удача.
    private ArrayList<WarcastleDuelDefenceBlockDirection> Blocs = new ArrayList<WarcastleDuelDefenceBlockDirection>();         // Блоки игрока.
    private ArrayList<WarcastleDuelDefenceBlockDirection> Attacks = new ArrayList<WarcastleDuelDefenceBlockDirection>();       // Атаки игрока.
    private int ROUNDS_NUMBER = 4;                // Количество раундов атаки.
    private int START_MORALE = 10000;             // Начальное количество боевого духа.

    private static final int TAX_RATES = 100;    // Минимальная величина налога.
    public static final int MAX_CARDS_HAND = 9;              // Максимальное число карт в руке.
    public final static int STAT_ID_NONE = 0;
    public final static int STAT_ID_SWORD = 1;
    public final static int STAT_ID_PRIEST = 2;
    public final static int STAT_ID_INSTRUCTOR = 3;
    public final static int STAT_ID_PEASANT = 4;
    public final static int STAT_ID_HORSE = 5;

    public WarcastleDuelPlayer()
    {
        setGUID(UUID.randomUUID().toString());
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

    public String getGUID()
    {
        return GUID;
    }

    public void setGUID(String GUID)
    {
        this.GUID = GUID;
    }

    public int getSwordAttack()
    {
        return SwordAttack;
    }

    public int getHorseAttack()
    {
        return HorseAttack;
    }

    public int getSwordSkill()
    {
        return SwordSkill;
    }

    public int getDodge()
    {
        return Dodge;
    }

    public int getLuck()
    {
        return Luck;
    }

    public void setSwordAttack(int swordAttack)
    {
        SwordAttack = swordAttack;
    }

    public void setHorseAttack(int horseAttack)
    {
        HorseAttack = horseAttack;
    }

    public void setArmor(int armor)
    {
        Armor = armor;
    }

    public void setSwordSkill(int swordSkill)
    {
        SwordSkill = swordSkill;
    }

    public void setDodge(int dodge)
    {
        Dodge = dodge;
    }

    public void setLuck(int luck)
    {
        Luck = luck;
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
        CalculateStats();
    }

    public void setInstructors(int insructors) {
        Instructors = insructors;
        CalculateStats();
    }

    public void setHorses(int horses) {
        Horses = horses;
        CalculateStats();
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
        CalculateStats();
    }

    public void setPeasants(int peasants)
    {
        Peasants = peasants;
        CalculateStats();
    }

    public ArrayList<WarcastleDuelDefenceBlockDirection> getAttacks()
    {
        return Attacks;
    }

    public ArrayList<WarcastleDuelDefenceBlockDirection> getBlocs()
    {
        return Blocs;
    }

    public Hand getDeck()
    {
        return Deck;
    }

    // Возвращает текущее число карт, которое может держать текущий игрок в руке.
    public int GetMaxCardHand()
    {
        return MAX_CARDS_HAND;
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
        ArrayList<WarcastleDuelCard> NewDeck = WarcastleDuelGame.GeneratePrivateDeck();
        for (WarcastleDuelCard CurrCard:NewDeck)
        {
            this.Deck.AddCard(CurrCard);
        }
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.getPrivateHand().DrawCard(this.getDeck());
        this.Swords      = 1;
        this.Priests     = 1;
        this.Instructors = 1;
        this.Peasants    = 1;
        this.Horses      = 1;
        this.Armor       = 0;
        CalculateStats();
    }

    // Инициализирует блоки игрока.
    private void InitBlocks()
    {
        this.Blocs.clear();
        for (int c = 0; c < ROUNDS_NUMBER; c++)
        {
            WarcastleDuelDefenceBlockDirection NewDefenceDirection = WarcastleDuelDefenceBlockDirection.NewRandomDefenceDirection();
            this.Blocs.add(NewDefenceDirection);
        }
        this.Attacks.clear();
        for (int c = 0; c < ROUNDS_NUMBER; c++)
        {
            WarcastleDuelDefenceBlockDirection NewDefenceDirection = WarcastleDuelDefenceBlockDirection.NewRandomDefenceDirection();
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

    // Увеличивает боевой бух текущего игрока на величину Delta_in.
    public void IncrementMorale(int Delta_in)
    {
        this.setMorale(this.getMorale() + Delta_in);
    }

    // Увеличивает количество Золота текущего игрока на величину Delta_in.
    public void IncrementAmount(int Delta_in)
    {
        this.setAmount(this.getAmount() + Delta_in);
    }

    /** Отображает блоки и атаки. */
    private void ShowBlocksAttacks()
    {
        String ShownText;
        // Блоки.
        ShownText = "Blocs: ";
        for (WarcastleDuelDefenceBlockDirection CurrDirection : this.getBlocs())
        {
            ShownText = ShownText + "[";
            ShownText = ShownText + CurrDirection.toString();
            ShownText = ShownText + "] ";
        }
        System.out.println(ShownText);
        // Атаки.
        ShownText = "Attacs: ";
        for (WarcastleDuelDefenceBlockDirection CurrDirection : this.getAttacks())
        {
            ShownText = ShownText + "[";
            ShownText = ShownText + CurrDirection.toString();
            ShownText = ShownText + "] ";
        }
        System.out.println(ShownText);
    }

    /** Отображает текущего игрока. */
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

    /** Выносит карту Card_in из руки в сброс. */
    public void PrivateHandCardToDiscard(Card Card_in)
    {
        Hand PrivateHand = this.getPrivateHand();
        PrivateHand.MoveCard(Card_in, this.DiscardHand);
    }

    /** Отправляет в сброс ("сжигает") верхнюю карту в колоде. */
    public void BurnTopCardInDeck()
    {
        Card BurnedCard;
        if (this.getDeck().Size() > 0)
        {
            BurnedCard = this.getDeck().GetCardById(0);
            PrivateHand.MoveCard(BurnedCard, this.DiscardHand);
        }
    }

    /** Вычисляет величину налогов. */
    public int CalculateTaxes()
    {
        int rez = 0;
        rez = this.getPeasants() * TAX_RATES;
        return rez;
    }

    /** Рассчитывает статы текущего игрока.*/
    public void CalculateStats()
    {
        int NewStat;
        // Атака мечников.
        NewStat = (int)Math.ceil((this.getSwords()*1.25 + this.getPeasants()/2.0)*100);
        this.setSwordAttack(NewStat);
        // Атакак конницы.
        NewStat = (int)Math.ceil((this.getHorses()*5 + this.getPeasants()/10.0)*10);
        this.setHorseAttack(NewStat);
        // Удача.
        NewStat = (int)Math.ceil(this.getPriests()*10);
        this.setLuck(NewStat);
        // Уворот.
        NewStat = (int)Math.ceil((this.getInstructors() + this.getLuck()/2.0 + this.getPeasants()/5.0)*10);
        this.setDodge(NewStat);
        // Владение мечом.
        NewStat = (int)Math.ceil((this.getSwords()/10.0 + (this.getInstructors()*2.0 + this.getSwords())/2.0 + this.getLuck()/2.0 + this.getPeasants()/5.0)*10);
        this.setSwordSkill(NewStat);
        // Броня.
        NewStat = (int)Math.ceil(0 * 30);
        this.setArmor(NewStat);
    }

    /** Возвращает стоимость покупки Мечников.*/
    public int SwordPrice()
    {
        int rez = 0;
        rez = getSwords()*getSwords()*100;
        return rez;
    }

    /** Возвращает стоимость покупки Священников.*/
    public int PriestPrice()
    {
        int rez = 0;
        rez = getPriests()*getPriests()*100;
        return rez;
    }

    /** Возвращает стоимость покупки Инструкторов.*/
    public int InstructorPrice()
    {
        int rez = 0;
        rez = getInstructors()*getInstructors()*100;
        return rez;
    }

    /** Возвращает стоимость покупки Крестьян.*/
    public int PeasantPrice()
    {
        int rez = 0;
        rez = getPeasants()*getPeasants()*100;
        return rez;
    }

    /** Возвращает стоимость покупки Конницы.*/
    public int HorsePrice()
    {
        int rez = 0;
        rez = getHorses()*getHorses()*100;
        return rez;
    }

    /** Увеличиает стат StatId_in на величину Delta_in.*/
    public void IncrementStatById(int StatId_in, int Delta_in)
    {
        if (StatId_in == STAT_ID_SWORD)
            IncrementSwords(Delta_in);
        if (StatId_in == STAT_ID_PRIEST)
            IncrementPriests(Delta_in);
        if (StatId_in == STAT_ID_INSTRUCTOR)
            IncrementInstructors(Delta_in);
        if (StatId_in == STAT_ID_PEASANT)
            IncrementPeasants(Delta_in);
        if (StatId_in == STAT_ID_HORSE)
            IncrementHorses(Delta_in);
    }

    /** Вовзвращает стоимость покупки стата StatId_in.*/
    public int PriceStatById(int StatId_in)
    {
        int rez = 0;
        if (StatId_in == STAT_ID_SWORD)
            rez = SwordPrice();
        if (StatId_in == STAT_ID_PRIEST)
            rez = PriestPrice();
        if (StatId_in == STAT_ID_INSTRUCTOR)
            rez = InstructorPrice();
        if (StatId_in == STAT_ID_PEASANT)
            rez = PeasantPrice();
        if (StatId_in == STAT_ID_HORSE)
            rez = HorsePrice();
        return rez;
    }
}
