package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;

public class Wall extends GameObject {

	public Wall() {
		this.objID = "wall";
		width = 32;
		height = 48;
	}

	public void update() {

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.gray);
		g.fillRect(x + 4, y + 4, width - 8, height - 8);
	}

}
