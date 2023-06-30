package net.bram91;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameObjectSpawned;
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
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		switch(event.getGameObject().getId()) {
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
				client.getScene().removeGameObject(event.getGameObject());
		}
	}
}
