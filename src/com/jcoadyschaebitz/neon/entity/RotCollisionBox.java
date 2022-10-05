package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.particle.DebugParticle;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class RotCollisionBox extends CollisionBox {

	private int[] rotXValues, rotYValues;

	public RotCollisionBox(int[] xV, int[] yV) {
		super(xV, yV);
		rotXValues = xV.clone();
		rotYValues = yV.clone();
	}

	public void setValues(int[] x, int[] y) {
		rotXValues = x.clone();
		rotYValues = y.clone();
	}

	public void offsetPoints(int offsetX, int offsetY) {
		for (int i = 0; i < rotXValues.length; i++) {
			rotXValues[i] += offsetX;
			rotYValues[i] += offsetY;
		}
	}

	public void rotatePoints(double angle, int width, int height) {
		for (int i = 0; i < origXValues.length; i++) {
			Vec2i rotP = rotatePoint(new Vec2i((width / 2), (height / 2)), angle, new Vec2i(origXValues[i], origYValues[i]));
			rotXValues[i] = rotP.x;
			rotYValues[i] = rotP.y;
		}
	}
	
	public Vec2i rotatePoint(Vec2i rotateAround, double angle, Vec2i p) {
		p = p.subtract(rotateAround);
		double angle1 = Math.atan((double) p.y / (double) p.x);
		double angle2 = angle1 + angle;
		double z = p.y / Math.sin(angle1);

		double x1 = z * Math.cos(angle2);
		double y1 = z * Math.sin(angle2);
		Vec2i result = new Vec2i((int) Math.round(x1), (int) Math.round(y1));
		return result.add(rotateAround);
	}

	public boolean checkForCollisions(Projectile p, int x, int y, Entity e, boolean rotated) {
		if (!rotated) {
			int px0, px1, py0, py1;
			px0 = p.entityBounds.getXValues()[0] + x;
			px1 = p.entityBounds.getXValues()[1] + x;
			py0 = p.entityBounds.getYValues()[0] + y;
			py1 = p.entityBounds.getYValues()[2] + y;
			for (int i = 0; i < rotXValues.length; i++) {
				if ((rotXValues[i] + e.getIntX()) > px0 && (rotXValues[i] + e.getIntX()) < px1) {
					if ((rotYValues[i] + e.getIntY()) > py0 && (rotYValues[i] + e.getIntY()) < py1) {
						return true;
					}
				}
			}
			return false;
		} else {
			// two rotaties at once
			int px0, px1, py0, py1;
			px0 = p.getCollisionBounds().getXValues()[0] + x;
			py0 = p.getCollisionBounds().getYValues()[0] + y;
			px1 = p.getCollisionBounds().getXValues()[1] + x;
			py1 = p.getCollisionBounds().getYValues()[2] + y;
			Vec2i rotateAround = (new Vec2i(x + p.getSpriteW() / 2, y + p.getSpriteH() / 2).subtract(e.getPos())).add(e.getSpriteW() / 2, e.getSpriteH() / 2);
			for (int i = 0; i < rotXValues.length; i++) {
				Vec2i rotPoint = rotatePoint(rotateAround, p.angle * -1, new Vec2i(rotXValues[i], rotYValues[i]));
				Game.getUIManager().getGame().getLevel().add(new DebugParticle((rotPoint.x + e.getIntX()), rotPoint.y + e.getIntY(), 300, Sprite.smallParticleLime));
				if ((rotPoint.x + e.getIntX()) > px0 && (rotPoint.x + e.getIntX()) < px1) {
					if ((rotPoint.y + e.getIntY()) > py0 && (rotPoint.y + e.getIntY()) < py1) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public void renderBounds(Screen screen, int colour, int x, int y) {
		for (int i = 0; i < rotXValues.length; i++) {
			screen.renderPixel(rotXValues[i] + x, rotYValues[i] + y, colour);
			Game.getUIManager().getGame().getLevel().add(new DebugParticle(origXValues[i] + x, origYValues[i] + y, 300, Sprite.smallParticleCrimson));
		}
	}
}
