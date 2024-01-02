package ru.gigabyte_artur.warcastleduel.warcastle.screen_interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class ScreenButton extends ImageButton
{

    private Texture ButtonTexture;
    Stage ScreensStage;
    Action AfterActButton = new Action() {
        @Override
        public boolean act(float v)
        {
            return false;
        }
    };

    public ScreenButton()
    {
        this(new TextureRegionDrawable(new Texture("Interface/AddButton.png")));
        ButtonTexture = new Texture(Gdx.files.internal("Interface/AddButton.png"));
        ScreensStage = new Stage();
        // Добавляем обработчик кликов на кнопку
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ActButton();
                AfterActButton.act(0);
            }
        });
    }

    public ScreenButton(Drawable drawable)
    {
        super(drawable);
    }

    /** Выполняет действие кнопки.*/
    public abstract void ActButton();

    public void setAfterActButton(Action afterActButton)
    {
        AfterActButton = afterActButton;
    }

    public void setButtonTexture(Texture buttonTexture_in)
    {
        ButtonTexture = buttonTexture_in;
        TextureRegion newTextureRegion = new TextureRegion(ButtonTexture);
        TextureRegionDrawable newDrawable = new TextureRegionDrawable(newTextureRegion);
        this.getStyle().imageUp = newDrawable;
    }

    public void SetTextureByPath(String path)
    {
        Texture newTexture = new Texture(Gdx.files.internal(path));
        setButtonTexture(newTexture);
    }

    public void setScreensStage(Stage screensStage)
    {
        ScreensStage = screensStage;
        Gdx.input.setInputProcessor(ScreensStage);
        ScreensStage.addActor(this);
    }

    public void Render(float delta)
    {
        // Отрисовка сцены.
        ScreensStage.act(delta);
        ScreensStage.draw();
    }
}
