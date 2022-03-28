package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class UIItemSlot implements UIComp {

	public PlayerWeapon weapon;
	private static int slotNumber = 0;
	public final int thisSlot;

	public boolean selected = false;
	public boolean hasItem;
	private Player player;
	private Sprite sprite;
	private int x, y;

	public UIItemSlot(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		thisSlot = slotNumber;
		slotNumber++;
		sprite = Sprite.item_slot;
		this.player = player;
	}
	
	public void update() {
		if (ui.selectedItemSlot == thisSlot) {
			selected = true;
			player.setGun(weapon);
		} else selected = false;
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, false);
		if (hasItem) screen.renderSprite(x, y, weapon.slotSprite, false);
		if (selected) {
			screen.renderSprite(x, y, Sprite.item_slot_outline, false);
			screen.renderTranslucentSprite(x, y, Sprite.item_slot_glow, false, 0.2);
		}
	}
	
	public PlayerWeapon getWeapon() {
		return weapon;
	}

	public void addWeapon(PlayerWeapon weapon) {
		this.weapon = weapon;
		hasItem = true;
	}

	public void removeWeapon() {
		this.weapon = null;
		hasItem = false;
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
