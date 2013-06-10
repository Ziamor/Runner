package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.BackButton;
import com.ziamor.runner.gameObjects.LevelSelectButton;
import com.ziamor.runner.gameObjects.WorldSelectButton;
import com.ziamor.runner.screens.*;

public class WorldSelectScreen extends GameScreen {

	public static int viewX = 0;

	public WorldSelectScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		viewX = (Runner.world - 1) * 700;

		// make the world buttons
		for (int i = 1; i < 4; i++) {
			WorldSelectButton tempbutton = new WorldSelectButton();
			tempbutton.setX(200 + 700 * (i - 1));
			tempbutton.setY(100);
			tempbutton.setWidth(624);
			tempbutton.setHeight(408);
			tempbutton.world = i;
			this.addGameObject(tempbutton);
		}

		// make the back button
		this.addGameObject(new BackButton("MainMenu"));
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		if (Runner._input.isKeyHit(InputManager._keys.get("right"))) {
			Runner.world++;
		}
		if (Runner._input.isKeyHit(InputManager._keys.get("left"))) {
			Runner.world--;
		}

		// make sure worldCurrent isn't less than 1
		if (Runner.world < 1) {
			Runner.world = 1;
		}

		// move the view smoothly
		viewX += (int) (((Runner.world - 1) * 700 - viewX) / 10);

	}

	public void paintComponent(Graphics g) {
		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}

}
