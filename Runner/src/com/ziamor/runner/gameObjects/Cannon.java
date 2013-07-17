package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Cannon extends GameObject {

	private int shotTimer;

	public Cannon(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "cannon";
		this.gobjFactorty = GameObjectFactory.CANNON;
		width = 32;
		height = 24;

	}

	public void update() {

		shotTimer++;

		if (shotTimer > 80 && !offScreen) {
			parent.addGameObject(new Fireball(x + 8, y + 4, -5, -8));
			shotTimer = 0;
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(Color.black);
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);

	}

}
