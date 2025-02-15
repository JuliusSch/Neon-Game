package com.jcoadyschaebitz.neon.save;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.level.Level;

public class SavedGame implements Serializable {

	private static final long serialVersionUID = -5212641408702701608L;
//	public TileCoordinate playerPos;
	public boolean selected = false;
	public Map<String, String> saveData = new HashMap<String, String>();

	public SavedGame(int slot) {
		populateNewFile(slot);
	}

	public SavedGame(String saveName) {
	}

	public void populateNewFile(int slot) {
		saveData.put("TimeCreated", LocalDateTime.now().toString().replace("T", " ").substring(0,
				LocalDateTime.now().toString().length() - 4));
		saveData.put("SaveName", "Save " + slot + ": created - " + saveData.get("TimeCreated"));
		saveData.put("Level", "level_1");
		saveData.put("PlayerPosition",
				Level.getLevelFromName(saveData.get("Level")).getDefaultPlayerSpawn().getString());
	}

	private void saveCurrentState() {
		saveData.put("Level", Game.getUIManager().getGame().getLevel().getLevelName());
		saveData.putAll(Game.getUIManager().getGame().getLevel().getPlayer().getSaveData());
	}

	public void saveGame(String fileName) {
		saveCurrentState();
		try {
			List<String> lines = mapToStringArray(saveData);
			Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SavedGame loadGame(String fileName) {
		SavedGame loadedSave = new SavedGame(fileName);
		try {
			List<String> fileContents = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			for (String line : fileContents) {
				String[] lineBits = line.split(" :: ");
				loadedSave.saveData.put(lineBits[0], lineBits[1]);
			}
		} catch (IOException e) {
			System.err.println("No file found");
			return null;
		}
		return loadedSave;
	}

	private List<String> mapToStringArray(Map<String, String> hashMap) {
		List<String> result = new ArrayList<String>();
		for (Map.Entry<String, String> entry : hashMap.entrySet())
			result.add(entry.getKey() + " :: " + entry.getValue());
		return result;
	}
}
