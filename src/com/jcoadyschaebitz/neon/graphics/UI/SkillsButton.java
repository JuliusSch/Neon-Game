package com.jcoadyschaebitz.neon.graphics.UI;

public class SkillsButton extends UIButton {
	
	public SkillsButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	public void doFunction() {
		uiManager.setMenu(uiManager.shopMenu);
	}
	
	@Override
	public String getId() {
		return "skillsButton";
	}
}
