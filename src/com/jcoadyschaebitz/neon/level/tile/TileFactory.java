package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Sprite;

public class TileFactory {
	
	public enum TileType {
		FLOOR, WALL, STAIR_LEFT, STAIR_RIGHT, STAIR_UP_DOWN 
	}
	
	public enum TileModifier {
		BLOCK_SIGHT, BLOCK_PROJECTILES, CASTS_SHADOW, IS_OUTDOORS
	}

	public static Tile createTile(int colour, Sprite sprite, TileType type, TileModifier[] modifiers, Renderer[] renderers) {
		Tile output = Tile.voidTile;
		switch(type) {
		case FLOOR:
			output = new FloorTile(sprite, colour, renderers);
			break;
		case WALL:
			output = new WallTile(sprite, colour, renderers);
			break;
		case STAIR_LEFT:
			break;
		case STAIR_RIGHT:
			break;
		case STAIR_UP_DOWN:
			break;
		default:
			output = Tile.voidTile;
			break;
		}
		for (TileModifier m : modifiers) {
//			if (m == TileModifier.BLOCK_SIGHT) output.blocksSightline = true;
			if (m == TileModifier.BLOCK_PROJECTILES) output.blocksProjectiles = true;
//			if (m == TileModifier.BLOCK_AI) output.blockAIMovement = true;
			if (m == TileModifier.CASTS_SHADOW) output.castsShadow = true;
			if (m == TileModifier.IS_OUTDOORS) output.isOutdoors = true;
		}
		return output;
	}
	
	public static Tile createTile(int colour, Sprite[] sprites, TileType type, TileModifier[] modifiers) {
		return null;
	}
	
}
