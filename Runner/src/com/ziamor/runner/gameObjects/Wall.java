package com.ziamor.runner.gameObjects;

import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class Wall extends GameObject {

	private int subImage;

	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "wall";
		this.gobjFactorty = GameObjectFactory.WALL;
		this.spriteID = "wallGrass";
		width = 32;
		height = 24;
		spriteOffsetX = -8;
		spriteOffsetY = -8;
		subImage = -1;
	}

	public Wall() {
		this.x = -1;
		this.y = -1;
		this.objID = "wall";
		this.gobjFactorty = GameObjectFactory.WALL;
		width = 32;
		height = 24;
		subImage = -1;
	}

	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		// set the bitmask, if it hasn't already
		if (subImage == -1)
			setBitmask();
		
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		int subImageToDraw = subImage;
		if (subImage == -1)
			subImageToDraw = 0;
		
		g.drawImage(
				TextureCache._textures.get(spriteID).getTexture(subImageToDraw,
						width - spriteOffsetX * 2, height - spriteOffsetY * 2),
				x - GamePlayScreen.viewX + getOffsetX() + spriteOffsetX, y
						- GamePlayScreen.viewY + getOffsetY() + spriteOffsetY,
				null);
	}

	public void setBitmask() {
		boolean above = false;
		boolean below = false;
		boolean left = false;
		boolean right = false;

		if (this.parent == null)
			return;
		if (this.parent.getGameObjectsByID("wall",
				"breakableWall").isEmpty())
			return;
		
		// loop through every wall to check for collisions
		for (GameObject gameObject : this.parent.getGameObjectsByID("wall",
				"breakableWall")) {
			if (gameObject.isActive() && !gameObject.equals(this)) {
				// check above
				y = y - 1;
				if (CollisionHandler.isColliding(this, gameObject))
					above = true;
				// check below
				y = y + 2;
				if (CollisionHandler.isColliding(this, gameObject))
					below = true;
				y = y - 1;
				// check left
				x = x - 1;
				if (CollisionHandler.isColliding(this, gameObject))
					left = true;
				// check right
				x = x + 2;
				if (CollisionHandler.isColliding(this, gameObject))
					right = true;
				x = x - 1;
			}
		}

		// set the proper subImage
		if (above) {
			if (below) {
				if (left) {
					if (right) {
						subImage = 15;
					} else {
						subImage = 14;
					}
				} else {
					if (right) {
						subImage = 13;
					} else {
						subImage = 12;
					}
				}
			} else {
				if (left) {
					if (right) {
						subImage = 11;
					} else {
						subImage = 10;
					}
				} else {
					if (right) {
						subImage = 9;
					} else {
						subImage = 8;
					}
				}
			}
		} else {
			if (below) {
				if (left) {
					if (right) {
						subImage = 7;
					} else {
						subImage = 6;
					}
				} else {
					if (right) {
						subImage = 5;
					} else {
						subImage = 4;
					}
				}
			} else {
				if (left) {
					if (right) {
						subImage = 3;
					} else {
						subImage = 2;
					}
				} else {
					if (right) {
						subImage = 1;
					} else {
						subImage = 0;
					}
				}
			}
		}
		
	}
}
