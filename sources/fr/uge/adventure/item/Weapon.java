package fr.uge.adventure.item;

import java.awt.Rectangle;

import fr.uge.adventure.collision.HitBox;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.element.ElementType;
import fr.uge.adventure.gamedata.ItemData;
import fr.uge.adventure.main.Game;

public class Weapon implements Element, Item{
	private final String name;
	private final String skin;
	private final double damage;
	
	private double wrldX;
	private double wrldY;
	private Rectangle hitBox;
	
	public Weapon(ItemData data, Game game) {
		this.name = data.name();
		this.skin = data.skin();
		this.damage = data.intData().get("damage");
		
		this.wrldX = (double) (data.pos().x() * game.tileSize());
		this.wrldY = (double) (data.pos().y() * game.tileSize());
		this.hitBox = new Rectangle(15, 20, (int) (game.tileSize() - 25), (int) (game.tileSize() - 20));
	}

	@Override
	public ItemType itemType() {
		return ItemType.weapon;
	}

	@Override
	public ElementType type() {
		return ElementType.GameObj;
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
	public String name() {
		return this.name;
	}

	@Override
	public String skin() {
		return this.skin;
	}

	@Override
	public Rectangle hitBox() {
		return this.hitBox;
	}

	@Override
	public HitBox hitBoxTest() {
		// TODO Auto-generated method stub
		return null;
	}
}
