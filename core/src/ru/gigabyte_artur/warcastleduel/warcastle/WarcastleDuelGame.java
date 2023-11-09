package ru.gigabyte_artur.warcastleduel.warcastle;

import ru.gigabyte_artur.warcastleduel.card_game.Hand;
import ru.gigabyte_artur.warcastleduel.gaming.TwoPlayersGame;

import java.util.ArrayList;

public class WarcastleDuelGame extends TwoPlayersGame
{

    private static final int CARD_IN_PRIVATE_DECK = 30;        // Сколько корт в колоде по умолчанию.

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
        WarcastlePlayer Player2 = new WarcastlePlayer();
        Player2.Init();
        this.SetPlayers(Player1, Player2);
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

    // Начинает ход игрока Player_in.
    private void BeginPlayerTurn(WarcastlePlayer Player_in)
    {
        Hand PlayersDeck, PlayersHand;
        PlayersDeck  = Player_in.getDeck();
        PlayersHand  = Player_in.getPrivateHand();
        PlayersHand.DrawCard(PlayersDeck);
    }

    // Генерирует новую колоду игрока.
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

    // Отображает текущую игру.
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

}
