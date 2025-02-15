package com.jcoadyschaebitz.neon.graphics.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.input.IInputObserver;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;

public class UIMenu implements UIComp, INavigableMenu, IInputObserver {
	
	private List<UIComp> comps;
	private List<NavigableButton> navigableButtons;
	private NavigableButton selectedButton;
	private String defaultSelectedButtonId;
	private String menuId;
	
	public UIMenu(UIMenu menu, String menuId) {
		comps = new ArrayList<UIComp>();
		navigableButtons = new ArrayList<NavigableButton>();
		this.menuId = menuId;
	}
	
	@Override
	public String getId() {
		return menuId;
	}

	public void update() {
		for (UIComp comp : comps)
			comp.update();
	}
	
	public void addComps(UIComp... comps) {
		for(UIComp comp : comps)
			this.comps.add(comp);
	}

	public void render(Screen screen) {
		for (UIComp comp : comps)
			comp.render(screen);
	}

	@Override
	public void activate() {
		for (UIComp comp : comps)
			comp.activate();
		
		setSelectedButton(defaultSelectedButtonId);
	}

	@Override
	public void deactivate() {
		for (UIComp comp : comps)
			comp.deactivate();
	}
	
	public void inputTypeChanged(InputType type) {		
		if (type == InputType.GAMEPAD) {
			if (navigableButtons.size() >= 0 && defaultSelectedButtonId != "" && defaultSelectedButtonId != null) {
				navigableButtons.stream()
					.forEach(nb -> nb.button()
					.setHighlighted(false));
				
				navigableButtons.stream()
					.filter(x -> x.id() == defaultSelectedButtonId)
					.findFirst()
					.get()
					.button().setHighlighted(true);
			}
		}
	}

	@Override
	public void addNavigableButtons(NavigableButton... navButtons) {
		for (NavigableButton navButton : navButtons) {
			navigableButtons.add(navButton);
			addComps(navButton.button());
		}
	}

	@Override
	public void setDefaultSelectedButton(String buttonId) {
		defaultSelectedButtonId = buttonId;
		setSelectedButton(defaultSelectedButtonId);
	}
	
	private boolean setSelectedButton(String buttonId) {
		if (buttonId == null || buttonId == "")
			return false;
		
		Optional<NavigableButton> navButton = navigableButtons.stream()
			.filter(x -> x.id().equals(buttonId))
			.findFirst();

		if (navButton.isPresent()) {
			selectedButton = navButton.get();
			
			navigableButtons.stream()
			.forEach(b -> b.button()
			.setHighlighted(false));
			
			selectedButton.button().setHighlighted(true);
			return true;				
		} else {
			System.err.println("Menu Navigation Error: There is no button set up on '" + getId() + "' with name '" + buttonId + "'.");
			return false; 				
		}			
	}

	@Override
	public void InputReceived(InputAction input, double value) {
		if (uiManager.getCurrentInputType() == InputType.GAMEPAD) {
			if (selectedButton == null) // Default selected button not set up
				return;
			
			String newButtonId = null;
			switch (input) {
			case MENU_SELECT:
				if (value == 1.0)
					selectedButton.button().doFunction();
				break;
			case MENU_LEFT:
				newButtonId = selectedButton.leftId();
				break;
			case MENU_UP:
				newButtonId = selectedButton.upId();
				break;
			case MENU_RIGHT:
				newButtonId = selectedButton.rightId();
				break;
			case MENU_DOWN:
				newButtonId = selectedButton.downId();
				break;
			default:
				break;
			}
			if (newButtonId != null && value == 1.0)
				setSelectedButton(newButtonId);
			
		}
	}
}
