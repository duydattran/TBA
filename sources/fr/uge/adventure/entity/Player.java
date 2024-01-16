package fr.uge.adventure.entity;

import java.util.ArrayList;
import java.util.Objects;
import fr.uge.adventure.collision.HitBox;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.element.ElementType;
import fr.uge.adventure.item.Item;
import fr.uge.adventure.item.ItemType;
import fr.uge.adventure.item.Weapon;
import fr.uge.adventure.main.Game;
import fr.uge.adventure.object.Door;
import fr.uge.adventure.object.GameObject;
import fr.uge.adventure.ulti.Direction;

public class Player implements Element, Entity{
	private double wrldX; private double wrldY;
	private double scrX; private double scrY;
	private double speed; private double xSpd; private double ySpd;
	
	private double health;
	private final String name;
	private final String skin;
	
	private boolean collision;
	private Direction direction;
	private final Game game;
	private final HitBox hitBox;
	
	private PlayerState playerState;
	
	private final double interactRange = 50;
	private final double attackRange = 50;
	
	private Weapon weapon = null;
	private Item item = null;
	
	private final ArrayList<Item> inventory;
	
	public Player(Game game) {
		Objects.requireNonNull(game);
		this.game = game;
		this.setHealth(game.data().playerData().health());
		this.speed = 4;
		this.name = game.data().playerData().name();
		this.skin = game.data().playerData().skin();
		this.setHealth(game.data().playerData().health());
		this.inventory = new ArrayList<Item>();
		this.playerState = PlayerState.normal;
		
		this.wrldX = (double) game.data().playerData().pos().x() * game.tileSize();
		this.wrldY = (double) game.data().playerData().pos().y() * game.tileSize();
		this.scrX = game.scrWidth() / 2;
		this.scrY = game.scrHeight() / 2;
		this.direction = Direction.UP;
		this.hitBox = new HitBox(15, 20, game.tileSize() - 25, game.tileSize() - 20);
	}
	
	public void update() {
		if (playerState == PlayerState.normal)
			move();
		if (playerState != PlayerState.attack && weapon != null)
			attack();
		hitBox.update(wrldX, wrldY);
		game.coliCheck().checkTile(this);
		game.coliCheck().checkEntity(this);
		interact();
		wrldX += xSpd;
		wrldY += ySpd;
	}
	
	private void move() {
		double xDir = 0, yDir = 0;
		double normalizedX, normalizedY;
		xSpd = 0;
		ySpd = 0;
		double length;
		
		if (game.input().rightPressed) {
			xDir = 1;
			direction = Direction.RIGHT;
		}
		if (game.input().leftPressed) {
			xDir = -1;
			direction = Direction.LEFT;
		}
		if (game.input().upPressed) {
			yDir = -1;
			direction = Direction.UP;
		}
		if (game.input().downPressed) {
			yDir = 1;
			direction = Direction.DOWN;
		}
		
		length = Math.sqrt(xDir * xDir + yDir * yDir);
		
		if (length != 0) {
			normalizedX = xDir / length;
			normalizedY = yDir / length;
			xSpd = normalizedX * speed();
			ySpd = normalizedY * speed();
		}
	}
	
	public void attack() {
		if (game.input().spaceTouch) {
			game.input().spaceTouch = false;
			System.out.println("attack");
			playerState = PlayerState.attack;
			Entity entity = game.coliCheck().hitDetectEnemy(this);
			if (entity != null) {
				game.lstEnemy().remove(entity);
			}
		}
	}
	
	public Item pickUpItem() {
		Item item = game.coliCheck().checkItem(this);
		if (item != null) {
			inventory.add(item);
		}
		return item;
	}
	
	public void interact() {
		GameObject object = game.coliCheck().checkObject(this);
		if (object == null)
			return;
		switch(object.objType()) {
		case door:
			object.event(this, true);
			break;
		default:
			break;
		}
	}
	
	public void useItem() {
		int index;
		if (!game.input().spaceTouch)
			return;
		game.input().spaceTouch = false;
		index = game.uiMng().yCursorInv() * 3 + game.uiMng().xCursorInv();
		if (index >= inventory.size())
			return;
		
		Item item = inventory.get(index);
		
		if (item == null)
			return;
			
		switch(item.itemType()) {
		case weapon:
			setWeapon((Weapon) item);
			break;
		case food:
			inventory.remove(item);
			health += 1;
			break;
		case key:
			if (this.item != item)
				this.setItem(item);
			else 
				this.setItem(null);
			break;
		default:
			break;
		}
			
	}
	
	public PlayerState playerState() {
		return this.playerState;
	}
	
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	
	public ArrayList<Item> inventory() {
		return this.inventory;
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
	public double scrX() {
		return this.scrX;
	}

	@Override
	public double scrY() {
		return this.scrY;
	}

	@Override
	public double speed() {
		return this.speed;
	}

	@Override
	public boolean collision() {
		return this.collision;
	}

	@Override
	public ElementType type() {
		return ElementType.Entity;
	}

	@Override
	public Direction direction() {
		return this.direction;
	}
	
	public double xSpd() {
		return this.xSpd;
	}
	
	public double ySpd() {
		return ySpd;
	}

	@Override
	public void setXSpd(double xSpd) {
		this.xSpd = xSpd;
	}

	@Override
	public void setYSpd(double ySpd) {
		this.ySpd = ySpd;
	}

	@Override
	public void setScrX(double scrX) {
		this.scrX = scrX;
	}

	@Override
	public void setScrY(double scrY) {
		this.scrY = scrY;
	}

	public String skin() {
		return this.skin;
	}

	public String name() {
		return name;
	}

	public double health() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	@Override
	public HitBox hitBox() {
		return this.hitBox;
	}

	public Weapon weapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Item item() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public double attackRange() {
		return attackRange;
	}
}
