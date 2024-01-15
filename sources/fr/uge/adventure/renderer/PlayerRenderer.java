package fr.uge.adventure.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.uge.adventure.camera.Camera;
import fr.uge.adventure.entity.Player;
import fr.uge.adventure.ulti.Direction;
import fr.uge.adventure.ulti.Utilities;

public class PlayerRenderer {
	private final HashMap<Direction, ArrayList<BufferedImage>> pTexture;
	private ArrayList<BufferedImage> weaponTexture;
	private final Player player;
	private final GameRenderer gameRenderer;
	private final Timer animTimer;
	private long animationTime = 100; // milliseconds
	private int animIndex;
	
	public PlayerRenderer(Player player, GameRenderer gameRenderer) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(gameRenderer);
		
		this.pTexture = new HashMap<Direction, ArrayList<BufferedImage>>();
		this.gameRenderer = gameRenderer;
		this.player = player;
		this.setWeaponTexture(new ArrayList<BufferedImage>());
		loadPlayerTexture(gameRenderer.ogSprSize());
		
		this.animTimer = new Timer();
	}
	
	public void update() {
		animatePlayer();
		if (player.weapon() != null && weaponTexture.size() == 0)
			loadWeaponTexture(gameRenderer.ogSprSize());
	}
	
	public void render(Graphics2D g2) {
		Objects.requireNonNull(g2);
		Camera cam = gameRenderer.cam();
		g2.drawImage(pTexture.get(player.direction()).get(animIndex), null, (int) (player.wrldX() - cam.camX()), 
																			(int) (player.wrldY() - cam.camY()));
		if (weaponTexture.size() != 0)
			g2.drawImage(weaponTexture.get(0), null, (int) (player.wrldX() - cam.camX() + 50), 
				(int) (player.wrldY() - cam.camY()));															
	}
	
	public void renderWeapon() {
		
	}
	
	private void loadPlayerTexture(double ogSprSize) {
		BufferedImage sprite = null;
		for (var dir : Direction.values()) {
			String pngName = player.skin().toLowerCase() + "_" + dir.toString().toLowerCase() + ".png";
			System.out.println(pngName);
			sprite = Utilities.loadImage("/fr/images/player/", pngName);
			var textureList = new ArrayList<BufferedImage>();

			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 4; col++) {
					BufferedImage sprFrm = Utilities.getSpriteFrame(sprite, ogSprSize, col, row);
					BufferedImage sclFrm = Utilities.scaleImage(sprFrm, gameRenderer.scale());
					textureList.add(sclFrm);
				}
			}
			pTexture.put(dir, textureList);
		}
	}
	
	private void loadWeaponTexture(double ogSprSize) {
		BufferedImage sprite = null;
		String pngName = player.weapon().skin().toLowerCase() + ".png";
		System.out.println(pngName);
		sprite = Utilities.loadImage("/fr/images/object/", pngName);
		var textureList = new ArrayList<BufferedImage>();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 1; col++) {
				BufferedImage sprFrm = Utilities.getSpriteFrame(sprite, ogSprSize, col, row);
				BufferedImage sclFrm = Utilities.scaleImage(sprFrm, gameRenderer.scale());
				textureList.add(sclFrm);
			}
		}
		
		setWeaponTexture(textureList);
	}
	
	private void animatePlayer() {
		animTimer.update();
		
		if (player.xSpd() == 0 && player.ySpd() == 0) {
			animTimer.reset();
			animIndex = 0;
		}
		
		if (animTimer.tick() >= animationTime * 1000000) {
			animTimer.reset();
			animIndex++;
			if (animIndex >= 11)
				animIndex = 0;
		}
	}

	public ArrayList<BufferedImage> weaponTexture() {
		return weaponTexture;
	}

	public void setWeaponTexture(ArrayList<BufferedImage> weaponTexture) {
		this.weaponTexture = weaponTexture;
	}
}
