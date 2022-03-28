package com.jcoadyschaebitz.neon.graphics.UI;

import java.time.LocalDateTime;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.save.SavedGame;

public class SaveSlot extends UIButton {
	
	private boolean empty = true;
	public SavedGame savedGame;
	public int slotNumber;
	private Player player;
	private Level level;

	public SaveSlot(int x, int y, int width, int height, String label, int fontColour, int slotNumber, Player player, Level level) {
		super(x, y, width, height, label, fontColour);
		this.slotNumber = slotNumber;
		this.player = player;
		this.level = level;
	}
	
	public SavedGame getSave() {
		return savedGame;
	}

	@Override
	public void doFunction() {
		if (empty) createSaveGame();
		else setSelected();
	}
	
	private void createSaveGame() {
		empty = false;
		Game.getUIManager().loadGameMenu.setSelectedSlot(slotNumber);
		savedGame = new SavedGame(level.levelName, player, label);
		savedGame.timeCreated = LocalDateTime.now().toString().replace("T", " ").substring(0, LocalDateTime.now().toString().length() - 4);
		savedGame.saveName = label = "Save " + slotNumber + ": created - "+ savedGame.timeCreated;
		setSelected();
	}
	
	public boolean tryLoadSave(int slotNumber) {
			savedGame = (SavedGame) SavedGame.loadGame("saves/save_" + slotNumber);
			if (savedGame == null) return false;
			empty = false;
			label = savedGame.saveName;
			if (savedGame.selected) return true;
			return false;
	}
	
	private void setSelected() {
		Game.getUIManager().loadGameMenu.setSelectedSlot(slotNumber);
		savedGame.selected = true;
	}
	
	public void render(Screen screen) {
		if (!empty && savedGame.selected) {
			font.render(x + 6, y + 10, 0, label, screen, false);
			screen.renderSprite(x, y, defaultSprite, false);						
		} else {
			screen.renderTranslucentSprite(x, y, defaultSprite, false, 0.3);
			font.render(x + 6, y + 10, label, screen, false, 0.3);			
		}
		if (buttonHighlighted) screen.renderTranslucentSprite(x, y, highlightedSprite, false, 0.2);
	}

}
