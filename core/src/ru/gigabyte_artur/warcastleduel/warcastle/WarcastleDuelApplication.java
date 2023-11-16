package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.badlogic.gdx.backends.lwjgl3.*;

public class WarcastleDuelApplication extends ApplicationAdapter
{
	SpriteBatch batch;
	WarcastleDuelGame Game1 = new WarcastleDuelGame();
	private WarcastleDuelScreen gameScreen;

	@Override
	public void create ()
	{
//		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config.width = 800; // Установка ширины окна
//		config.height = 600; // Установка высоты окна
//		new LwjglApplication(new WarcastleDuelApplication(), config);
		Game1.Init();
		gameScreen = new WarcastleDuelScreen();
		gameScreen.setGamePlaying(Game1);
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
