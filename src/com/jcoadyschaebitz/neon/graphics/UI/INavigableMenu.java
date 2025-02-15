package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.util.IIdentifiable;

public interface INavigableMenu extends IIdentifiable {

	public enum NavigationDirection {
		UP, DOWN, LEFT, RIGHT
	}
	
	public record NavigableButton (UIButton button, String id, String menuId, String leftId, String upId, String rightId, String downId) {
		
		public String getNavigationId(NavigationDirection direction) {
			switch(direction) {
			case LEFT:
				return leftId;
			case UP:
				return upId;
			case RIGHT:
				return rightId;
			case DOWN:
				return downId;
				default:
			return null;
			}
		}
	}
	
	public void addNavigableButtons(NavigableButton... buttons);
	
	public void setDefaultSelectedButton(String componentId);
}
