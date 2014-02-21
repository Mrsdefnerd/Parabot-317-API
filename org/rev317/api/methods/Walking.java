package org.rev317.api.methods;

import org.rev317.accessors.Client;
import org.rev317.api.sleep.Condition;
import org.rev317.api.sleep.Sleep;
import org.rev317.api.wrappers.scene.Tile;
import org.rev317.Loader;

/**
 * 
 * @author Everel
 *
 */
public final class Walking {
	
	/**
	 * Destination X
	 * @return destination x
	 */
	public static final int getDestinationX() {
		return Loader.getClient().getDestinationX();
	}
	
	/**
	 * Destination Y
	 * @return destination y
	 */
	public static final int getDestinationY() {
		return Loader.getClient().getDestinationX();
	}
	
	/**
	 * Gets the destination tile
	 * @return destination tile
	 */
	public static final Tile getDestination() {
		final Client client = Loader.getClient();
		return new Tile(client.getBaseX() + client.getDestinationX(), client.getBaseY() + client.getDestinationY());
	}
	
	/**
	 * Determines if destination is set
	 * @return <b>true</b> if destination is set, otherwise <b>false</b>.
	 */
	public static final boolean isDestinationSet() {
		return getDestinationX() > 0 && getDestinationY() > 0;
	}
	
	/**
	 * Determines if player is walking
	 * @return whether player is walking
	 */
	public static final boolean isMoving() {
		return Players.getLocal().isWalking();
	}
	
	/**
	 * Determines if bot is ready to click next tile
	 * @return whether bot is ready to click next tile
	 */
	public static final boolean readyForNextTile() {
		return !isMoving() || !isDestinationSet() || getDestination().distanceTo() < 5;
	}
	
	public static void walkPath(final Tile[] tiles, boolean reverse) {
		Tile tile = null;
		if (reverse) {
			for (int i = tiles.length - 1; i >= 0; i--)
				if (tiles[i].isOnMinimap()) {
					tile = tiles[i];
					break;
				}
		} else {
			for (int i = 0; i < tiles.length; i++)
				if (tiles[i].isOnMinimap()) {
					tile = tiles[i];
					break;
				}
		}
		tile.clickMM();
		final Tile currentTile = tile;
		if (currentTile != null)
			Sleep.sleep(new Condition() {
	
				@Override
				public boolean validate() {
					return currentTile.distanceTo() > 5;
				}
				
			}, 2000);
	}
	
	public static final boolean pathVisible(Tile[] tile) {
		for (Tile t : tile)
			if (t.isOnMinimap())
				return true;
		return false;
	}
	
	public static final boolean isRunActivated() {
		return Settings.getInstance().getSetting(173) == 0;
	}
	
	public static final int getRunEnergy() {
		return Loader.getClient().getRunEnergy();
	}

}
