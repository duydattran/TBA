package fr.uge.adventure.entity;

import java.awt.Rectangle;

import fr.uge.adventure.ulti.Direction;

public interface Entity {
	double wrldX();
	double wrldY();
	double scrX();
	double scrY();
	void setScrX(double scrX);
	void setScrY(double scrY);
	double xSpd();
	double ySpd();
	void setXSpd(double xSpd);
	void setYSpd(double ySpd);
	double speed();
	boolean collision();
	Direction direction();
	Rectangle hitBox();
}
