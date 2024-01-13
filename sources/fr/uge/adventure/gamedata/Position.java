package fr.uge.adventure.gamedata;

public record Position(int x, int y) {
	public Position {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException("Position attribute can't be negative");
	}
}
