package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Wall extends GameObject {

	public Wall() {
		this.objID = "wall";
		width = 32;
		height = 48;
		isVisible = true;
	}

	public void update() {
	}

	public void paintComponent(Graphics g) {
		if (!isVisible) {
			return;
		}

		g.setColor(Color.black);
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);
		g.setColor(Color.gray);
		g.fillRect(x + 4 - GamePlayScreen.viewX, y + 4 - GamePlayScreen.viewY,
				width - 8, height - 8);

	}

}
