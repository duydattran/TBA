package fr.uge.adventure.entity;

import java.awt.Rectangle;
import java.util.Objects;

import fr.uge.adventure.collision.HitBox;
import fr.uge.adventure.element.Element;
import fr.uge.adventure.element.ElementType;
import fr.uge.adventure.gamedata.PlayerData;
import fr.uge.adventure.main.Game;
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
	private Rectangle hitBox;
	private final HitBox hitBoxTest;
	
	
	public Player(Game game) {
		Objects.requireNonNull(game);
		this.game = game;
		this.setHealth(game.data().playerData().health());
		this.speed = 4;
		this.name = game.data().playerData().name();
		this.skin = game.data().playerData().skin();
		this.setHealth(game.data().playerData().health());
		
		this.wrldX = (double) game.data().playerData().pos().x() * game.tileSize();
		this.wrldY = (double) game.data().playerData().pos().y() * game.tileSize();
		this.scrX = game.scrWidth() / 2;
		this.scrY = game.scrHeight() / 2;
		this.direction = Direction.UP;
		this.hitBox = new Rectangle(15, 20, (int) (game.tileSize() - 25), (int) (game.tileSize() - 20));
		this.hitBoxTest = new HitBox(15, 20, game.tileSize() - 25, game.tileSize() - 20);
	}
	
	public void update() {
		move();
		hitBoxTest.update(wrldX, wrldY);
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
		
		game.coliCheck().checkTile(this);
		
		wrldX += xSpd;
		wrldY += ySpd;
	}
	
	public HitBox hitBoxTest() {
		return this.hitBoxTest;
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
	public Rectangle hitBox() {
		return this.hitBox;
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
}
