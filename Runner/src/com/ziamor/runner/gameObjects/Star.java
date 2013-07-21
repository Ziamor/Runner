package com.ziamor.runner.gameObjects;

import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class Star extends GameObject {

	public Star(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "star";
		this.spriteID = "star";
		this.gobjFactorty = GameObjectFactory.STAR;
		setOffsetX(4);
		spriteOffsetX = -4;
		spriteOffsetY = -4;
		width = 24;
		height = 24;

	}

	public Star() {
		// TODO Auto-generated constructor stub
	}

	public void update() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.drawImage(TextureCache._textures.get(spriteID).getTexture(), x
				- GamePlayScreen.viewX + offsetX + spriteOffsetX, y
				- GamePlayScreen.viewY + offsetY + spriteOffsetY, null);
	}

}
