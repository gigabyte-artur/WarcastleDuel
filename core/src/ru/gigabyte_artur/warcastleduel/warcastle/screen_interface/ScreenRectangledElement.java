package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

public abstract class ScreenRectangledElement
{
    private int x;
    private int y;
    private int width = 0;
    private int height = 0;

    public ScreenRectangledElement()
    {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public ScreenRectangledElement(int x_in, int y_in, int width_in, int height_in)
    {
        x = x_in;
        y = y_in;
        width = width_in;
        height = height_in;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    // Устанавливает элементу позицию (x_in; y_in).
    public void setPosition(int x_in, int y_in)
    {
        this.x = x_in;
        this.y = y_in;
    }

    // Устанавливает размеры width_in, int height_in элементу.
    public void setDimensions(int width_in, int height_in)
    {
        this.width = width_in;
        this.height = height_in;
    }
}
