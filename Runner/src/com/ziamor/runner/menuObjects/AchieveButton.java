package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.AchieveScreen;
import com.ziamor.runner.screens.MenuScreen;

public class AchieveButton extends GameObject {

	public AchieveButton() {
		x = 695;
		y = 15;
		width = 50;
		height = 50;
		spriteID = "achieveButton";
		objID = "achieveButton";
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			if (!(parent instanceof MenuScreen && MenuScreen.mode == 0)) {
			Runner._gameScreenManager.addScreen(new AchieveScreen());
			parent.setDisableUpdate(true); // freeze game objects
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (MenuScreen.mode != 0)
			g.drawImage(TextureCache._textures.get(spriteID).getTexture(), x,
					y, null);
	}

}
