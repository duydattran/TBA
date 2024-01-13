package fr.uge.adventure.gamedata;

import java.util.Objects;

import fr.uge.adventure.gameobject.ElementType;

public record PlayerData(String name, String skin, Position pos, double health) implements ElementData{
	public PlayerData {
		Objects.requireNonNull(name);
		Objects.requireNonNull(skin);
		Objects.requireNonNull(pos);
	}

	@Override
	public ElementType type() {
		return ElementType.Player;
	}
}
