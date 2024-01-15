package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelCard;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelScreen;

public class ScreenCard extends ScreenRectangledElement
{
    private WarcastleDuelCard LinkedCard;                             // Привязанная карта игры.
    private Texture cardTexture;                         // Текстура карты.
    private Texture Shadow;                              // Текстура тени.
    private Texture coverTexture;                        // Текстура рубашки карты.
    private boolean isCovered = true;                    // Признак, что карта отображается рубашкой вверх.
    private Stage ScreenStage;
    private WarcastleDuelScreen ParentScreen;
    private Image CardImage;
    private boolean dragging;                           // Признак, что карта перетаскивается.

    public static final int STANDARD_WIDTH = 80;        // Стандартная ширина карты.
    public static final int STANDARD_HEIGHT = 120;      // Стандартная высота карты.

    Action AfterDrag = new Action() {
        @Override
        public boolean act(float v)
        {
            Image TargetImage = (Image) this.getTarget();
            ScreenCard UserObjectCard = (ScreenCard)TargetImage.getUserObject();
            try
            {
                ParentScreen.CardDrag_Finish(UserObjectCard, (int)TargetImage.getX(), (int)TargetImage.getY());
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return false;
        }
    };

    public ScreenCard()
    {
        super();
        this.coverTexture = new Texture(Gdx.files.internal("CardTextures/CardCover.jpg"));
        this.cardTexture = new Texture(Gdx.files.internal("CardTextures/sword.jpg"));
        this.Shadow = new Texture(Gdx.files.internal("CardTextures/shadow.jpg"));
        CardImage = new Image(this.cardTexture);
        AfterDrag.setTarget(CardImage);
        CardImage.setUserObject(this);
    }

    public ScreenCard(WarcastleDuelCard Card_in, String TexturePath_in)
    {
        super(0, 0, STANDARD_WIDTH, STANDARD_HEIGHT);
        this.setDimensions(STANDARD_WIDTH, STANDARD_HEIGHT);
        this.setLinkedCard(Card_in);
        this.coverTexture = new Texture(Gdx.files.internal("CardTextures/CardCover.jpg"));
        this.cardTexture = new Texture(Gdx.files.internal(TexturePath_in));
        CardImage = new Image(this.cardTexture);
        CardImage.setSize(this.getWidth(), this.getHeight());
        CardImage.setUserObject(this);
        this.Shadow = new Texture(Gdx.files.internal("CardTextures/shadow.jpg"));
        AfterDrag.setTarget(CardImage);
    }

    public void setScreenStage(Stage stage_in)
    {
        ScreenStage = stage_in;
        ScreenStage.addActor(CardImage);
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered)
    {
        isCovered = covered;
        if (CardImage == null)
            CardImage = new Image(this.cardTexture);
        if (isCovered)
        {
            Drawable newDrawable = new TextureRegionDrawable(coverTexture);
            CardImage.setDrawable(newDrawable);
        }
        else
        {
            Drawable newDrawable = new TextureRegionDrawable(cardTexture);
            CardImage.setDrawable(newDrawable);
        }
        CardImage.setSize(this.getWidth(), this.getHeight());
        CardImage.setPosition(this.getX(), this.getY());
    }

    public void setParentScreen(WarcastleDuelScreen parentScreen)
    {
        ParentScreen = parentScreen;
        ScreenStage = parentScreen.getStage();
    }

    public void setLinkedCard(WarcastleDuelCard linkedCard) {
        LinkedCard = linkedCard;
    }

    public void setCardTexture(Texture cardTexture) {
        this.cardTexture = cardTexture;
    }

    public WarcastleDuelCard getLinkedCard()
    {
        return LinkedCard;
    }

    @Override
    public void setPosition(int x_in, int y_in)
    {
        super.setPosition(x_in, y_in);
        CardImage.setPosition(x_in, y_in);
    }

    /** Добавляет событие перетаскивание карты.*/
    public void AddDragListener()
    {
        CardImage.addListener(new InputListener(){

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
                dragging = true; // начало перетаскивания
                return true;
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer)
            {
                if (dragging)
                {
                    // обновляем позицию изображения в соответствии с перемещением
                    CardImage.moveBy(x - CardImage.getWidth() / 2, y - CardImage.getHeight() / 2);
                }
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button)
            {
                dragging = false; // конец перетаскивания
                int NewY = (int)CardImage.getY();
                setPosition((int)CardImage.getX(), NewY);
                AfterDrag.act(0);
            }

        });
    }

    /**Выводит карту на экран в рамках батча batch.*/
    public void draw(SpriteBatch batch)
    {
//        Color shadowColor = new Color(0, 0, 0, 0.5f); // Цвет тени (черный с 50% прозрачности)
//        batch.setColor(shadowColor);
//        batch.draw(this.Shadow, this.getX() + 5, this.getY() - 5); // Изменяем координаты для положения тени
//        batch.setColor(Color.WHITE); // Восстанавливаем обычный цвет для последующей отрисовки основного спрайта
//        cardSprite.draw(batch);
//        // Загрузка текстуры
//        Texture texture = new Texture(Gdx.files.internal("image.png"));
// Создание экземпляра Image с указанной текстурой
//        Image image;
//        Texture SelectedTexture;
//        if (!this.isCovered)
//        {
//            SelectedTexture = this.cardTexture;
//        }
//        else
//        {
//            SelectedTexture = this.coverTexture;
//        }
//        Image image = new Image(SelectedTexture);
        // Установка позиции изображения
//        CardImage.setPosition(this.getX(), this.getY());
//        CardImage.setWidth(this.getWidth());
//        CardImage.setHeight(this.getHeight());
        // Добавление изображения на сцену (Stage)
//        stage.addActor(CardImage);
    }

    /** */
    public void RemoveFromStage(Stage stage_chng)
    {
        stage_chng.getRoot().removeActor(CardImage);
    }
}
