package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class UIManager {

	private List<UIMenu> menus;
	public UIMenu currentMenu;
	public Vec2i position;
	public int selectedItemSlot = 0;
	private Game game;
	private Player player;
	public LoadGameSubMenu loadGameMenu;

	public UIManager(Game game) {
		this.game = game;
		menus = new ArrayList<UIMenu>();
	}

	public void initialisePlayer(Player player) {
		this.player = player;
	}

	public void addMenu(UIMenu menu) {
		menus.add(menu);
	}
	
	public void setLoadMenu(LoadGameSubMenu menu) {
		loadGameMenu = menu;
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

	public boolean addWeapon(PlayerWeapon weapon) {
		if (player.getFirstEmptySlot() != null) {
			int selectSlot = player.getFirstEmptySlot().thisSlot;
			player.getFirstEmptySlot().addWeapon(weapon);
			selectedItemSlot = selectSlot;
			return true;
		} else {
			player.getSelectedSlot().removeWeapon();
			player.getSelectedSlot().addWeapon(weapon);
		}
		return false;
	}

	public void scrollItemSlots(int amount) {
		int n = 0;
		do {
			if (game.getState().canScrollWeapons()) selectedItemSlot += amount;
			if ((selectedItemSlot + amount) > 4) selectedItemSlot = 0;
			if ((selectedItemSlot + amount) < -1) selectedItemSlot = 3;
			n++;
		} while (!player.slots.get(selectedItemSlot).hasItem && n < player.slots.size());
	}

}
