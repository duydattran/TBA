package fr.uge.adventure.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import fr.uge.adventure.collision.HitBox;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.element.ElementType;
import fr.uge.adventure.gamedata.EnemyData;
import fr.uge.adventure.item.Item;
import fr.uge.adventure.main.Game;
import fr.uge.adventure.ulti.Direction;

public class Enemy implements Element, Entity{
	private double wrldX; private double wrldY;
	private double scrX; private double scrY;
	private double speed; private double xSpd; private double ySpd;
	private double xDir = 0, yDir = 0;
	private int step = 0;
	
	private double health;
	private final String name;
	private final String skin;
	
	private boolean collision;
	private Direction direction;
	private final Game game;
	private final HitBox hitBox;
	
	
	public Enemy(EnemyData enemyData, Game game) {
		Objects.requireNonNull(enemyData);
		Objects.requireNonNull(game);
		this.game = game;
		this.setHealth(enemyData.health());
		this.speed = 4;
		this.name = enemyData.name();
		this.skin = enemyData.skin();
		this.setHealth(enemyData.health());
		
		this.wrldX = (double) enemyData.pos().x() * game.tileSize();
		this.wrldY = (double) enemyData.pos().y() * game.tileSize();
		this.direction = Direction.UP;
		this.hitBox = new HitBox(15, 20, game.tileSize() - 25, game.tileSize() - 20);
	}
	
	public void update() {
		move();
		hitBox.update(wrldX, wrldY);
		game.coliCheck().checkTile(this);
		if (game.coliCheck().checkPlayer(this))
			attack();
		wrldX += xSpd;
		wrldY += ySpd;
	}
	
	private void move() {
		double normalizedX, normalizedY, length;
		xSpd = 0; ySpd = 0;
		
		int[] dir = {-1, 0, 1};
		int index;
		Random random = new Random();
		
		if (step == 0) {
			step = random.nextInt(100, 200);
	        index = random.nextInt(3);
	        xDir = dir[index];
	        index = random.nextInt(3);
	        yDir = dir[index];
		}
		else {
			step--;
		}
		
		if (xDir == 1) {
			direction = Direction.RIGHT;
		}
		if (xDir == -1) {
			direction = Direction.LEFT;
		}
		if (yDir == -1) {
			direction = Direction.UP;
		}
		if (yDir == 1) {
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
	
	private void attack() {
		if (game.player().playerState() == PlayerState.normal) {
			game.player().setHealth(game.player().health() - 1);
			game.player().setPlayerState(PlayerState.hurt);
		}
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

	public double health() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public String name() {
		return name;
	}

	@Override
	public HitBox hitBox() {
		return this.hitBox;
	}

	@Override
	public Item item() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Item> inventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double attackRange() {
		// TODO Auto-generated method stub
		return 0;
	}
}
