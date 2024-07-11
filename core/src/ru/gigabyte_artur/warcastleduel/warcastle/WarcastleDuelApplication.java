package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WarcastleDuelApplication extends ApplicationAdapter
{
	private WarcastleDuelJoinGameScreen JoinGameScreen;
	Screen ShownScreen;

	@Override
	public void create ()
	{
		JoinGameScreen = new WarcastleDuelJoinGameScreen(this);
		setScreen(JoinGameScreen);
	}

	@Override
	public void render ()
	{
		ShownScreen.render(0);
	}

	public void setScreen(Screen screen)
	{
		screen.show();
		ShownScreen = screen;
	}

	@Override
	public void dispose ()
	{

	}
}
