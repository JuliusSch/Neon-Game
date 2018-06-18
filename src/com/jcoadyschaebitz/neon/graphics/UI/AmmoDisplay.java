package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class AmmoDisplay implements UIComp {

	private Font font;
	private int totalAmmoCount;
	private Player player;

	public AmmoDisplay(Player player) {
		font = new Font(Font.SIZE_8x8, 0xffADFFFF);
		this.player = player;
	}

	public void update() {
		if (player.weapon != null) {
			totalAmmoCount = Player.currentAmmo;
		}
	}

	public int getRemainingAmmo() {
		return totalAmmoCount;
	}

	public void render(Screen screen) {
		font.render(19, 36, 1, Integer.toString(totalAmmoCount), screen, false);
	}

}
