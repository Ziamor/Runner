package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class LevelSelectButton extends GameObject {

	public int world;
	public int level;

	public LevelSelectButton() {

	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			
			// create a GamePlayScreen with the current world and level
			Runner._gameScreenManager.addScreen(new GamePlayScreen(world, level));

			// remove the LevelSelectScreen
			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString("" + world + " - " + level, x + width/2 - 10 , y + height/2);
	}

}
