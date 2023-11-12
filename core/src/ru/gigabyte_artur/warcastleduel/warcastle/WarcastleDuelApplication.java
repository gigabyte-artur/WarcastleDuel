package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class WarcastleDuelApplication extends ApplicationAdapter {
	SpriteBatch batch;
	WarcastleDuelGame Game1 = new WarcastleDuelGame();
	private WarcastleDuelScreen gameScreen;

	@Override
	public void create () {
		gameScreen = new WarcastleDuelScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		gameScreen.render(0);
	}

	private void setScreen(Screen screen)
	{
		screen.show();
	}

	@Override
	public void dispose ()
	{
		batch.dispose();
	}
}