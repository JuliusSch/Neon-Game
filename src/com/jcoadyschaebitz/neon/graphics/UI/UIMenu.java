package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class UIMenu implements UIComp {
	
	private List<UIComp> comps;
	
	public UIMenu(UIMenu menu) {
		comps = new ArrayList<UIComp>();
	}

	public void update() {
		for (UIComp comp : comps) {
			comp.update();
		}
		comps.size();
	}
	
	public void addComp(UIComp comp) {
		comps.add(comp);
	}

	public void render(Screen screen) {
		for (UIComp comp : comps) {
			comp.render(screen);
		}
	}

	@Override
	public void activate() {
		for (UIComp comp : comps) comp.activate();
	}

	@Override
	public void deactivate() {
		for (UIComp comp : comps) comp.deactivate();
	}

}
