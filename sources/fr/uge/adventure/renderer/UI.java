package fr.uge.adventure.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import fr.uge.adventure.main.Game;
import fr.uge.adventure.item.Item;
import fr.uge.adventure.ulti.Utilities;

public class UI {
	private final Game game; 
	private final GameRenderer gameRenderer;
	private static int cellSize = 160;
	private final Font font;
	private int textCursor = 0;
	private static int maxLineTextBox = 6;
	private ArrayList<String> contentAdapt;
	
	public UI(Game game, GameRenderer gameRenderer) {
		Objects.requireNonNull(game);
		
		this.game = game;
		this.gameRenderer = gameRenderer;
		this.font = Utilities.loadFont("/fr/font/", "font.ttf");
	}
	
	public void update() {
		
	}
	
	public void message(Graphics2D g2) {
		g2.setFont(font.deriveFont(24f));
		g2.setColor(Color.white);
		g2.drawString("hello dit me may", 100, 100);
	}
	
	public void textBox(Graphics2D g2) {
		double offSetX = 400;
		double offSetY = 700;
		double width = game.scrWidth() - 2 * offSetX;
		double height = 300;
		
		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect((int) (offSetX),(int) (offSetY),(int) width, (int)height, 20, 20);
		g2.setColor(new Color(255, 255, 255, 200));
		g2.drawRoundRect((int) (offSetX),(int) (offSetY),(int) width, (int)height, 20, 20);
	}
	
	public void textBoxString(Graphics2D g2, String name, String content) {
		//name
		double offSetX = 400;
		double offSetY = 690;
		double width = game.scrWidth() - 2 * offSetX;
		double height = 300;
		double lineSpace = 36;
		
		g2.setColor(new Color(255, 255, 255, 200));
		g2.setFont(font.deriveFont(32f));
		if (name != null)
			g2.drawString(name, (int)offSetX, (int)offSetY);
		
		//content
		offSetX = 430;
		offSetY = 750;
		
		g2.setColor(new Color(255, 255, 255, 200));
		g2.setFont(font.deriveFont(32f));
		
		contentAdapt = adaptTextBoxContent(width, content, 32f);
		
		for (int i = 0; i < maxLineTextBox; i++) {
			if (i + textCursor < contentAdapt.size())
				g2.drawString(contentAdapt.get(i + textCursor), (int)offSetX, (int)(offSetY + i * lineSpace));
		}
	}
	
	private ArrayList<String> adaptTextBoxContent(double widthTextBox, String inputString, double fontSize) {
		ArrayList<String> res = new ArrayList<String>();
		String[] words = inputString.split(" ");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			builder.append(words[i]);
			builder.append(" ");
			if (i < words.length - 1 && 
					(builder.length() + words[i + 1].length()) * (fontSize / 2) > widthTextBox - 2 * fontSize) {
				res.add(builder.toString());
				builder = new StringBuilder();
			}
			else if (i == words.length - 1) {
				if ((builder.length() + words[i].length()) * (fontSize / 2) > widthTextBox - 2 * fontSize) {
					builder.delete(builder.length() - words[i].length() - 1, builder.length());
					res.add(builder.toString());
					res.add(words[i]);
				}
				else {
					res.add(builder.toString());
				}
			}
		}
		return res;
	}
	
	public void equipment(Graphics2D g2) {
		double offSetX = 120;
		double offSetY = 900;
		double width = 100;
		double height = 120;
		
		g2.setColor(new Color(200, 195, 148, 200));
		g2.fillRoundRect((int)offSetX, (int)offSetY, (int)width, (int)height, 20, 20);
		
		if (game.player().item() != null) {
			BufferedImage texture = null;
			g2.setFont(font.deriveFont(16f));
			g2.setColor(Color.white);
			texture = gameRenderer.texture().lstItemTextureUI().get(game.player().item().skin()).get(0);
			if (texture != null) {
				g2.drawImage(texture, null, (int)(offSetX + 5), (int)(offSetY + 15));
				g2.drawString(game.player().item().name(), (int)(offSetX + width / 2 - 4 * game.player().item().name().length()), 
														   (int)(offSetY + height / 2 + 50)
														   );
			}
		}
	}
	
	public void weapon(Graphics2D g2) {
		double offSetX = 120;
		double offSetY = 760;
		double width = 100;
		double height = 120;
		
		g2.setColor(new Color(200, 195, 148, 200));
		g2.fillRoundRect((int)offSetX, (int)offSetY, (int)width, (int)height, 20, 20);
		
		if (game.player().weapon() != null) {
			BufferedImage texture = null;
			g2.setFont(font.deriveFont(16f));
			g2.setColor(Color.white);
			texture = gameRenderer.texture().lstItemTextureUI().get(game.player().weapon().skin()).get(0);
			if (texture != null) {
				g2.drawImage(texture, null, (int)(offSetX + 5), (int)(offSetY + 15));
				g2.drawString(game.player().weapon().name(), (int)(offSetX + width / 2 - 4 * game.player().weapon().name().length()), 
														   (int)(offSetY + height / 2 + 50)
														   );
			}
		}
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
				
				if (game.uiMng().xCursorInv() == col && game.uiMng().yCursorInv() == row)
					g2.setColor(new Color(255, 255, 255, 200));
				else 
					g2.setColor(new Color(0, 0, 0, 100));
				g2.fillRoundRect(x, y, cellSize, cellSize, 20, 20);
				
				int index = cols * row + col;
				BufferedImage texture = null;
				Item item = null;
				g2.setFont(font.deriveFont(16f));
				g2.setColor(Color.white);
				if (index < game.player().inventory().size())
					item = game.player().inventory().get(index);
				if (item != null)
					texture = gameRenderer.texture().lstItemTextureUI().get(item.skin()).get(0);
				if (texture != null) {
					g2.drawImage(texture, null, x + cellSize / 4, y + cellSize / 4);
					g2.drawString(item.name(), x + cellSize / 2 - 4 * item.name().length(), y + cellSize - 10);
				}
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

	public int textCursor() {
		return textCursor;
	}

	public void setTextCursor(int textCursor) {
		this.textCursor = textCursor;
	}

	public ArrayList<String> contentAdapt() {
		return contentAdapt;
	}

	public void setContentAdapt(ArrayList<String> contentAdapt) {
		this.contentAdapt = contentAdapt;
	}
}
