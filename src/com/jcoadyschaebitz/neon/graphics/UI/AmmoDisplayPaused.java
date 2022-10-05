package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class AmmoDisplayPaused implements UIComp {

	private int x, y;
	private Font font;
	private PlayerWeapon weapon1, weapon2, weapon3, weapon4;
	private Player player;

	public AmmoDisplayPaused(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		font = new Font(Font.SIZE_8x8, 0xffBAFFDA, 1);
		this.player = player;
		weapon1 = player.slots.get(0).getWeapon();
		weapon2 = player.slots.get(1).getWeapon();
		weapon3 = player.slots.get(2).getWeapon();
		weapon4 = player.slots.get(3).getWeapon();
	}

	public void update() {
		weapon1 = player.slots.get(0).getWeapon();
		weapon2 = player.slots.get(1).getWeapon();
		weapon3 = player.slots.get(2).getWeapon();
		weapon4 = player.slots.get(3).getWeapon();
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y + 10, Sprite.pistolIcon, false);
		screen.renderSprite(x + 64, y + 10, Sprite.shotgunIcon, false);
		screen.renderSprite(x + 128, y + 10, Sprite.crossbowIcon, false);
		screen.renderSprite(x + 192, y + 10, Sprite.assaultRifleIcon, false);
		font.render(x + 20, y + 10, 1, getAmmoString(weapon1), screen, false);
		font.render(x + 84, y + 10, 1, getAmmoString(weapon2), screen, false);
		font.render(x + 148, y + 10, 1, getAmmoString(weapon3), screen, false);
		font.render(x + 212, y + 10, 1, getAmmoString(weapon4), screen, false);
	}

	public String getAmmoString(PlayerWeapon weapon) {
		String[] strings = new String[2];
		if (weapon != null) {
			strings[0] = Integer.toString(weapon.ammoCount);
			strings[1] = Integer.toString(weapon.maxAmmo);
		} else {
			strings[0] = "-";
			strings[1] = "-";
		}
		return strings[0] + "/" + strings[1];
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

}
