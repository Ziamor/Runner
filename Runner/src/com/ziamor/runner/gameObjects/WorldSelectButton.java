package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class WorldSelectButton extends GameObject {

	public int world;

	public WorldSelectButton() {

	}

	public void update() {
		if (Runner._input.isMouseClicked(x - WorldSelectScreen.viewX, y, width,
				height)) {
			// go to the LevelSelectScreen for the current world
			Runner._gameScreenManager.addScreen(new LevelSelectScreen());

			// remove the WorldSelectScreen
			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x - WorldSelectScreen.viewX, y, width, height);
		g.setColor(Color.black);
		g.drawString("World " + world, x - WorldSelectScreen.viewX + width / 2,
				y + height / 2);
	}

}
