package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.List;
import java.util.ArrayList;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class UIMenu implements UIComp {
	
	private List<UIComp> comps;
	
	public UIMenu() {
		comps = new ArrayList<UIComp>();
	}

	public void update() {
		for (UIComp comp : comps) {
			comp.update();
		}
	}
	
	public void addComp(UIComp comp) {
		comps.add(comp);
	}

	public void render(Screen screen) {
		for (UIComp comp : comps) {
			comp.render(screen);
		}
	}

}
