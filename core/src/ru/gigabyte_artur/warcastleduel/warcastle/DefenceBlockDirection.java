package ru.gigabyte_artur.warcastleduel.warcastle;

import java.util.Random;

public class DefenceBlockDirection
{

    public final int DIRECTION_NONE = 0;
    public final int DIRECTION_UP = 1;
    public final int DIRECTION_RIGHT = 2;
    public final int DIRECTION_DOWN = 3;
    public final int DIRECTION_LEFT = 4;

    private int Direction;

    public DefenceBlockDirection()
    {
        Direction = DIRECTION_NONE;
    }

    public DefenceBlockDirection(int direction)
    {
        Direction = direction;
    }

    public int getDirection()
    {
        return Direction;
    }

    public void setDirection(int direction)
    {
        Direction = direction;
    }

    // Устанавливает случайное направление.
    public void SetRandomDirection()
    {
        final Random random = new Random();
        int Generated;
        Generated = random.nextInt(4) + 1;
        if (Generated == 1)
            this.Direction = DIRECTION_UP;
        if (Generated == 2)
            this.Direction = DIRECTION_RIGHT;
        if (Generated == 3)
            this.Direction = DIRECTION_DOWN;
        if (Generated == 4)
            this.Direction = DIRECTION_LEFT;
    }

    // Возвращает объект со случайным направлением.
    public static DefenceBlockDirection NewRandomDefenceDirection()
    {
        DefenceBlockDirection rez = new DefenceBlockDirection();
        rez.SetRandomDirection();
        return rez;
    }

    // Текстовое представление текущего направления.
    public String toString()
    {
        String rez = "";
        int CurrDirection;
        CurrDirection = this.getDirection();
        if (CurrDirection == DIRECTION_UP)
            rez = "Up";
        if (CurrDirection == DIRECTION_RIGHT)
            rez = "Right";
        if (CurrDirection == DIRECTION_DOWN)
            rez = "Down";
        if (CurrDirection == DIRECTION_LEFT)
            rez = "Left";
        return rez;
    }
}
