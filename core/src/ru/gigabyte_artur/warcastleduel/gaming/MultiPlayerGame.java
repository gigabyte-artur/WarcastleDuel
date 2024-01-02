package ru.gigabyte_artur.warcastleduel.gaming;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MultiPlayerGame
{
    private ArrayList<Player> Players = new ArrayList<>();                              // Игроки.
    private HashMap<Player, Integer> PlayersState = new HashMap<Player, Integer>();      // Состояния игроков.

    public static final int PLAYER_STATE_NONE = 0;

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public abstract int Play();

    // Инициализирует игру.
    public abstract void Init();

    // Генерирует игрока текущей игры.
    public abstract Player NewPlayer();

    // Возвращает стандартное количество игроков в одной игре.
    public abstract int GetStandardNumberOfPlayers();

    // Очищает и устанавливает в массив игроков пустых игроков в количестве num_players_in.
    public void SetEmptyPlayers(int num_players_in)
    {
        this.Players.clear();
        for (int c = 0; c < num_players_in; c++)
        {
            Player EmptyPlayer = new Player();
            this.Players.add(EmptyPlayer);
        }
    }

    // Возвращает игрока по его номеру.
    public Player getPlayerById(int id_player_in)
    {
        Player rez = new Player();
        if (this.Players.size() >= id_player_in)
        {
            rez = this.Players.get(id_player_in);
        }
        else
        {
            System.out.println("Невозможно получить игрока номер " + id_player_in + ". В игре недостаточно игроков.");
        }
        return rez;
    }

    // Устанавливает игрока Player_in в массив игроков под номером id_player_in.
    public void setPlayerById(int id_player_in, Player Player_in)
    {
        if (this.Players.size() >= id_player_in)
        {
            this.Players.set(id_player_in, Player_in);
        }
        else
        {
            System.out.println("Невозможно уставновить игрока номер " + id_player_in + ". В игре недостаточно игроков.");
        }
    }

    public ArrayList<Player> getPlayers()
    {
        return this.Players;
    }

    // Возвращает состояние игрока Player_in.
    public int GetPlayersState(Player Player_in)
    {
        int rez = this.PlayersState.get(Player_in);
        return rez;
    }

    // Устанавливает игроку Player_in состояние State_in.
    public void SetPlayersState(Player Player_in, int State_in)
    {
        this.PlayersState.put(Player_in, State_in);
    }

    // Добавляет игрока Player_in в текущую игру.
    public void AddPlayer(Player Player_in)
    {
        this.Players.add(Player_in);
        this.SetPlayersState(Player_in, PLAYER_STATE_NONE);
    }

    // Возвращает следующего за Player_in игрока.
    public Player GetNextPlayer(Player Player_in)
    {
        Player rez;
        int PlayerIndex, PlayersSize;
        ArrayList<Player> PlayersGame = this.getPlayers();
        PlayersSize = PlayersGame.size();
        PlayerIndex = PlayersGame.indexOf(Player_in);
        if (PlayerIndex < (PlayersSize - 1))
        {
            PlayerIndex = PlayerIndex + 1;
        }
        else
        {
            PlayerIndex = 0;
        }
        rez = PlayersGame.get(PlayerIndex);
        return rez;
    }

    // Играет текущую игру и возвращает победителя.
    public Player PlayAndGetWinner()
    {
        Player rez = new Player();
        int WinnerNumber = this.Play();
        ArrayList<Player> Players = this.getPlayers();
        rez = Players.get(WinnerNumber);
        return rez;
    }
}
