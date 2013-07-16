package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class BreakableWall extends GameObject {

	private int alpha;
	private int[] particleX;
	private int[] particleY;
	private int[] particleXSpeed;
	private int[] particleYSpeed;

	private boolean startBreak;

	public BreakableWall(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "breakablewall";
		width = 32;
		height = 48;

		alpha = 255;

		particleX = new int[96];
		particleY = new int[96];
		particleXSpeed = new int[96];
		particleYSpeed = new int[96];
	}

	public BreakableWall() {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		if (!isActive && isVisible) {
			if (!startBreak) {
				startBreak = true;
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 12; j++) {
						particleX[i * 8 + j] = x + i * 5;
						particleY[i * 8 + j] = y + j * 5;
						particleXSpeed[i * 8 + j] = 5;
						particleYSpeed[i * 8 + j] = (int) -(Math.random() * 8)+4;
					}
				}
			}

			for (int i = 0; i < 96; i++) {
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
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		if (!startBreak) { // if the wall is intact
			g.setColor(Color.gray);
			g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY,
					width, height);
			g.setColor(Color.lightGray);
			g.fillRect(x + 4 - GamePlayScreen.viewX, y + 4
					- GamePlayScreen.viewY, width - 8, height - 8);
		} else { // else draw particles
			for (int i = 0; i < 96; i++) {
				g.setColor(new Color(150, 150, 150, alpha));
				g.fillRect(particleX[i] - GamePlayScreen.viewX, particleY[i]
						- GamePlayScreen.viewY, 4, 4);
			}
		}
	}

}
