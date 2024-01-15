package fr.uge.adventure.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.adventure.main.Game;
import fr.uge.adventure.object.Item;
import fr.uge.adventure.ulti.Utilities;

public class UI {
	private final Game game; 
	private final GameRenderer gameRenderer;
	private static int cellSize = 160;
	
	public UI(Game game, GameRenderer gameRenderer) {
		Objects.requireNonNull(game);
		
		this.game = game;
		this.gameRenderer = gameRenderer;
	}
	
	public void update() {
		
	}
	
	public void inventoryGrid(Graphics2D g2) {
		double offSetX = 400;
		double offSetY = 100;
		double width = game.scrWidth() - 2 * offSetX;
		double height = game.scrHeight() - 4 * offSetY;
		
		g2.setColor(new Color(0, 0, 0, 230));
		g2.fill(new Rectangle(0, 0, (int)game.scrWidth(), (int)game.scrHeight()));
		
		g2.setColor(new Color(200, 195, 148, 200));
		g2.fillRoundRect((int) offSetX,(int) (2 * offSetY),(int) width, (int)height, 20, 20);
		
		offSetX += width / 2 + 50;
		offSetY = 2 * (offSetY + 20) - 20;
		int cols = 3, rows = 4;
		width = cellSize * cols;
		height = cellSize * rows;
		
		g2.setColor(new Color(175, 172, 154, 200));
		g2.fillRoundRect((int) (offSetX),(int) (offSetY),(int) width, (int)height, 20, 20);
		
		int x, y;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				x = (int) (offSetX + col * cellSize);
				y = (int) (offSetY + row * cellSize);
				
				g2.setColor(new Color(0, 0, 0, 109));
				g2.drawRoundRect(x, y, cellSize, cellSize, 20, 20);
				
				if (game.uiMng().x() == col && game.uiMng().y() == row)
					g2.setColor(new Color(255, 255, 255, 200));
				else 
					g2.setColor(new Color(0, 0, 0, 100));
				g2.fillRoundRect(x, y, cellSize, cellSize, 20, 20);
				
				int index = cols * row + col;
				BufferedImage texture = null;
				Item item = null;
				if (index < game.player().inventory().size())
					item = game.player().inventory().get(index);
				if (item != null)
					texture = gameRenderer.texture().lstItemTextureUI().get(item.skin()).get(0);
				if (texture != null)
					g2.drawImage(texture, null, x + cellSize / 4, y + cellSize / 4);
			}
		}
	}
	
	public void healthBar(Graphics2D g2) {
		ArrayList<BufferedImage> texture = gameRenderer.texture().lstUiTexture().get("love");
		BufferedImage currentFrame = texture.get(0);
		double x = 30, y = 30, padding = 20;
		for (int i = 0; i < game.player().health(); i++) {
			g2.drawImage(currentFrame, null, (int)(x + i * (currentFrame.getWidth() + padding)), (int)y);
		}
	}
}
