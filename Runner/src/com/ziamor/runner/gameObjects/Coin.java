package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Coin extends GameObject {

	private int alpha = 255;

	public Coin(int x, int y) {
		this.x = x + 8;
		this.y = y + 4;
		this.objID = "coin";
		width = 16;
		height = 16;
	}

	public Coin() {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// make the coin fade away if the player picked it up
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
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(new Color(230, 230, 0, alpha));
		g.fillRect(x - GamePlayScreen.viewX + 1, y - GamePlayScreen.viewY + 1,
				width - 2, height - 2);
		g.fillRect(x - GamePlayScreen.viewX + 5, y - GamePlayScreen.viewY - 1,
				width - 10, height + 2);
		g.fillRect(x - GamePlayScreen.viewX - 1, y - GamePlayScreen.viewY + 5,
				width + 2, height - 10);

	}

}
