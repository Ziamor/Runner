package com.ziamor.runner.gameObjects;

import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class Wall extends GameObject {
	
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "wall";
		this.gobjFactorty = GameObjectFactory.WALL;
		this.spriteID = "basicWall";
		spriteOffsetY = -4;
		width = 32;
		height = 48;
	}

	public Wall() {
		this.x = -1;
		this.y = -1;
		this.objID = "wall";
		this.gobjFactorty = GameObjectFactory.WALL;
		width = 32;
		height = 48;
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
