package fr.uge.adventure.ui;

import java.util.ArrayList;

import fr.uge.adventure.main.Game;
import fr.uge.adventure.main.GameState;

public class UIManager {
	private final Game game;
	
	private boolean showText;
	
	private static int gridCol = 3;
	private static int gridRow = 4;
	private int xCursorInv = 0;
	private int yCursorInv = 0;
	private static int maxLineTextBox = 6;
	
	private String contentTextBox;
	
	private boolean textBox;
	
	public UIManager(Game game) {
		this.game = game;
	}
	
	public void update() {	
			uiInventoryControl();
			uiTextBoxControl();
	}
	
	public void uiInventoryControl() {
		if (game.gameState() == GameState.inventoryScr) {
			if (game.input().rightTouch) {
				xCursorInv = Math.min(gridCol - 1, xCursorInv + 1);
				game.input().rightTouch = false;
			}
			else if (game.input().leftTouch) {
				xCursorInv = Math.max(0, xCursorInv - 1);
				game.input().leftTouch = false;
			}
			else if (game.input().upTouch) {
				yCursorInv = Math.max(0, yCursorInv - 1);
				game.input().upTouch = false;
			}
			else if (game.input().downTouch) {
				yCursorInv = Math.min(gridRow - 1, yCursorInv + 1);
				game.input().downTouch = false;
			}
			
			game.player().useItem();
		}
	}
	
	public void uiTextBoxControl() {
		if (game.gameState() == GameState.dialogueScr) {
			if (game.input().spaceTouch) {
				if (game.renderer().ui().textCursor() + maxLineTextBox < game.renderer().ui().contentAdapt().size() - 2)
					game.renderer().ui().setTextCursor(game.renderer().ui().textCursor() + maxLineTextBox);
				else {
					game.renderer().ui().setTextCursor(0);
					game.setGameState(GameState.running);
				}
				game.input().spaceTouch = false;
			}
		}
	}
	
	public void textBox(String content) {
		
	}
	
	public int xCursorInv() {
		return this.xCursorInv;
	}
	
	public int yCursorInv() {
		return this.yCursorInv;
	}

	public boolean textBox() {
		return textBox;
	}

	public void setTextBox(boolean textBox) {
		this.textBox = textBox;
	}

	public String contentTextBox() {
		return contentTextBox;
	}

	public void setContentTextBox(String contentTextBox) {
		this.contentTextBox = contentTextBox;
	}

	public boolean showText() {
		return showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}
}
