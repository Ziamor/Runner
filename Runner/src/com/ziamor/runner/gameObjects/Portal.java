package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.LevelEditScreen;

public class Portal extends GameObject {

	private int amount;
	private int[] particleX;
	private int[] particleY;
	private int[] particleXSpeed;
	private int[] particleYSpeed;

	public Portal(int x, int y, boolean endPortal) {
		this.objID = "portal";
		this.x = x;
		this.y = y;

		if (endPortal) {
			this.gobjFactorty = GameObjectFactory.EPORTAL;
			offsetY = -y;
			height = y + 48;
		} else {
			this.gobjFactorty = GameObjectFactory.BLANK;
			offsetY = -y;
			height = GamePlayScreen.levelHeight;
		}

		width = 96;

		offsetX = -32;

		amount = height/ 10;

		particleX = new int[amount];
		particleY = new int[amount];
		particleXSpeed = new int[amount];
		particleYSpeed = new int[amount];

		for (int i = 0; i < amount; i++) {
			particleX[i] = (int) (this.x + Math.random() * (width - 5));
			particleY[i] = (int) (y + Math.random() * (height - 5));
			particleYSpeed[i] = (int) (-(Math.random() * 3 + 2));
		}
	}

	public void update() {
		for (int i = 0; i < amount; i++) {
			if (particleX[i] > x + width / 2)
				particleXSpeed[i] += -1;
			else
				particleXSpeed[i] += 1;
			if (particleY[i] < y) {
				particleY[i] = (int) (y + height - Math.random() * 100);
				particleYSpeed[i] = (int) (-(Math.random() * 3 + 2));
			}
			particleX[i] += particleXSpeed[i];
			particleY[i] += particleYSpeed[i];
		}
	}

	public void paintComponent(Graphics g) {
		if (LevelEditScreen.editing) {
			LevelEditScreen.endPortalX = x;
			LevelEditScreen.endPortalY = y;
			g.setColor(Color.blue);
			g.drawRect(x + 2 - GamePlayScreen.viewX, y + 2
					- GamePlayScreen.viewY, 28, 44);
			g.drawRect(x + 4 - GamePlayScreen.viewX, y + 4
					- GamePlayScreen.viewY, 24, 40);
			return; // don't draw the portals while editing
		}

		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(Color.cyan);
		g.fillRect(x - GamePlayScreen.viewX + offsetX + spriteOffsetX, y
				- GamePlayScreen.viewY + offsetY + spriteOffsetY, width, height);

		g.setColor(Color.blue);
		for (int i = 0; i < amount; i++) {
			g.fillRect(particleX[i] - GamePlayScreen.viewX + offsetX
					+ spriteOffsetX, particleY[i] - GamePlayScreen.viewY
					+ offsetY + spriteOffsetY, 5, 5);
		}
	}

}