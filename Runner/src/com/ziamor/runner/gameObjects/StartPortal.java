package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.LevelEditScreen;

public class StartPortal extends GameObject {

	public static int x;
	private int amount;
	private int[] particleX;
	private int[] particleY;
	private int[] particleXSpeed;
	private int[] particleYSpeed;

	public StartPortal(int x, int y) {
		this.objID = "startPortal";
		StartPortal.x = x;
		this.y = y;

		setOffsetY(-y);
		height = GamePlayScreen.levelHeight;
		width = 96;
		setOffsetX(-32);

		amount = height / 10;

		particleX = new int[amount];
		particleY = new int[amount];
		particleXSpeed = new int[amount];
		particleYSpeed = new int[amount];

		for (int i = 0; i < amount; i++) {
			particleX[i] = (int) (Math.random() * (width - 5));
			particleY[i] = (int) (Math.random() * (height - 5));
			particleYSpeed[i] = (int) (-(Math.random() * 3 + 2));
		}
	}

	public void update() {

		for (int i = 0; i < amount; i++) {
			if (particleX[i] > width / 2)
				particleXSpeed[i] += -1;
			else
				particleXSpeed[i] += 1;
			if (particleY[i] < y) {
				particleY[i] = (int) (height - Math.random() * 100);
				particleYSpeed[i] = (int) (-(Math.random() * 3 + 2));
			}
			particleX[i] += particleXSpeed[i];
			particleY[i] += particleYSpeed[i];
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(Color.cyan);
		g.fillRect(x - GamePlayScreen.viewX + getOffsetX() + spriteOffsetX, y
				- GamePlayScreen.viewY + getOffsetY() + spriteOffsetY, width,
				height);

		g.setColor(Color.blue);
		for (int i = 0; i < amount; i++) {
			g.fillRect(particleX[i] + x - GamePlayScreen.viewX + getOffsetX()
					+ spriteOffsetX, particleY[i] + y - GamePlayScreen.viewY
					+ getOffsetY() + spriteOffsetY, 5, 5);
		}
	}

	public int getX() { // need this because x is static
		return x;
	}
}