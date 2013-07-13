package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Wall extends GameObject {

	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "wall";
		width = 32;
		height = 48;
	}

	public void update() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(Color.black);
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);
		g.setColor(Color.gray);
		g.fillRect(x + 4 - GamePlayScreen.viewX, y + 4 - GamePlayScreen.viewY,
				width - 8, height - 8);

	}

}
