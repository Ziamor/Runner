package com.ziamor.runner;

import java.awt.Color;
import java.awt.Graphics;

public class Wall {

	public int x;
	public int y;

	public Wall() {

		x = 500;
		y = 350;

	}

	public void update(Graphics g) {

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, 32, 48);
	}

}
