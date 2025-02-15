package com.jcoadyschaebitz.neon.graphics.UI;

public class MapButton extends UIButton {

	public MapButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	public void doFunction() {
		
	}
	
	@Override
	public String getId() {
		return "mapButton";
	}
}
