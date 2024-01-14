package fr.uge.adventure.gamedata;

import java.util.HashMap;
import java.util.Objects;

public record ItemData(String name, String skin, Position pos, HashMap<String, String> strData, 
						HashMap<String, Integer> intData) implements ElementData{
	public ItemData {
		Objects.requireNonNull(name);
		Objects.requireNonNull(skin);
		Objects.requireNonNull(pos);
		Objects.requireNonNull(strData);
		Objects.requireNonNull(intData);
	}

	@Override
	public DataType type() {
		return DataType.Item;
	}
}
