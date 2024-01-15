package fr.uge.adventure.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.adventure.renderer.GameRenderer;
import fr.uge.adventure.renderer.Timer;
import fr.uge.adventure.ulti.Direction;
import fr.uge.adventure.ulti.Utilities;

public class EnemyRenderer {
	private final ArrayList<HashMap<Direction, ArrayList<BufferedImage>>> enemyTextures;
	private final ArrayList<Enemy> lstEnemy;
	private final GameRenderer gameRenderer;
	private final ArrayList<Timer> animTimers;
	private long animationTime = 100; // milliseconds
	private int[] animIndexes;
	
	public EnemyRenderer(ArrayList<Enemy> lstEnemy, GameRenderer gameRenderer) {
		Objects.requireNonNull(lstEnemy);
		Objects.requireNonNull(gameRenderer);
		
		this.enemyTextures = new ArrayList<HashMap<Direction, ArrayList<BufferedImage>>>();
		this.gameRenderer = gameRenderer;
		this.lstEnemy = lstEnemy;
		this.animIndexes = new int[lstEnemy.size()];
		loadEnemyTexture(gameRenderer.ogSprSize());
		
		this.animTimers = new ArrayList<Timer>();
	}
	
	public void update() {
		animateEnemy();
	}
	
	public void render(Graphics2D g2) {
		Objects.requireNonNull(g2);
		for (int i = 0; i < lstEnemy.size(); i++) {
			Enemy currentEnemy = lstEnemy.get(i);
			int currentIndexAnim = animIndexes[i];
			var texture = enemyTextures.get(i);
			BufferedImage currentTexture = texture.get(currentEnemy.direction()).get(currentIndexAnim);
			g2.drawImage(currentTexture, null, (int) (currentEnemy.wrldX() - gameRenderer.cam().camX()), 
						(int) (currentEnemy.wrldY() - gameRenderer.cam().camY()));
		}
	}
	
	private void loadEnemyTexture(double ogSprSize) {
		BufferedImage sprite = null;
		for (var enemy : lstEnemy) {
			var currentTexture = new HashMap<Direction, ArrayList<BufferedImage>>();
			for (var dir : Direction.values()) {
				String pngName = enemy.skin().toLowerCase() + "_" + dir.toString().toLowerCase() + ".png";
				System.out.println(pngName);
				sprite = Utilities.loadImage("/fr/images/player/", pngName);
				var textureList = new ArrayList<BufferedImage>();
	
				for (int row = 0; row < 3; row++) {
					for (int col = 0; col < 4; col++) {
						BufferedImage sprFrm = Utilities.getSpriteFrame(sprite, ogSprSize, col, row); //sprite frame
						BufferedImage sclFrm = Utilities.scaleImage(sprFrm, gameRenderer.scale()); //scaled frame
						textureList.add(sclFrm);
					}
				}
				currentTexture.put(dir, textureList);
			}
			enemyTextures.add(currentTexture);
		}
	}
	
	private void animateEnemy() {
		for (int i = 0; i < lstEnemy.size(); i++) {
			Enemy currentEnemy = lstEnemy.get(i);
			Timer currentTimer = animTimers.get(i);
			currentTimer.update();
			
			if (currentEnemy.xSpd() == 0 && currentEnemy.ySpd() == 0) {
				currentTimer.reset();
				animIndexes[i] = 0;
			}
			
			if (currentTimer.tick() >= animationTime * 1000000) {
				currentTimer.reset();
				animIndexes[i]++;
				if (animIndexes[i] >= 11)
					animIndexes[i] = 0;
			}
		}
	}
}
