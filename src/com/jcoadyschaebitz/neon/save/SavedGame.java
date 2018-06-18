package com.jcoadyschaebitz.neon.save;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.level.Level;

public class SavedGame implements Serializable {
	
	private static final long serialVersionUID = -5212641408702701608L;
	Level level;
	Player player;

	public SavedGame(String name, Level level, Player player) {
		this.level = level;
		this.player = player;
	}
	
	public static void saveGame(SavedGame file, String fileName) {
		try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
			out.writeObject(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: failed to save game - " + fileName);
		}
	}
	
	public static Object loadGame(String fileName) {
		try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
			return in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: failed to load game - " + fileName);
			return null;
		}
	}
	
}
