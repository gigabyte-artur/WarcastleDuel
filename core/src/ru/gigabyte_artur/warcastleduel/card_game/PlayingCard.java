package ru.gigabyte_artur.warcastleduel.card_game;

public class PlayingCard extends Card {
    private int suite;      // Масть.
    private int value;      // Значение.

    public static final int Suite_None = 0;         // Нет масти.
    public static final int Suite_Hearts = 1;       // Значение масти Червы.
    public static final int Suite_Diamonds = 2;     // Значение масти Буби.
    public static final int Suite_Clubs = 3;        // Значение масти Трефы.
    public static final int Suite_Spades = 4;       // Значение масти Пики.
    public static final int MAX_VALUE = 14;         // Максимальное значение карты.
    public static final int Value_None = 0;         // Значение масти 0.
    public static final int Value_Jack = 11;        // Значение масти Пики.
    public static final int Value_Queen = 12;       // Значение масти Пики.
    public static final int Value_King = 13;        // Значение масти Пики.
    public static final int Value_Ace = 14;         // Значение масти Пики.

    // Устанавливамет карту с мастью suite_in и значением value_in.
    public void Set(int suite_in, int value_in)
    {
        this.suite = suite_in;
        this.value = value_in;
    }

    // Возвращает масть.
    public int GetSuite()
    {
        return this.suite;
    }

    // Возвращает значение карты.
    public int GetValue()
    {
        return this.value;
    }

    // Печатает на экран значение текущей карты.
    public static void PrintValue(int value_in)
    {
        switch (value_in)
        {
            case  (Value_Jack):
                System.out.print("J");
                break;
            case (Value_Queen):
                System.out.print("Q");
                break;
            case (Value_King):
                System.out.print("K");
                break;
            case (Value_Ace):
                System.out.print("A");
                break;
            default:
                System.out.print(value_in);
                break;
        }
    }

    @Override
    // Выводит карту на экран.
    public void Show()
    {
        System.out.print("(");
        // Вывод масти.
        switch (this.suite)
        {
            case  (Suite_Hearts):
                System.out.print("H");
                break;
            case (Suite_Diamonds):
                System.out.print("D");
                break;
            case (Suite_Clubs):
                System.out.print("C");
                break;
            case (Suite_Spades):
                System.out.print("S");
                break;
            default:
                break;
        }
        // Вывод значения.
        System.out.print(" ");
        this.PrintValue(this.value);
        System.out.print(")");
    }

    // Переопределение операции сравнения.
    public boolean equals(PlayingCard card_in)
    {
        boolean rez = false;
        if (card_in == null)
            rez = false;
        else
        {
            boolean equals_suite, equals_value;
            equals_suite = (card_in.GetSuite() == this.GetSuite());
            equals_value = (card_in.GetValue() == this.GetValue());
            rez = ((equals_suite) && (equals_value));
        }
        return rez;
    }

}
