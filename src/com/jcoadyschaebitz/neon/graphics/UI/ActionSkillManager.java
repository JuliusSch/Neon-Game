package com.jcoadyschaebitz.neon.graphics.UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.ActionSkillDisplay;
import com.jcoadyschaebitz.neon.input.Mouse;

public class ActionSkillManager implements MouseListener {
	
	private Player player;
	private int maxDuration, gracePeriodLength;
	private Shield shield;
	private double cooldown;
	private boolean actionSkillUnlocked = true, inGraceP;

	public boolean shieldActive, twinShieldUnlocked = false;;
	public ActionSkillDisplay actionSkillDisplay;
	public int shieldCount;

	public ActionSkillManager(Player player) {
		this.player = player;
		maxDuration = 180;
		gracePeriodLength = 30;
		actionSkillDisplay = new ActionSkillDisplay(10, 190, Game.getUIManager());
		
	}
	
	public void update() {
		if (!actionSkillUnlocked) return;
		if (!shieldActive && cooldown > 0) cooldown--; 
		actionSkillDisplay.updateStats(shieldActive, (int) cooldown, (int) (maxDuration - cooldown), maxDuration);
		if (Mouse.getB() == 3 && cooldown < maxDuration && shieldActive) {
			cooldown += 1.5;
		}
		if (cooldown <= gracePeriodLength && shieldActive) inGraceP = true;
		else inGraceP = false;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public Shield getShield() {
		if (shield != null) return shield;
		else return null;
	}

	public void mousePressed(MouseEvent e) {
		if (cooldown <= gracePeriodLength && e.getButton() == 3) {
			cooldown = 0;
			shieldActive = true;
			if (shield == null) shield = player.createShield(maxDuration);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 3) {
			shieldActive = false;
			if (shield != null) {
				shield.remove();
				shield = null;
			}
		}
	}
}
