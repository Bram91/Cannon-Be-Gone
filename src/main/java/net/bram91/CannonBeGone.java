package net.bram91;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Cannon Be Gone"
)
public class CannonBeGone extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Override
	protected void startUp() throws Exception
	{
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			clientThread.invoke(this::hide);
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientThread.invoke(() ->
		{
			if (client.getGameState() == GameState.LOGGED_IN)
			{
				client.setGameState(GameState.LOADING);
			}
		});
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		hide();
	}

	private void hide()
	{
		Scene scene = client.getScene();
		Tile[][] tiles = scene.getTiles()[0];
		for (int x = 0; x < Constants.SCENE_SIZE; ++x)
		{
			for (int y = 0; y < Constants.SCENE_SIZE; ++y)
			{
				Tile tile = tiles[x][y];
				if (tile == null)
				{
					continue;
				}

				for (GameObject gameObject : tile.getGameObjects())
				{
					if (gameObject != null)
					{
						switch(gameObject.getId()) {
							case ObjectID.BROKEN_MULTICANNON:
							case ObjectID.BROKEN_MULTICANNON_14916:
							case ObjectID.BROKEN_MULTICANNON_43028:
							case ObjectID.DWARF_MULTICANNON:
							case ObjectID.DWARF_MULTICANNON_11868:
							case ObjectID.DWARF_MULTICANNON_35885:
							case ObjectID.DWARF_MULTICANNON_35886:
							case ObjectID.DWARF_MULTICANNON_43027:
							case ObjectID.DWARF_MULTICANNON_5975:
							case ObjectID.DWARF_MULTICANNON_5976:
								scene.removeGameObject(gameObject);
						}
						break;
					}
				}
			}
		}
	}
}
