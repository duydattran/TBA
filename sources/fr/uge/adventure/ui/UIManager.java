package fr.uge.adventure.ui;

import fr.uge.adventure.main.Game;

public class UIManager {
	private final Game game;
	private static int gridCol = 3;
	private static int gridRow = 4;
	private int x = 0;
	private int y = 0;
	
	public UIManager(Game game) {
		this.game = game;
	}
	
	public void update() {
		uiInventoryControl();
	}
	
	public void uiInventoryControl() {
		if (game.input().rightTouch) {
			x = Math.min(gridCol - 1, x + 1);
			game.input().rightTouch = false;
		}
		else if (game.input().leftTouch) {
			x = Math.max(0, x - 1);
			game.input().leftTouch = false;
		}
		else if (game.input().upTouch) {
			y = Math.max(0, y - 1);
			game.input().upTouch = false;
		}
		else if (game.input().downTouch) {
			y = Math.min(gridRow - 1, y + 1);
			game.input().downTouch = false;
		}
		
		game.player().useItem();
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
}
