package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Hand;
import ru.gigabyte_artur.warcastleduel.gaming.TwoPlayersGame;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class WarcastleDuelGame extends TwoPlayersGame
{

    private String GUID = "";
    private static final int CARD_IN_PRIVATE_DECK = 30;        // Сколько корт в колоде по умолчанию.

    public WarcastleDuelGame()
    {
        setGUID(UUID.randomUUID().toString());
    }

    public String getGUID()
    {
        return GUID;
    }

    public void setGUID(String GUID)
    {
        this.GUID = GUID;
    }

    @Override
    public int Play()
    {
        return 0;
    }

    @Override
    public void Init()
    {
        WarcastlePlayer Player1 = new WarcastlePlayer();
        Player1.Init();
        CollectTaxes(Player1);
        WarcastlePlayer Player2 = new WarcastlePlayer();
        Player2.Init();
        this.SetPlayers(Player1, Player2);
        CollectTaxes(Player2);
    }

    @Override
    public WarcastlePlayer NewPlayer()
    {
        WarcastlePlayer rez = new WarcastlePlayer();
        return rez;
    }

    @Override
    public int GetStandardNumberOfPlayers()
    {
        return 2;
    }

    /** Начинает ход игрока Player_in. */
    private void BeginPlayerTurn(WarcastlePlayer Player_in)
    {
        Hand PlayersDeck, PlayersHand;
        PlayersDeck  = Player_in.getDeck();
        PlayersHand  = Player_in.getPrivateHand();
        if (PlayersHand.Size() < Player_in.GetMaxCardHand())
        {
            PlayersHand.DrawCard(PlayersDeck);
        }
        else
        {
            Player_in.BurnTopCardInDeck();
        }
        CollectTaxes(Player_in);
    }

    /** Осуществляет сбор налогов игроком Player_in. */
    public void CollectTaxes(WarcastlePlayer Player_in)
    {
        int NewTaxes;
        NewTaxes = Player_in.CalculateTaxes();
        Player_in.setAmount(Player_in.getAmount() + NewTaxes);
    }

    /** Генерирует новую колоду игрока. */
    public static ArrayList<WarcastleCard> GeneratePrivateDeck()
    {
        ArrayList<WarcastleCard> rez = new ArrayList<WarcastleCard>();
        for (int c = 0; c < CARD_IN_PRIVATE_DECK; c++)
        {
            WarcastleCard NewCard = WarcastleCard.GenerateRandomCard();
            rez.add(NewCard);
        }
        return rez;
    }

    /** Отображает текущую игру. */
    public void Show()
    {
        System.out.println("------");
        System.out.println("Player 1:");
        BeginPlayerTurn((WarcastlePlayer)this.getPlayer1());
        ((WarcastlePlayer)this.getPlayer1()).Show();
        System.out.println("------");
        System.out.println("Player 2:");
        ((WarcastlePlayer)this.getPlayer2()).Show();
    }

    /** Окончание хода игрока  Player_in. */
    public void EndPlayerTurn(WarcastlePlayer Player_in)
    {
        BeginPlayerTurn(Player_in);
    }

    /** Рассчитывает шанс крита.*/
    private int CalculateCritChanse(WarcastlePlayer Attacker_in, WarcastlePlayer Defender_in)
    {
        int rez;
        rez = (int)Math.ceil(40 + ((Attacker_in.getLuck() - Defender_in.getLuck() - 50)/2.0));
        if (rez < 5)
            rez = 5;
        if (rez > 60)
            rez = 60;
        return rez;
    }

    /** Кидает кости на успех, где вероятность успеха составляет Probability_in.*/
    public static boolean RollSucces(int Probability_in)
    {
        boolean rez = false;
        final Random random = new Random();
        int Generated;
        Generated = random.nextInt(100);
        rez = (Generated <= Probability_in);
        return rez;
    }

    /** Вычисляет коэффициент уворота игрока Defender_in от атаки игрока Attacker_in.*/
    public int CalculateDodgeKoeff(WarcastlePlayer Attacker_in, WarcastlePlayer Defender_in)
    {
        int rez = 0;
        int DodgeEffect = 0;
        DodgeEffect = (Defender_in.getDodge() - Attacker_in.getSwordSkill() + 20);
        if (DodgeEffect < 0)
        {
            rez = 0;
        }
        else
        {
            if (DodgeEffect < 180)
            {
                rez = (int)Math.ceil(DodgeEffect / 2.0);
            }
            else
            {
                rez = (int)Math.ceil(DodgeEffect*6.0/(1 + DodgeEffect*0.06));
            }
        }
        return rez;
    }

    /** Выполняет атаку мечом игрока Attacker_in в отношение игрока Defender_in.*/
    public void AttackBySwords(WarcastlePlayer Attacker_in, WarcastlePlayer Defender_in)
    {
        // Иниициализация.
        int Damage = 0, CritChance = 0, DodgeKoeff = 0;
        // Вычисление прямого урона мечником.
        Damage = (Attacker_in.getSwordAttack());
        // Вычисление уворота.
        DodgeKoeff = CalculateDodgeKoeff(Attacker_in, Defender_in);
        Damage = (int)Math.ceil(Damage * (1 - DodgeKoeff/100));
        // Вычисление поглощения бронёй.
        Damage = Damage - Defender_in.getArmor();
        // Вычисление крита мечником.
        CritChance = CalculateCritChanse(Attacker_in, Defender_in);
        if (RollSucces(CritChance))
        {
            Damage = Damage * 2;
        }
        else
        {
            // Крит не выпал - не изменяем урон.
        }
        // Уменьшение боевого духа защищающегося.
        Defender_in.IncrementMorale(-Damage);
    }

    /** Выполняет атаку конницей игрока Attacker_in в отношение игрока Defender_in.*/
    public void AttackByHorse(WarcastlePlayer Attacker_in, WarcastlePlayer Defender_in)
    {
        int Damage = 0;
        Damage = Attacker_in.getHorseAttack();
        Defender_in.IncrementMorale(-Damage);
    }

    /** Выполняет атаку игрока Attacker_in на Defender_in*/
    public void AttackPlayer(WarcastlePlayer Attacker_in, WarcastlePlayer Defender_in)
    {
        AttackBySwords(Attacker_in, Defender_in);
        AttackByHorse(Attacker_in, Defender_in);
    }

}
