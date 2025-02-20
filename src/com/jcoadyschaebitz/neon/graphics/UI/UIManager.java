package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.INavigableMenu.NavigableButton;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeManager;
import com.jcoadyschaebitz.neon.input.IInputObserver;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class UIManager implements IInputObserver {

	private List<UIMenu> menus;
	private Game game;
	private Player player;
	private InputType inputType = InputType.KEYBOARD;

	public UIMenu shopMenu, pauseSettingsMenu, gamePlayUI, cutSceneUI, mainMenu, loadMenu, deadMenu;	
	public UIMenu currentMenu;
	public Vec2i position;
	public int selectedItemSlot = 0;
	public LoadGameSubMenu loadGameSubMenu;
//	public LoadGameSubMenu loadSaveSelection;
	
	public HealthBar healthBar;
	public UIItemSlot itemSlot1, itemSlot2, itemSlot3, itemSlot4;
	public XPDisplay xpDisplay;
	public SkillTreeManager skillTreeManager;
	public Shield shield;
	private AmmoDisplay ammoDisplay;
	private ResumeButton resumeButton;
	private SettingsButton settingsButton;
//	private SkillsButton skillsButton;
	private SaveAndExitButton saveAndExitButton;
	private MapButton mapButton;
	private LoadMenuButton loadMenuButton;
	private AmmoDisplayPaused ammoDisplayPaused;
	private PlayButton playButton;
	private ExitButton exitButton;
	private MainMenuButton mainMenuButton;
	private RespawnButton respawnButton;
	public List<UIItemSlot> slots = new ArrayList<UIItemSlot>();
	
	
	public UIManager(Game game) {
		this.game = game;
		menus = new ArrayList<UIMenu>();
		Game.getInputManager().registerObserver(this);
	}
	
	public void initialiseMenus() {
		
		// Menus
		shopMenu = new UIMenu(null, "shopMenu");
		pauseSettingsMenu = new UIMenu(null, "pauseSettingsMenu");
		gamePlayUI = new UIMenu(null, "gamePlayUI");
		cutSceneUI = new UIMenu(null, "cutSceneUI");
		mainMenu = new UIMenu(null, "mainMenu");
		loadMenu = new UIMenu(null, "loadMenu");
		deadMenu = new UIMenu(null, "deadMenu");
		
		// Main Menu
		playButton = new PlayButton(60, 120, 61, 37, "Play", 0xffADFFFF);
		loadMenuButton = new LoadMenuButton(60, 160, 61, 37, "Load", 0xffADFFFF);
		exitButton = new ExitButton(60, 200, 61, 37, "Exit", 0xffADFFFF);
		
		mainMenu.addComps(playButton, loadMenuButton, exitButton);
		mainMenu.addNavigableButtons(
			new NavigableButton(playButton, playButton.getId(), mainMenu.getId(), null, exitButton.getId(), null, loadMenuButton.getId()),
			new NavigableButton(loadMenuButton, loadMenuButton.getId(), mainMenu.getId(), null, playButton.getId(), null, exitButton.getId()),
			new NavigableButton(exitButton, exitButton.getId(), mainMenu.getId(), null, loadMenuButton.getId(), null, playButton.getId())
		);
		mainMenu.setDefaultSelectedButton(playButton.getId());

		//Load Game Menu
		mainMenuButton = new MainMenuButton(60, 200, 61, 37, "Back", 0xffADFFFF);
		loadGameSubMenu = new LoadGameSubMenu(122, 122, player, game.getLevel());
		loadMenu.addComps(playButton, mainMenuButton, /* respawnButton, */loadGameSubMenu);
		loadGameSubMenu.initialiseButtonNavigation(playButton, mainMenuButton, loadMenu);
		loadMenu.setDefaultSelectedButton(playButton.getId()); // Change to selected save slot. How should this work with sub-menus?

		// Pause Menu
		skillTreeManager = new SkillTreeManager(game, player);
//		actionSkillManager = new ActionSkillManager(player);
		resumeButton = new ResumeButton(13, 27, 61, 37, "Continue", 0xffADFFFF);
		settingsButton = new SettingsButton(13, 67, 61, 37, "Settings", 0xffADFFFF);
//		skillsButton = new SkillsButton(13, 107, 61, 37, "Skills", 0xffADFFFF, game);
		mapButton = new MapButton(13, 147, 61, 37, "Map", 0xffADFFFF);
		saveAndExitButton = new SaveAndExitButton(13, 187, 61, 37, "Exit", 0xffADFFFF);
		ammoDisplayPaused = new AmmoDisplayPaused(120, 30);

		pauseSettingsMenu.addComps(/*skillsButton, */resumeButton, settingsButton, mapButton, saveAndExitButton);
		pauseSettingsMenu.addNavigableButtons(
			new NavigableButton(resumeButton, resumeButton.getId(), pauseSettingsMenu.getId(), null, saveAndExitButton.getId(), null, settingsButton.getId()),
			new NavigableButton(settingsButton, settingsButton.getId(), pauseSettingsMenu.getId(), null, resumeButton.getId(), null, mapButton.getId()),
			new NavigableButton(mapButton, mapButton.getId(), pauseSettingsMenu.getId(), null, settingsButton.getId(), null, saveAndExitButton.getId()),
			new NavigableButton(saveAndExitButton, saveAndExitButton.getId(), pauseSettingsMenu.getId(), null, mapButton.getId(), null, resumeButton.getId())
		);
		pauseSettingsMenu.setDefaultSelectedButton(resumeButton.getId());
		
		// In Game UI
		healthBar = new HealthBar(19, 10, 80, 5, 0xffBC0045, 0xffff0059, this);
		itemSlot1 = new UIItemSlot(18, 18, player);
		itemSlot2 = new UIItemSlot(34, 18, player);
		itemSlot3 = new UIItemSlot(50, 18, player);
		itemSlot4 = new UIItemSlot(66, 18, player);
		xpDisplay = new XPDisplay(84, 21, this);
		ammoDisplay = new AmmoDisplay(player);
		
		slots.add(itemSlot1);
		slots.add(itemSlot2);
		slots.add(itemSlot3);
		slots.add(itemSlot4);
		healthBar.setMaxValue(player.maxHealth);
		healthBar.setValue(player.getHealth());
		shield = skillTreeManager.getShield();
		
		gamePlayUI.addComps(healthBar, itemSlot1, itemSlot2, itemSlot3, itemSlot4, ammoDisplay, xpDisplay, shield.actionSkillDisplay);

		// Shop Menu
		shopMenu.addComps(/*resumeButton, settingsButton, skillsButton, mapButton, saveButton, */skillTreeManager, ammoDisplayPaused);

		// CutScene UI
//		game.cutSceneUI.addComp(skipButton);	add skip button as button (find out where thats currently being done.
		
		// Death Screen
		respawnButton = new RespawnButton(60, 30, 60, 30, "Continue", 0xffADFFFF);
		
		deadMenu.addComps(respawnButton);
		deadMenu.addNavigableButtons(
			new NavigableButton(respawnButton, respawnButton.getId(), deadMenu.getId(), null, null, null, null)
		);
		deadMenu.setDefaultSelectedButton(respawnButton.getId());
				
//		addMenu(shopMenu);
		setMenu(mainMenu);
	}

	public void initialisePlayer(Player player) {
		this.player = player;
	}

	public void addMenu(UIMenu menu) {
		menus.add(menu);
	}
	
	public void setLoadMenu(LoadGameSubMenu menu) {
		loadGameSubMenu = menu;
	}

	public Game getGame() {
		return game;
	}

	public void update() {
		currentMenu.update();
	}

	public void render(Screen screen) {
		currentMenu.render(screen);
	}

	public void setMenu(UIMenu menu) {
		if (currentMenu != null) currentMenu.deactivate();
		currentMenu = menu;
		currentMenu.activate();
	}
	
	public InputType getCurrentInputType() {
		return inputType;
	}
	
	public void inputTypeChanged(InputType type) {
		inputType = type;
		currentMenu.inputTypeChanged(inputType);
	}

	public boolean addWeapon(PlayerWeapon weapon) {
		UIItemSlot slot = getFirstEmptySlot();
		if (slot != null) {
			slot.addWeapon(weapon);
			selectedItemSlot = slot.thisSlot;
			return true;
		} else {
			getSelectedSlot().removeWeapon();
			getSelectedSlot().addWeapon(weapon);
		}
		return false;
	}
	
	private UIItemSlot getFirstEmptySlot() {
		if (!itemSlot1.hasItem) return itemSlot1;
		if (!itemSlot2.hasItem) return itemSlot2;
		if (!itemSlot3.hasItem) return itemSlot3;
		if (!itemSlot4.hasItem) return itemSlot4;
		return null;
	}
	
	private UIItemSlot getSelectedSlot() {
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).selected)
				return slots.get(i);
		}
		return slots.get(0);
	}
	
	public void switchGunsToLevel(Level newL) {
		for (UIItemSlot s : slots) {
			try {
				newL.add(s.getWeapon());
			} catch (NullPointerException e) {
			}
		}
	}

	public void scrollItemSlots(int amount) {
		int n = 0;
		do {
			n++;
			if (game.getState().canScrollWeapons()) selectedItemSlot += amount;
			if ((selectedItemSlot + amount) > 4) selectedItemSlot = 0;
			if ((selectedItemSlot + amount) < -1) selectedItemSlot = 3;
		} while (!slots.get(selectedItemSlot).hasItem && n < slots.size());
	}

	@Override
	public void InputReceived(InputAction input, double value) {
		currentMenu.InputReceived(input, value);
		
		if (value == 1f) {
			if (input == InputAction.PAUSE)
				game.togglePause();
			
			if (input == InputAction.CYCLE_ITEM_LEFT)
				scrollItemSlots(-1);
			else if (input == InputAction.CYCLE_ITEM_RIGHT)
				scrollItemSlots(1);
		}
	}
	
	public Map<String, String> getSaveData() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("Slot1Weapon", itemSlot1.hasItem ? itemSlot1.getWeapon().toString() : "null");
		result.put("Slot2Weapon", itemSlot2.hasItem ? itemSlot2.getWeapon().toString() : "null");
		result.put("Slot3Weapon", itemSlot3.hasItem ? itemSlot3.getWeapon().toString() : "null");
		result.put("Slot4Weapon", itemSlot4.hasItem ? itemSlot4.getWeapon().toString() : "null");
		result.put("Slot1Ammo", itemSlot1.hasItem ? Integer.toString(itemSlot1.getWeapon().ammoCount) : "null");
		result.put("Slot2Ammo", itemSlot2.hasItem ? Integer.toString(itemSlot2.getWeapon().ammoCount) : "null");
		result.put("Slot3Ammo", itemSlot3.hasItem ? Integer.toString(itemSlot3.getWeapon().ammoCount) : "null");
		result.put("Slot4Ammo", itemSlot4.hasItem ? Integer.toString(itemSlot4.getWeapon().ammoCount) : "null");
		return result;
	}

}
