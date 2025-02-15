package com.jcoadyschaebitz.neon.graphics.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.INavigableMenu.NavigableButton;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.save.SavedGame;

public class LoadGameSubMenu implements UIComp {

	private int x, y;
	private List<SaveSlot> saveSlots;
	private int selectedSlot, noOfSlots = 5;
	private boolean compActive;

	public LoadGameSubMenu(int x, int y, Player player, Level level) {
		this.x = x;
		this.y = y;
		saveSlots = new ArrayList<SaveSlot>();
		for (int i = 0; i < noOfSlots; i++) {
			SaveSlot slot = new SaveSlot(x + 4, y + 4 + i * 32, 236, 32, "Empty save", 0xffADFFFF, i);
			saveSlots.add(slot);
			slot.tryLoadSave(i);
			Game.getUIManager().loadMenu.addComps(slot);
		}
		Game.getUIManager().setLoadMenu(this);
		loadState();
	}
	
	public SavedGame getSelectedSave() {
		return saveSlots.get(selectedSlot).getSavedGame();
	}
	
	public void saveState() {
		List<String> lines = Arrays.asList(String.valueOf(selectedSlot), "The second line");
		Path file = Paths.get("game.txt");
		try {
			Files.write(file, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Error saving game data :(");
		}
	}
	
	public void loadState() {
		Path path = Paths.get("game.txt");
		try {
			List<String> lines = Files.readAllLines(path);
			setSelectedSlot(Integer.parseInt(lines.get(0)));
		} catch (IOException e) {
			System.err.println("game data not found");
		}
	}
	
	public void setSelectedSlot(int slotNumber) {
		selectedSlot = slotNumber;
		for (SaveSlot slot : saveSlots) if (slot.savedGame != null && slot.slotNumber != selectedSlot) slot.savedGame.selected = false;
	}

	public void initialiseButtonNavigation(UIButton playButton, UIButton mainMenuButton, UIMenu loadMenu) {
		loadMenu.addNavigableButtons(
			new NavigableButton(playButton, playButton.getId(), loadMenu.getId(), null, mainMenuButton.getId(), saveSlots.get(0).getId(), mainMenuButton.getId()),
			new NavigableButton(mainMenuButton, mainMenuButton.getId(), loadMenu.getId(), null, playButton.getId(), saveSlots.get(0).getId(), playButton.getId()),
			new NavigableButton(saveSlots.get(0), saveSlots.get(0).getId(), loadMenu.getId(), playButton.getId(), null, null, saveSlots.get(1).getId()),
			new NavigableButton(saveSlots.get(1), saveSlots.get(1).getId(), loadMenu.getId(), playButton.getId(), saveSlots.get(0).getId(), null, saveSlots.get(2).getId()),
			new NavigableButton(saveSlots.get(2), saveSlots.get(2).getId(), loadMenu.getId(), playButton.getId(), saveSlots.get(1).getId(), null, saveSlots.get(3).getId()),
			new NavigableButton(saveSlots.get(3), saveSlots.get(3).getId(), loadMenu.getId(), playButton.getId(), saveSlots.get(2).getId(), null, saveSlots.get(4).getId()),
			new NavigableButton(saveSlots.get(4), saveSlots.get(4).getId(), loadMenu.getId(), playButton.getId(), saveSlots.get(3).getId(), null, null)
		);
	}
	
	@Override
	public void update() {
	}
	
	public void saveToSelectedSlot() {
		saveSlots.get(selectedSlot).getSave().saveGame("saves/save_" + selectedSlot);
	}

	@Override
	public void render(Screen screen) {
		if (!compActive) return;
		screen.renderSprite(x, y, Sprite.loadMenuOutline, false);
		for (int i = 0; i < saveSlots.size(); i++) saveSlots.get(i).render(screen);
	}

	@Override
	public void activate() {
		compActive = true;
		for (int i = 0; i < saveSlots.size(); i++) saveSlots.get(i).buttonActive = true;
	}

	@Override
	public void deactivate() {
		compActive = false;
		for (int i = 0; i < saveSlots.size(); i++) saveSlots.get(i).buttonActive = false;
	}
}
