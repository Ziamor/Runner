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

	public int level;

	public LevelSelectButton() {

	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			
			// create a GamePlayScreen
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner.level = this.level; // set the level

			// remove the LevelSelectScreen
			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString("" + Runner.world + " - " + level, x + width/2 - 10 , y + height/2);
	}

}
