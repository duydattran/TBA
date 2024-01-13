package fr.uge.adventure.gamedata;

import java.util.ArrayList;
import java.util.Objects;

public class GameData {
	private final MapData mapData;
	private final PlayerData playerData;
	private final ArrayList<EnemyData> lstEnemyData;
	
	public GameData(MapData mapData, PlayerData playerData, ArrayList<EnemyData> lstEnemyData) {
		Objects.requireNonNull(mapData);
		
		this.mapData = mapData;
		this.playerData = playerData;
		this.lstEnemyData = lstEnemyData;
	}
	
	public MapData map() {
		return this.mapData;
	}

	public PlayerData playerData() {
		return playerData;
	}

	public ArrayList<EnemyData> lstEnemyData() {
		return lstEnemyData;
	}
}
