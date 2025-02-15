package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.input.InputManager.InputAction;

public interface IInteractableItem {
	
	void onInteract(InputAction action);
	
	void setShowPrompt(boolean val);
	
//	protected Keyboard input;
//	protected int interactRadius = 50;
//	protected Sprite key;
//	
//	public InteractableEntity(Keyboard input) {
//		this.input = input;
//	}
//	
//	@Override
//	public void update() {
//		if (input.E && level.isPlayerInRad(centreX, centreY, interactRadius)) interact("E");
//	}
//
//	@Override
//	public void hitReceived(Projectile projectile) {
//	}
	
//	@Override
//	public void render(Screen screen) {
//		screen.renderSprite(getIntX(), getIntY(), sprite, true);
//		if (level.isPlayerInRad(centreX, centreY, interactRadius)) screen.renderSprite(getIntX(), getIntY(), key, true);
//	}
}
