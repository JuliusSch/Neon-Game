package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.entity.collisionEntities.SlidingDoor;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class DoorKey extends Item {

	private SlidingDoor door; //Change to [implements lockable]
	
	public DoorKey(int x, int y, SlidingDoor door) {
		super(x, y);
		this.door = door;
		sprite = Sprite.keyCard;
	}

	@Override
	protected void doPickUp() {
		door.unlock();
		remove();
	}
}
