package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class Spikes extends GameObject{
	
	public Spikes(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "hazard";
		this.spriteID = "spikes";
		this.gobjFactorty = GameObjectFactory.SPIKES;
		width = 32;
		height = 16;
		offsetY = 8;
		spriteOffsetY = -8;
	}

	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;
		
		g.drawImage(TextureCache._textures.get(spriteID).getTexture(), x
				- GamePlayScreen.viewX + getOffsetX() + spriteOffsetX, y
				- GamePlayScreen.viewY + getOffsetY() + spriteOffsetY, null);
		
		

	}

}

