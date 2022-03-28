package com.jcoadyschaebitz.neon.save;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;

public class SavedGame implements Serializable {
	
	private static final long serialVersionUID = -5212641408702701608L;
	public String level;
//	public TileCoordinate playerPos;
//	public Player player;
	public String saveName;
	public String timeCreated;
	public boolean selected = false;

	public SavedGame(String level, Player player, String saveName) {
		this.level = level;
//		this.player = player;
		this.saveName = saveName;
	}
	
	public void saveGame(String fileName) {
		level = Game.getUIManager().getGame().getLevel().getLevelName();
//		playerPos = Game.getUIManager().getGame().getLevel().getPlayerSpawn();
		try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
//			System.out.println(out.toString());
			out.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: failed to save game - " + fileName);
		}
	}
	
	public static Object loadGame(String fileName) {
		System.out.println(fileName);
		try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
			Object o = in.available();
			System.out.println(o);
			return in.readObject();
		} catch (Exception e) {
			System.err.println("Error: failed to load game - " + fileName);
			return null;
		}
	}
	
}
