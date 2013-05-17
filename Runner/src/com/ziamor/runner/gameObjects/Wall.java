package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;

public class Wall extends GameObject{

	public int x;
	public int y;

	public Wall() {

		x = 400;
		y = 350+80;

	}

	public void update() {

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, 32, 48);
	}

}
