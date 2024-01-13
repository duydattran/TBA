package fr.uge.adventure.camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;

import fr.uge.adventure.entity.Entity;
import fr.uge.adventure.main.Game;

public class Camera {
	private Entity target; //the target on which the camera will look at
	private static double camSpeed = 0.05;
	private final Game game;
	private double camX; private double camY;
	private int camStartCol; private int camEndCol;
	private int camStartRow; private int camEndRow;
	private final double camWidth; private final double camHeight;
	
	public Camera(Entity target, double camWidth, double camHeight, Game game) {
		Objects.requireNonNull(target);
		this.target = target;
		this.camX = target.wrldX();
		this.camY = target.wrldY();
		this.camStartCol = (int) (camX / game.tileSize()  - camWidth / 2);
		this.camStartRow = (int) (camY / game.tileSize() - camHeight / 2);
		this.camEndCol = (int) (camX / game.tileSize() + camWidth / 2);
		this.camEndRow = (int) (camY / game.tileSize() + camHeight / 2);
		if (camStartCol < 0)
			this.camStartCol = 0;
		if (camStartRow < 0)
			this.camStartRow = 0;
		if (camEndCol > game.tileMap().col())
			this.camEndCol = game.tileMap().col();
		if (camEndRow > game.tileMap().row())
			this.camEndRow = game.tileMap().row();
		this.game = game;
		this.camWidth = camWidth;
		this.camHeight = camHeight;
	}
	
	public void update() {
		camRangeCheck();
		camPosUpdate();
	}
	
	private void camPosUpdate() {
		double targetX = target.wrldX() - camWidth / 2;
		double targetY = target.wrldY() - camHeight / 2;
		camX += (targetX - camX) * camSpeed;
		camY += (targetY - camY) * camSpeed;
		if (camX < 0)
			camX = 0;
		if (camY < 0)
			camY = 0;
		if (camX + camWidth > game.tileMap().col() * game.tileSize())
			camX = game.tileMap().col() * game.tileSize() - camWidth;
		if (camY + camHeight > game.tileMap().row() * game.tileSize())
			camY = game.tileMap().row() * game.tileSize() - camHeight;
	}
	
	public double getCamWidth() {
		return camWidth;
	}

	public double getCamHeight() {
		return camHeight;
	}

	public void camRangeCheck() {
		camStartCol = (int) (camX / game.tileSize());
		camStartRow = (int) (camY / game.tileSize());
		camEndCol = (int) ((camX + camWidth) / game.tileSize() + 2); // +2 to fill the screen
		camEndRow = (int) ((camY + camHeight) / game.tileSize() + 2);
		if (camStartCol < 0)
			camStartCol = 0;
		if (camStartRow < 0)
			camStartRow = 0;
		if (camEndCol > game.tileMap().col())
			camEndCol = game.tileMap().col();
		if (camEndRow > game.tileMap().row())
			camEndRow = game.tileMap().row();
	}
	
	public boolean isEntityInRange(Entity entity) {
		if (camX < entity.wrldX() && entity.wrldX() < camX + camWidth &&
			camY < entity.wrldY() && entity.wrldY() < camY + camHeight) {
			return true;
		}
		return false;
	}
	
	//getters and setters
	
	public double camX() {
		return camX;
	}

	public void setCamX(int cameraX) {
		this.camX = cameraX;
	}

	public double camY() {
		return camY;
	}

	public void setCamY(int cameraY) {
		this.camY = cameraY;
	}
	
	public Entity target() {
		return this.target;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public int cameraStartCol() {
		return camStartCol;
	}

	
	public void setCameraStartCol(int cameraStartCol) {
		this.camStartCol = cameraStartCol;
	}

	public int cameraEndCol() {
		return camEndCol;
	}

	public void setCameraEndCol(int cameraEndCol) {
		this.camEndCol = cameraEndCol;
	}

	public int cameraStartRow() {
		return camStartRow;
	}

	public void setCameraStartRow(int cameraStartRow) {
		this.camStartRow = cameraStartRow;
	}

	public int cameraEndRow() {
		return camEndRow;
	}

	public void setCameraEndRow(int cameraEndRow) {
		this.camEndRow = cameraEndRow;
	}
	
	public double camWidth() {
		return this.camWidth;
	}
	
	public double camHeight() {
		return this.camHeight;
	}
}
