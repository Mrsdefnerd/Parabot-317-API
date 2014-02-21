package org.rev317.api.wrappers.interactive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.rev317.Loader;
import org.rev317.accessors.Client;
import org.rev317.api.interfaces.Interactable;
import org.rev317.api.interfaces.Locatable;
import org.rev317.api.methods.Calculations;
import org.rev317.api.methods.Game;
import org.rev317.api.methods.Menu;
import org.rev317.api.methods.Players;
import org.rev317.api.wrappers.renderable.CharacterModel;
import org.rev317.api.wrappers.scene.Tile;

public class Character implements Locatable, Interactable {
	private org.rev317.accessors.Character accessor = null;

	Client client;
	public Character(org.rev317.accessors.Character accessor) {
		this.accessor = accessor;
		this.client = Loader.getClient();
	}

	public final Tile getLocation() {
		Client client = Loader.getClient();
		return new Tile(client.getBaseX() + getLocalRegionX(),
				client.getBaseY() + getLocalRegionY(), client.getPlane());
	}

	public final int getLocalRegionX() {
		return this.accessor.getX() >> 7;
	}

	public final int getLocalRegionY() {
		return this.accessor.getY() >> 7;
	}

	public final Point getCenterPointOnScreen() {
		return Calculations.tileToScreen(getLocalRegionX(), getLocalRegionY(),
				0.5D, 0.5D, 100);
	}

	public final int distanceTo() {
		return (int) Calculations.distanceTo(getLocation());
	}

	public final int getAnimation() {
		return this.accessor.getAnimation();
	}

	public int getHealth() {
		return this.accessor.getHealth();
	}

	public final int getMaxHealth() {
		return this.accessor.getMaxHealth();
	}

	public final int getLoopCycleStatus() {
		return this.accessor.getLoopCycleStatus();
	}

	public final int getInteractingCharacter() {
		return this.accessor.getInteractingId();
	}

	public final int getTurnDirection() {
		return this.accessor.getTurnDirection();
	}

	public final int getHeight() {
		return this.accessor.getHeight();
	}

	public final int[] getQueueX() {
		return this.accessor.getQueueX();
	}

	public final int[] getQueueY() {
		return this.accessor.getQueueY();
	}

	public final boolean isWalking() {
		return this.accessor.isWalking() != 0;
	}

	public final String getDisplayedText() {
		return this.accessor.getDisplayedText();
	}

	public org.rev317.accessors.Character getAccessor() {
		return this.accessor;
	}

	public boolean isOnScreen() {
		return Calculations.isOnScreen(getCenterPointOnScreen());
	}
	
	public final String getUsername() {
		return client.getUsername();
	}
	
	public final String getPassword() {
		return client.getPassword();
	}

	public org.rev317.api.wrappers.renderable.Model getModel() {
		org.rev317.accessors.Model model = this.accessor.getModel();
		if (model == null) {
			return null;
		}
		return new CharacterModel(this.accessor.getModel(), this.accessor);
	}

	public String getName() {
		return null;
	}

	public boolean isInCombat() {
		return this.accessor.getLoopCycleStatus() > Game.getLoopCycle();
	}

	public void draw(Graphics g) {
		draw(g, false);
	}

	public void draw(Graphics g, boolean drawModel) {
		if (!isOnScreen()) {
			return;
		}
		if (drawModel) {
			org.rev317.api.wrappers.renderable.Model m = getModel();
			if (m != null) {
				m.drawWireFrame(g);
			}
		}
		Point p = getCenterPointOnScreen();
		g.setColor(Color.red);
		g.fillRect(p.x - 2, p.y - 2, 4, 4);
		g.setColor(Color.white);
		g.drawString(getName(), p.x + 5, p.y - 2);
	}

	public final boolean interact(String action) {
		org.rev317.api.wrappers.renderable.Model model = getModel();
		Point a = model == null ? getCenterPointOnScreen() : model
				.getCentralPoint();
		Menu.interact(action, a);
		return Game.getCrosshairType() == 2;
	}
	
	public Npc getNpcFacer() {
		if (this.getInteractingCharacter() == -1 || this.getInteractingCharacter() > 32768 || this.getInteractingCharacter() == 0)
			return null;
		return new Npc(client.getNpcs()[this.getInteractingCharacter()]);
	}
	
	public Player getPlayerFacer() {
		if (this.getInteractingCharacter() == -1 || this.getInteractingCharacter() == 0 || this.getInteractingCharacter() == 32768)
			return null;

		Player p = new Player(client.getPlayers()[this.getInteractingCharacter() - 32768]);
		if (p.getAccessor() == null)
			return Players.getLocal();
		return p;
	}
	
	public boolean isFacing() {
		return this.getInteractingCharacter() != -1 && this.getInteractingCharacter() != 0 && this.getInteractingCharacter() != 32768;
	}
	
}