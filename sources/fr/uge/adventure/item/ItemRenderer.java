package fr.uge.adventure.item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import fr.uge.adventure.entity.Enemy;
import fr.uge.adventure.renderer.GameRenderer;
import fr.uge.adventure.renderer.Timer;
import fr.uge.adventure.ulti.Utilities;

public class ItemRenderer {
	private final ArrayList<ArrayList<BufferedImage>> lstItemTextures;
	private final ArrayList<Item> lstItem;
	private final GameRenderer gameRenderer;
	private final ArrayList<Timer> lstAnimTimers;
	private long animationTime = 100; // milliseconds
	private int[] animIndexes;
	
	public ItemRenderer(ArrayList<Item> lstItem, GameRenderer gameRenderer) {
		Objects.requireNonNull(lstItem);
		Objects.requireNonNull(gameRenderer);
		
		this.gameRenderer = gameRenderer;
		this.lstItemTextures = new ArrayList<ArrayList<BufferedImage>>();
		this.lstItem = lstItem;
		this.lstAnimTimers = new ArrayList<Timer>();
		this.animIndexes = new int[lstItem.size()];
		
		loadItemTexture();
		initializeTimers();
	}
	
	public void update() {
		animateItem();
	}
	
	public void render(Graphics2D g2) {
		Objects.requireNonNull(g2);
		
		for (int i = 0; i < lstItem.size(); i++) {
			Item currentItem = lstItem.get(i);
			if (!gameRenderer.cam().isItemInRange(currentItem)) {
				continue;
			}
			int currentIndexAnim = animIndexes[i];
			var texture = lstItemTextures.get(i);
			BufferedImage currentTexture = texture.get(currentIndexAnim);
			g2.drawImage(currentTexture, null, (int) (currentItem.wrldX() - gameRenderer.cam().camX()), 
						(int) (currentItem.wrldY() - gameRenderer.cam().camY()));
		}
	}
	
	private void loadItemTexture() {
		BufferedImage sprite = null;
		for (var item : lstItem) {
			
			String pngName = item.skin().toLowerCase() + ".png";
			System.out.println(pngName);
			sprite = Utilities.loadImage("/fr/images/object/", pngName);
			var textureList = new ArrayList<BufferedImage>();

			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 1; col++) {
					BufferedImage sprFrm = Utilities.getSpriteFrame(sprite, gameRenderer.ogSprSize(), col, row); //sprite frame
					BufferedImage sclFrm = Utilities.scaleImage(sprFrm, gameRenderer.scale()); //scaled frame
					textureList.add(sclFrm);
				}
			}
			
			lstItemTextures.add(textureList);
		}
	}
	
	private void initializeTimers() {
		for (int i = 0; i < lstItem.size(); i++) {
			lstAnimTimers.add(new Timer());
		}
	}
	
	private void animateItem() {
		for (int i = 0; i < lstItem.size(); i++) {
			Timer currentTimer = lstAnimTimers.get(i);
			currentTimer.update();
			
			if (currentTimer.tick() >= animationTime * 1000000) {
				currentTimer.reset();
				animIndexes[i]++;
				if (animIndexes[i] >= 3)
					animIndexes[i] = 0;
			}
		}
	}
	
	public void deleteTexture(Item item) {
		lstItemTextures.remove(lstItem.indexOf(item));
	}
}
