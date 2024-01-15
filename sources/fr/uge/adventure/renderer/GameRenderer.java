package fr.uge.adventure.renderer;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import fr.uge.adventure.camera.Camera;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.main.Game;

public class GameRenderer {
	private static double ogSprSize = 25;
	private final double scale;
	private final double tileSize;
	private final Game game;
	
	private final Texture texture;
	
	private final MapRenderer mapRenderer;
	private final PlayerRenderer pRenderer;
	private final EnemyRenderer eRenderer;
	private final ItemRenderer iRenderer; 
	private final ObjectRenderer oRenderer; 
	private final UI ui;
	
	private final Camera cam;
	private BufferedImage bufferImage;
	private Graphics2D bufferGraphics;
	
	public GameRenderer(Game game) throws IOException {
		this.game = game;
		this.scale = scaleCalc();
		this.tileSize = ogSprSize * scale;
		bufferImage = new BufferedImage((int) game.scrWidth(), (int) game.scrHeight(), BufferedImage.TYPE_INT_RGB);
		bufferGraphics = bufferImage.createGraphics();
		
		this.texture = new Texture(game, scale);
		
		this.mapRenderer = new MapRenderer(game.tileMap(), this);
		this.pRenderer = new PlayerRenderer(game.player(), this);
		this.eRenderer = new EnemyRenderer(game.lstEnemy(), this);
		this.iRenderer = new ItemRenderer(game.lstItem(), this);
		this.oRenderer = new ObjectRenderer(game.lstObject(), this);
		this.ui = new UI(game, this);
		this.cam = game.camera();
	}
	
	public void update() {
		pRenderer.update();
		eRenderer.update();
		iRenderer.update();
		oRenderer.update();
		ui.update();
	}
	
	public void render() {
		this.game.context().renderFrame(graphics -> {	
			
			//draw all the element to the bufferImage off screen
			clearScreen(bufferGraphics);
			mapRenderer.render(bufferGraphics);
			iRenderer.render(bufferGraphics);
			pRenderer.render(bufferGraphics);
			eRenderer.render(bufferGraphics);
			oRenderer.render(bufferGraphics);
//			game.player().hitBox().draw(bufferGraphics, cam.camX(), cam.camY());
			
			//UI
			if (game.input().inventory)
				ui.inventoryGrid(bufferGraphics);
			ui.healthBar(bufferGraphics);
			ui.message(bufferGraphics);
			ui.equipment(bufferGraphics);
			ui.weapon(bufferGraphics);
			
			//when all the elements are drawn, draw the buffer image
			graphics.drawImage(bufferImage, null, 0, 0);
		});
	}
	
	public double scaleCalc() {
		double heightScale = (double) this.game.scrHeight() / (double) (game.maxScrRow() * ogSprSize);
		double widthScale = (double) this.game.scrWidth() / (double) (game.maxScrCol() * ogSprSize);
		return Math.max(heightScale, widthScale);
	}
	
	public void clearScreen(Graphics2D graphics) {
		Rectangle2D rec = new Rectangle2D.Double(0, 0, game.scrWidth(), game.scrHeight());
		graphics.setColor(Color.BLACK);
		graphics.fill(rec);
	}
	
	public ItemRenderer iRenderer() {
		return this.iRenderer;
	}
	
	public Camera cam() {
		return this.cam;
	}
	
	public double scale() {
		return this.scale;
	}
	
	public double tileSize() {
		return this.tileSize;
	}
	
	public double ogSprSize() {
		return 25;
	}
	
	public Texture texture() {
		return this.texture;
	}
}
