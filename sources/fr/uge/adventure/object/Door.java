package fr.uge.adventure.object;

import fr.uge.adventure.collision.HitBox;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.element.ElementType;
import fr.uge.adventure.entity.Enemy;
import fr.uge.adventure.entity.Entity;
import fr.uge.adventure.gamedata.ObjectData;
import fr.uge.adventure.main.Game;

public class Door implements Element, GameObject{
	private double wrldX;
	private double wrldY;
	private final Game game;
	private final String name;
	private final String skin;
	private final HitBox hitBox;
	private final String OpenWith;
	private final String nameOpen;
	
	public Door(ObjectData data, Game game) {
		this.game = game;
		this.name = data.name();
		this.skin = data.skin();
		this.wrldX = (double) (data.pos().x() * game.tileSize());
		this.wrldY = (double) (data.pos().y() * game.tileSize());
		this.hitBox = new HitBox(0, 0, game.tileSize(), game.tileSize() + 15);
		this.hitBox.update(wrldX, wrldY);
		String[] strData = data.strData().get("locked").split(" ");
		this.OpenWith = strData[0];
		this.nameOpen = strData[1];
	}
	
	@Override
	public void event() {
		// TODO Auto-generated method stub
		
	}
	
	public String openWith() {
		return OpenWith;
	}

	public String nameOpen() {
		return nameOpen;
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}
	
	@Override
	public GameObjectType objType() {
		return GameObjectType.door;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String skin() {
		return this.skin;
	}

	@Override
	public double wrldX() {
		return this.wrldX;
	}

	@Override
	public double wrldY() {
		return this.wrldY;
	}

	@Override
	public HitBox hitBox() {
		return this.hitBox;
	}

	@Override
	public ElementType type() {
		return ElementType.GameObj;
	}
}
