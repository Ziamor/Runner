package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Portal extends GameObject {

	private int amount;
	private int[] particleX;
	private int[] particleY;
	private int[] particleXSpeed;
	private int[] particleYSpeed;

	public Portal(int x) {
		this.objID = "portal";
		width = 96;
		height = 3000;

		this.x = x;
		y = -1000;

		amount = 300;
		
		particleX = new int[amount];
		particleY = new int[amount];
		particleXSpeed = new int[amount];
		particleYSpeed = new int[amount];

		for (int i = 0; i < amount; i++) {
			particleX[i] = (int) (x + Math.random() * (width - 5));
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
				particleY[i] = (int) (y + height - Math.random()*100);
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
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);

		g.setColor(Color.blue);
		for (int i = 0; i < amount; i++) {
			g.fillRect(particleX[i] - GamePlayScreen.viewX, particleY[i]
					- GamePlayScreen.viewY, 5, 5);
		}

	}
}