package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Coin extends GameObject {

	private int alpha = 255;

	public Coin() {
		this.objID = "coin";
		width = 16;
		height = 16;
		isVisible = true;
		isActive = true;
	}

	public void update() {
		if (!isActive && isVisible) {
			alpha -= 20;
			y -= 2;
			if (alpha < 0) {
				alpha = 0;
				isVisible = false;
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (!isVisible) {
			return;
		}
		Color c = new Color(230, 230, 0, alpha);
		g.setColor(c);
		g.fillRect(x - GamePlayScreen.viewX + 1, y - GamePlayScreen.viewY + 1,
				width - 2, height - 2);
		g.fillRect(x - GamePlayScreen.viewX + 5, y - GamePlayScreen.viewY - 1,
				width - 10, height + 2);
		g.fillRect(x - GamePlayScreen.viewX - 1, y - GamePlayScreen.viewY + 5,
				width + 2, height - 10);

	}

}
