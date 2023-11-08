package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class WarcastleDuelApplication extends ApplicationAdapter {
	SpriteBatch batch;
	WarcastleDuelGame Game1 = new WarcastleDuelGame();

	@Override
	public void create () {
		batch = new SpriteBatch();
		Game1.Init();
		Game1.Show();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
}
