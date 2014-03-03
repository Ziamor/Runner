package com.ziamor.runner.gameObjects;

import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class Coin extends GameObject {

	private int alpha = 255;
	private int animation;

	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		setOffsetX(8);
		setOffsetY(4);
		this.objID = "coin";
		this.gobjFactorty = GameObjectFactory.COIN;
		this.spriteID = "coin";
		spriteOffsetX = -2;
		spriteOffsetY = -2;
		width = 16;
		height = 16;
		animation = 0;
	}

	public Coin() {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// make the coin fade away if the player picked it up
		if (!isActive && isVisible) {
			alpha -= 20;
			y -= 2;
			if (alpha < 0) {
				alpha = 0;
				isVisible = false;
			}
		}
		
		animation++; // update the animation
		if (animation > 39)
			animation = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.drawImage(TextureCache._textures.get(spriteID).getTexture(animation/5,20,20), x
				- GamePlayScreen.viewX + getOffsetX() + spriteOffsetX, y
				- GamePlayScreen.viewY + getOffsetY() + spriteOffsetY, null);
	}

}
