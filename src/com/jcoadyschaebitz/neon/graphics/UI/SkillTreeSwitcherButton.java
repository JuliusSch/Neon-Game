package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.SkillTreeManager;

public class SkillTreeSwitcherButton extends UIButton {
	
	public enum Pointing {LEFT, RIGHT} 
	private Pointing d;
	private SkillTreeManager skm;

	public SkillTreeSwitcherButton(int x, int y, int width, int height, String label, int fontColour, SkillTreeManager skm, Pointing d) {
		super(x, y, width, height, label, fontColour);
		this.d = d;
		this.skm = skm;
		if (d == Pointing.LEFT) defaultSprite = Sprite.leftButton;
		else defaultSprite = Sprite.rightButton;
		if (d == Pointing.LEFT) highlightedSprite = Sprite.leftButtonHighlighted;
		else highlightedSprite = Sprite.rightButtonHighlighted;
	}

	public void doFunction() {
		if (d == Pointing.LEFT) skm.changeTree(-1);
		else skm.changeTree(1);
	}

}
