package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Button;
import com.ziamor.runner.gameObjects.LevelSelectButton;
import com.ziamor.runner.gameObjects.WorldSelectButton;
import com.ziamor.runner.screens.*;

public class LevelSelectScreen extends GameScreen {

	public LevelSelectScreen(int world) {
		this.setBlockRender(true);
		this.setBlockUpdate(true);

		// make the level buttons
		this.world = world;
		for (int i = 1; i < 7; i++) {
			LevelSelectButton tempbutton = new LevelSelectButton();
			tempbutton.setX(150 * i - 65);
			tempbutton.setY(250);
			tempbutton.setWidth(100);
			tempbutton.setHeight(108);
			tempbutton.world = this.world;
			tempbutton.level = i;
			this.addGameObject(tempbutton);
		}

		// make the back button
		this.addGameObject(new Button("WorldSelect"));
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;
	}

}
