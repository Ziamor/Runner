package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Coin extends GameObject {

	public Coin() {
		this.objID = "coin";
		width = 16;
		height = 16;
		isVisible = true;
	}

	public void update() {
	}

	public void paintComponent(Graphics g) {
		if (!isVisible) {
			return;
		}

		g.setColor(Color.cyan);
		g.fillRect(x - GamePlayScreen.viewX+1, y - GamePlayScreen.viewY+1, width-2,
				height-2);
		g.fillRect(x - GamePlayScreen.viewX + 4, y - GamePlayScreen.viewY - 2,
				width - 8, height + 4);
		g.fillRect(x - GamePlayScreen.viewX - 2, y - GamePlayScreen.viewY + 4,
				width + 4, height - 8);

	}

}
