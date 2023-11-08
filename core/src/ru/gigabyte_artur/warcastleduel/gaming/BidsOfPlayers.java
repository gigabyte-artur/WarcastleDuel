package ru.gigabyte_artur.warcastleduel.gaming;

import java.util.ArrayList;
import java.util.HashMap;

public class BidsOfPlayers
{

    private HashMap<Player, Integer> Bids;        // Ставки игроков.

    public BidsOfPlayers()
    {
        this.Bids = new HashMap<Player, Integer>();
    }

    public BidsOfPlayers(ArrayList<Player> players_in)
    {
        this.Bids = new HashMap<Player, Integer>();
        for (Player curr_players_in : players_in)
        {
            setBidOfPlayer(curr_players_in, 0);
        }
    }

    // Устаанавливает ставку игрока player_in в размере bid_in.
    public void setBidOfPlayer(Player player_in, int bid_in)
    {
        this.Bids.put(player_in, bid_in);
    }

    public int getBidOfPlayer(Player player_in)
    {
        int rez = Bids.get(player_in);
        return rez;
    }
}
