package fr.uge.adventure.gamedata;

import java.util.ArrayList;
import java.util.Objects;

public class GameData {
	private final MapData mapData;
	private final PlayerData playerData;
	private final ArrayList<EnemyData> lstEnemyData;
	private final ArrayList<ItemData> lstItemData;
	private final ArrayList<ObjectData> lstObjData;
	
	public GameData(MapData mapData, PlayerData playerData, ArrayList<EnemyData> lstEnemyData,
					ArrayList<ItemData> lstItemData, ArrayList<ObjectData> lstObjData) {
		Objects.requireNonNull(mapData);
		Objects.requireNonNull(playerData);
		Objects.requireNonNull(lstEnemyData);
		Objects.requireNonNull(lstItemData);
		
		this.mapData = mapData;
		this.playerData = playerData;
		this.lstEnemyData = lstEnemyData;
		this.lstItemData = lstItemData;
		this.lstObjData = lstObjData;
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
	
	public ArrayList<ItemData> lstItemData() {
		return lstItemData;
	}

	public ArrayList<ObjectData> lstObjData() {
		return lstObjData;
	}
}
