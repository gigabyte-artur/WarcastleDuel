package ru.gigabyte_artur.warcastleduel.gaming;

import java.util.ArrayList;
import java.util.Random;

public class Player
{

    private String name;
    // Конструктор нового.
    public Player()
    {
        SetRandomName();
    }

    // Конструктор копирования.
    public Player(Player player_in)
    {
        setName(player_in.getName());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Player copy()
    {
        return new Player(this);
    }

    // Возвращает массив случайных имён.
    private ArrayList<String> GetRandomNamesArray()
    {
        ArrayList<String> rez = new ArrayList<>();
        rez.add("Adam");
        rez.add("Alana");
        rez.add("Albert");
        rez.add("Alex");
        rez.add("Alexander");
        rez.add("Alfie");
        rez.add("Alice");
        rez.add("Amber");
        rez.add("Amelia");
        rez.add("Andrew");
        rez.add("Archie");
        rez.add("Austin");
        rez.add("Ava");
        rez.add("Ben");
        rez.add("Bethany");
        rez.add("Bill");
        rez.add("Brad");
        rez.add("Caleb");
        rez.add("Caroline");
        rez.add("Charlie");
        rez.add("Chloe");
        rez.add("Chris");
        rez.add("Connor");
        rez.add("Cynthia");
        rez.add("Dave");
        rez.add("David");
        rez.add("Deborah");
        rez.add("Dexter");
        rez.add("Douglas");
        rez.add("Edgar");
        rez.add("Ella");
        rez.add("Ellie");
        rez.add("Emily");
        rez.add("Emma");
        rez.add("Ethan");
        rez.add("Evie");
        rez.add("Faith");
        rez.add("Florence");
        rez.add("Fred");
        rez.add("Freya");
        rez.add("Gabriel");
        rez.add("George");
        rez.add("Gordon");
        rez.add("Grace");
        rez.add("Hana");
        rez.add("Harry");
        rez.add("Heidi");
        rez.add("Henry");
        rez.add("Hurriet");
        rez.add("Isabella");
        rez.add("Isabelle");
        rez.add("Isla");
        rez.add("Ivy");
        rez.add("Jack");
        rez.add("Jacob");
        rez.add("James");
        rez.add("Jenna");
        rez.add("Jerry");
        rez.add("Jessica");
        rez.add("John");
        rez.add("Joseph");
        rez.add("Joshua");
        rez.add("Kevin");
        rez.add("Leo");
        rez.add("Lily");
        rez.add("Lora");
        rez.add("Maddison");
        rez.add("Martin");
        rez.add("Maryam");
        rez.add("Melanie");
        rez.add("Mia");
        rez.add("Michael");
        rez.add("Nicole");
        rez.add("Noah");
        rez.add("Oliver");
        rez.add("Olivia");
        rez.add("Oscar");
        rez.add("Owen");
        rez.add("Paige");
        rez.add("Pamela");
        rez.add("Phoebe");
        rez.add("Poppy");
        rez.add("Raymond");
        rez.add("Rose");
        rez.add("Roxie");
        rez.add("Ruby");
        rez.add("Ryan");
        rez.add("Samuel");
        rez.add("Samuel");
        rez.add("Sandra");
        rez.add("Scarlett");
        rez.add("Scarlett");
        rez.add("Sharon");
        rez.add("Sophia");
        rez.add("Sophie");
        rez.add("Stanley");
        rez.add("Steve");
        rez.add("Susan");
        rez.add("Theodore");
        rez.add("Thomas");
        rez.add("Tom");
        rez.add("Virginia");
        rez.add("William");
        rez.add("Zoe");
        return rez;
    }

    // Устанавливает имя из списка случайных.
    public void SetRandomName()
    {
        final Random random = new Random();
        int IdName;
        ArrayList<String> ArrayNames = GetRandomNamesArray();
        String NewName;
        int NamesSize = ArrayNames.size();
        if (NamesSize > 0)
        {
            IdName = random.nextInt(NamesSize - 1);
            NewName = ArrayNames.get(IdName);
            setName(NewName);
        }
    }

    // Запускает новую игру.
    public void NewGame()
    {
//        this.hand.Empty();
        // TODO: Написать контруктор копирования.
    }

    // Играет текущим игроком игру game_in. Возвращает итоговую сумму очков. Параметр silent_in определяет, будут
    // ли выводиться данные в консоль.
    public int Play(TwoPlayersGame game_in, boolean silent_in)
    {
        int rez;
        rez = 0;
        return rez;
    }
}
