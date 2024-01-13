package fr.uge.adventure.main;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import fr.uge.adventure.camera.Camera;
import fr.uge.adventure.collision.CollisionChecker;
import fr.uge.adventure.entity.Entity;
import fr.uge.adventure.entity.Player;
import fr.uge.adventure.entity.Enemy;
import fr.uge.adventure.entity.EnemyManager;
import fr.uge.adventure.fileloader.Parser;
import fr.uge.adventure.gamedata.GameData;
import fr.uge.adventure.input.InputHandler;
import fr.uge.adventure.renderer.GameRenderer;
import fr.uge.adventure.tile.TileManager;
import fr.uge.adventure.tile.TileMap;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;

public class Game {	
	private static double ogSprSize = 25;
	private final double tileSize;
	//FPS
	private final int fps = 120; //set a little more than 60 because the method
								//to calculate the waiting time is not 100% accurate
	
	//SCREEN SET UP
	private final int maxScrCol = 32; //32
	private final int maxScrRow = 18; //18
	private final double scrWidth;
	private final double scrHeight;
	private final ApplicationContext context;
	
	private final String mapName;
	private final GameData data;
	
	private final Player player;
	private final TileMap tileMap;
	private final ArrayList<Enemy> lstEnemy;
	
	private final TileManager tileMng;
	private final EnemyManager enemyMng;
	
	private final Camera cam;
	private final InputHandler input;
	private final CollisionChecker coliCheck;
	
	private final GameRenderer renderer;
	
	//DEBUG
	private int drawCount = 0;
	private long lastTime = System.nanoTime();
	private long currentTime;
	private long timerFps = 0;
	
	public Game(ApplicationContext context, String mapName) throws IOException {
		this.context = context;
		this.scrHeight = context.getScreenInfo().getHeight();
		this.scrWidth = context.getScreenInfo().getWidth();
		this.mapName = mapName;
		this.tileSize =  ogSprSize * scaleCalc();
		this.data = loadGameData();
		
		this.player = new Player(this);
		this.tileMap = new TileMap(data.map());
		this.lstEnemy = new ArrayList<Enemy>();
		
		this.input = new InputHandler();
		this.coliCheck = new CollisionChecker(this);
		this.tileMng = new TileManager(this);
		this.enemyMng = new EnemyManager(this);
		
		this.cam = new Camera(player, scrWidth, scrHeight, this);
		
		this.renderer = new GameRenderer(this);
	}
	
	public void update() {
		//check input
		Event event = context.pollOrWaitEvent(1000 / fps);
		input.eventType(event);
		if (input.exitPressed) {
			context.exit(0);
			return;
		}
		
		//update events
		player.update();
		cam.update();
		enemyMng.update();
		tileMng.update();
		
		//update animation
		renderer.update();
		
		//debug
//		showFPS();
	}
	
	public void render() {
		renderer.render();
		drawCount++;
	}
	
	//DEBUG FUNCTIONS
	private void showFPS() {
		currentTime = System.nanoTime();
		timerFps += (currentTime - lastTime);
		lastTime = currentTime;
		if (timerFps >= 1000000000) { //equal to 1 second
			System.out.println("FPS : " + drawCount);
			drawCount = 0;
			timerFps = 0;
		}
	}
	
	public double scaleCalc() {
		double heightScale = (double) scrHeight / (double) (maxScrRow * ogSprSize);
		double widthScale = (double) scrWidth / (double) (maxScrCol * ogSprSize);
		return Math.max(heightScale, widthScale);
	}
	
	public GameData loadGameData() throws IOException {
		GameData data = new Parser(mapName, this).parse();
		return data;
	}
	
	public ApplicationContext context() {
		return this.context;
	}
	
	public TileManager tileManager() {
		return this.tileMng;
	}
	
	public GameData data() {
		return this.data;
	}
	
	public String mapName() {
		return this.mapName;
	}
	
	public double scrWidth() {
		return this.scrWidth;
	}
	
	public double scrHeight() {
		return this.scrHeight;
	}
	
	public int maxScrCol() {
		return this.maxScrCol;
	}
	
	public int maxScrRow() {
		return this.maxScrRow;
	}

	public GameRenderer renderer() {
		return this.renderer;
	}

	public Player player() {
		return player;
	}
	
	public InputHandler input() {
		return this.input;
	}

	public double tileSize() {
		return this.tileSize;
	}
	
	public TileMap tileMap() {
		return this.tileMap;
	}

	public CollisionChecker coliCheck() {
		return coliCheck;
	}

	public Camera camera() {
		return this.cam;
	}

	public ArrayList<Enemy> lstEnemy() {
		return lstEnemy;
	}
}
