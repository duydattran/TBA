package fr.uge.adventure.collision;

import fr.uge.adventure.entity.Entity;
import fr.uge.adventure.main.Game;
import fr.uge.adventure.object.Item;
import fr.uge.adventure.tile.Tile;

public class CollisionChecker {
	private final Game game;

	public CollisionChecker(Game game) {
		this.game = game;
	}

	public void checkTile(Entity entity) {
		int entityHorizontalTile = 0;
		int entityVerticalTile = 0;
		int entityLeftWorldX = (int) (entity.hitBox().wrldX() / game.tileSize());
		int entityRightWorldX = (int) ((entity.hitBox().wrldX() + entity.hitBox().width()) / game.tileSize());
		int entityTopWorldY = (int) (entity.hitBox().wrldY() / game.tileSize());
		int entityBottomWorldY = (int) ((entity.hitBox().wrldY() + entity.hitBox().height()) / game.tileSize());
		
		//predict if the next position of player is in a tile thats is solid
		if (entity.xSpd() > 0) {
			entityHorizontalTile = (int) ((entity.hitBox().wrldX() + entity.hitBox().width() + entity.xSpd()) / game.tileSize());
		} else if (entity.xSpd() < 0)
			entityHorizontalTile = (int) ((entity.hitBox().wrldX() + entity.xSpd()) / game.tileSize());
		
		if (entity.ySpd() > 0) {
			entityVerticalTile = (int) ((entity.hitBox().wrldY() + entity.hitBox().height() + entity.ySpd()) / game.tileSize());
		} else if (entity.ySpd() < 0)
			entityVerticalTile = (int) ((entity.hitBox().wrldY() + entity.ySpd()) / game.tileSize());
		
		Tile tileNum1, tileNum2, tileNum3, tileNum4;
		
		//check two tiles for each direction
		tileNum1 = game.tileMap().tiles()[entityTopWorldY][entityHorizontalTile];
		tileNum2 = game.tileMap().tiles()[entityBottomWorldY][entityHorizontalTile];
		tileNum3 = game.tileMap().tiles()[entityVerticalTile][entityLeftWorldX];
		tileNum4 = game.tileMap().tiles()[entityVerticalTile][entityRightWorldX];
		
		if ((tileNum1 != null && tileNum1.isCollidable()) ||
			(tileNum2 != null && tileNum2.isCollidable())) {
			entity.setXSpd(0);
		}
		
		if ((tileNum3 != null && tileNum3.isCollidable())||
			(tileNum4 != null && tileNum4.isCollidable())) {
			entity.setYSpd(0);
		}
	}
	
	public Item checkObject(Entity entity) {
		for (var item : game.lstItem()) {
			if (item.hitBox() == null)
				continue;
			if (entity.hitBox().intersectInDistance(item.hitBox(), entity.xSpd(), entity.ySpd()))
				return item;
		}
		return null;
	}
}
