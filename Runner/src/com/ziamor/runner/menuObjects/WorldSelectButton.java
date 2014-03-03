package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.MenuScreen;

public class WorldSelectButton extends GameObject {

	public int world;

	public WorldSelectButton() {
		objID = "worldSelectButton";
	}

	public void update() {
		// if the menu is on world select
		if (MenuScreen.mode == 1) {
			// if the world is unlocked
			if (Runner.isUnlocked[1/* this.world */][1] == true) {
				// if the user hits space and this is the current world
				// or if the user clicks this button
				if ((Runner._input.isKeyHit(InputManager._keys.get("space")) && Runner.world == this.world)
						|| Runner._input.isMouseClicked(x - MenuScreen.viewX,
								y, width, height)) {
					// go to level select
					MenuScreen.mode = 2;
					Runner.world = this.world;
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (MenuScreen.mode == 0)
			return;

		g.drawImage(TextureCache._textures.get("worldButton").getTexture(),
				x - MenuScreen.viewX, y - MenuScreen.viewY, null);
		g.setColor(Color.black);
		g.drawString("World " + world, x - MenuScreen.viewX + width / 2 - 50, y
				- MenuScreen.viewY + height / 2);

	}

}
