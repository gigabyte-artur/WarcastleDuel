package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gigabyte_artur.warcastleduel.card_game.Card;

public class ScreenCard
{

    private Card LinkedCard;        // Привязанная карта игры.
    private int x;                  // Координата X.
    private int y;                  // Координата Y.
    private int width;              // Ширина.
    private int height;             // Высота.
    private Texture cardTexture;    // Текстура карты.
    private Texture Shadow;         // Текстура тени.
    private Sprite cardSprite;      // Спрайт карты.
    private boolean isCardDragged = false;      // Признак, что карта перетаскивается.

    public ScreenCard()
    {
        this.width = 0;
        this.height = 0;
        this.x = 0;
        this.y = 0;
    }

    public ScreenCard(Card Card_in, String TexturePath_in)
    {
        this.width = 80;
        this.height = 120;
        this.setLinkedCard(Card_in);
        this.cardTexture = new Texture(Gdx.files.internal(TexturePath_in));
        this.cardSprite = new Sprite(cardTexture);
        this.cardSprite.setSize(this.width, this.height);
        this.Shadow = new Texture(Gdx.files.internal("shadow.jpg"));
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLinkedCard(Card linkedCard) {
        LinkedCard = linkedCard;
    }

    public void setCardTexture(Texture cardTexture) {
        this.cardTexture = cardTexture;
    }

    public void setCardSprite(Sprite cardSprite) {
        this.cardSprite = cardSprite;
    }

    public Sprite getCardSprite()
    {
        return cardSprite;
    }

    public boolean isCardDragged() {
        return isCardDragged;
    }

    public void setCardDragged(boolean cardDragged) {
        isCardDragged = cardDragged;
    }

    public Card getLinkedCard()
    {
        return LinkedCard;
    }

    // Выводит карту на экран в рамках батча batch.
    public void draw(SpriteBatch batch)
    {
        Color shadowColor = new Color(0, 0, 0, 0.5f); // Цвет тени (черный с 50% прозрачности)
        batch.setColor(shadowColor);
        batch.draw(this.Shadow, this.cardSprite.getX() + 5, this.cardSprite.getY() - 5); // Изменяем координаты для положения тени
        batch.setColor(Color.WHITE); // Восстанавливаем обычный цвет для последующей отрисовки основного спрайта
        cardSprite.draw(batch);
    }

    // Устанавливает карте позицию (x_in; y_in).
    public void setPosition(int x_in, int y_in)
    {
        this.x = x_in;
        this.y = y_in;
        this.cardSprite.setPosition(this.x, this.y);
    }
}
