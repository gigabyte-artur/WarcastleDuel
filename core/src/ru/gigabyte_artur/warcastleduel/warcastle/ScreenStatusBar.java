package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayDeque;

public class ScreenStatusBar extends ScreenRectangledElement
{
    private ArrayDeque<String> StatusBarText = new ArrayDeque<>();           // Очередь строк
    private int TextSize = 0;                                                // Размер текста в строках.
    private BitmapFont StatusBarFont;                                        // Шрифт текста.

    int LineSpacing = 0;

    public static final int STANDARD_LINE_SPACING = 30;

    public ScreenStatusBar()
    {
        super();
        // Шрифт.
        StatusBarFont = new BitmapFont();
        StatusBarFont.setColor(Color.GOLD);
        LineSpacing = STANDARD_LINE_SPACING;
    }

    public ScreenStatusBar(int x_in, int y_in, int width_in, int height_in)
    {
        super(x_in, y_in, width_in, height_in);
        // Шрифт.
        StatusBarFont = new BitmapFont();
        StatusBarFont.setColor(Color.GOLD);
        LineSpacing = STANDARD_LINE_SPACING;
    }

    public int getLineSpacing()
    {
        return LineSpacing;
    }

    public void setTextSize(int textSize)
    {
        TextSize = textSize;
    }

    /** Инициализирует текст строчками в количестве size_in. */
    public void InitText(int size_in)
    {
        for (int c = 0; c < size_in; c++)
        {
            StatusBarText.add("");
        }
        this.setTextSize(size_in);
    }

    /** Добавляет в статус-бар текст  text_in.*/
    public void AddText(String text_in)
    {
        StatusBarText.poll();
        StatusBarText.add(text_in);
    }

    /** Выводит статус-бар на экран.*/
    public void draw(SpriteBatch batch)
    {
        int count = 1;
        int NewY = 0;
        for (String curr_StatusBarText: StatusBarText)
        {
            NewY = this.getY() + (count * getLineSpacing());
            StatusBarFont.draw(batch, curr_StatusBarText, this.getX(), NewY);
            count = count + 1;
        }
    }
}
