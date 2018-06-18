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
		font = new Font(Font.SIZE_8x8, 0xffBAFFDA);
		this.player = player;
		weapon1 = player.slots.get(0).weapon;
		weapon2 = player.slots.get(1).weapon;
		weapon3 = player.slots.get(2).weapon;
		weapon4 = player.slots.get(3).weapon;
	}

	public void update() {
		weapon1 = player.slots.get(0).weapon;
		weapon2 = player.slots.get(1).weapon;
		weapon3 = player.slots.get(2).weapon;
		weapon4 = player.slots.get(3).weapon;
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, Sprite.pistolIcon, false);
		screen.renderSprite(x, y + 12, Sprite.shotgunIcon, false);
		screen.renderSprite(x, y + 24, Sprite.crossbowIcon, false);
		screen.renderSprite(x, y + 36, Sprite.assaultRifleIcon, false);
		font.render(x + 24, y - 2, 1, getAmmoValues(weapon1)[0] + "/" + getAmmoValues(weapon1)[1], screen, false);
		font.render(x + 24, y + 10, 1, getAmmoValues(weapon2)[0] + "/" + getAmmoValues(weapon2)[1], screen, false);
		font.render(x + 24, y + 22, 1, getAmmoValues(weapon3)[0] + "/" + getAmmoValues(weapon3)[1], screen, false);
		font.render(x + 24, y + 34, 1, getAmmoValues(weapon4)[0] + "/" + getAmmoValues(weapon4)[1], screen, false);
	}

	public String[] getAmmoValues(PlayerWeapon weapon) {
		String[] strings = new String[2];
		if (weapon != null) {
			int totalAmmoCount = weapon.ammoCount;
			strings[1] = Integer.toString(totalAmmoCount);
		} else {
			strings[0] = "0";
			strings[1] = "0";
		}
		return strings;
	}

}
