package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WarcastleDuelApplication extends ApplicationAdapter
{
	WarcastleDuelGame Game1 = new WarcastleDuelGame();
	private WarcastleDuelJoinGameScreen JoinGameScreen;

	@Override
	public void create ()
	{
		Game1.Init();
		JoinGameScreen = new WarcastleDuelJoinGameScreen();
		setScreen(JoinGameScreen);
	}

	@Override
	public void render ()
	{
		JoinGameScreen.render(0);
	}

	private void setScreen(Screen screen)
	{
		screen.show();
	}

	@Override
	public void dispose ()
	{

	}
}
