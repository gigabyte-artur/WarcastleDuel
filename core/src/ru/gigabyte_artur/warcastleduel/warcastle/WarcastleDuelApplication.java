package ru.gigabyte_artur.warcastleduel.warcastle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gigabyte_artur.warcastleduel.card_game.Card;

import java.util.ArrayList;

public class WarcastleDuelApplication extends ApplicationAdapter
{
	SpriteBatch batch;
	WarcastleDuelGame Game1 = new WarcastleDuelGame();
	private WarcastleDuelScreen gameScreen;

	@Override
	public void create ()
	{
		Game1.Init();
		gameScreen = new WarcastleDuelScreen();
		ArrayList<Card> Player1Cards = ((WarcastlePlayer)Game1.getPlayer1()).getPrivateHand().getCards();
		ArrayList<WarcastleCard> PlayersCards = new ArrayList<>();
		for (Card curr_card: Player1Cards)
		{
			PlayersCards.add((WarcastleCard)curr_card);
		}
		gameScreen.setPlayersCards(PlayersCards);
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
