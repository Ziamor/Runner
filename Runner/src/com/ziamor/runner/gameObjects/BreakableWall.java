package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class BreakableWall extends GameObject {

	private int alpha;
	private short[] particleX;
	private short[] particleY;
	private byte[] particleXSpeed;
	private byte[] particleYSpeed;
	private int subImage;
	private boolean startBreak;
	private short particleColorRed;
	private short particleColorGreen;
	private short particleColorBlue;

	public BreakableWall(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "breakableWall";
		this.gobjFactorty = GameObjectFactory.BREAKABLE_WALL;
		this.spriteID = "wallGrass";
		particleColorRed = 211;
		particleColorGreen = 141;
		particleColorBlue = 61;
		spriteOffsetX = -8;
		spriteOffsetY = -8;
		width = 32;
		height = 24;

		alpha = 255;

		particleX = new short[48];
		particleY = new short[48];
		particleXSpeed = new byte[48];
		particleYSpeed = new byte[48];

		subImage = -1;
	}

	public BreakableWall() {
		// TODO Auto-generated constructor stub
	}

	public void update() {

		if (!isActive && isVisible) {
			if (!startBreak) {
				startBreak = true;
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 8; j++) {
						particleX[i * 8 + j] = (short) (x + i * 5);
						particleY[i * 8 + j] = (short) (y + j * 5);
						particleXSpeed[i * 8 + j] = (byte) (5 + Math.random()*i);
						particleYSpeed[i * 8 + j] = (byte) ((int) -(Math.random() * 8) + 4);
					}
				}
			}

			for (int i = 0; i < 48; i++) {
				// particleYSpeed[i] += 0.5;
				particleX[i] += particleXSpeed[i];
				particleY[i] += particleYSpeed[i];
			}

			alpha -= 20;
			if (alpha < 0) {
				isVisible = false;
			}
		}
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

		if (!startBreak) { // if the wall is intact
			g.drawImage(
					TextureCache._textures.get(spriteID).getTexture(
							subImageToDraw, width - spriteOffsetX * 2,
							height - spriteOffsetY * 2), x
							- GamePlayScreen.viewX + getOffsetX()
							+ spriteOffsetX, y - GamePlayScreen.viewY
							+ getOffsetY() + spriteOffsetY, null);
			g.drawImage(TextureCache._textures.get("breakableOverlay")
					.getTexture(), x - GamePlayScreen.viewX + getOffsetX(), y
					- GamePlayScreen.viewY + getOffsetY(), null);
		} else { // else draw particles
			g.setColor(new Color(particleColorRed,particleColorGreen,particleColorBlue,alpha));
			for (int i = 0; i < 48; i++) {
				g.fillRect(particleX[i] - GamePlayScreen.viewX, particleY[i]
						- GamePlayScreen.viewY, 4, 4);
			}
		}
	}

	public void setBitmask() {
		boolean above = false;
		boolean below = false;
		boolean left = false;
		boolean right = false;

		if (this.parent == null)
			return;
		if (this.parent.getGameObjectsByID("wall", "breakableWall").isEmpty())
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
