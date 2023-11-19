package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScreenButton extends ImageButton
{
    private ImageButton ImgButton;
    private Texture ButtonTexture;
    private Action onClickAction;

    public ScreenButton(Skin skin) {
        super(skin);
    }
}
