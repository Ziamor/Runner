package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Button;

public class MainMenuScreen extends GameScreen {

	public MainMenuScreen() {
		// test making a button
		Button tempbutton = new Button();
		tempbutton.setX(100);
		tempbutton.setY(100);
		tempbutton.setWidth(100);
		tempbutton.setHeight(100);
		this.addGameObject(tempbutton);
	}

	public void update() {
		if (Runner._input.isKeyPressed(InputManager._keys.get("space"))) {
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);
		}

		// update all game objects
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}
	}

	public void paintComponent(Graphics g) {
		// update all game objects
		for (GameObject gameObject : gameObjects) {
			gameObject.paintComponent(g);
		}

		// paint other stuff (temporary)
		g.setColor(Color.green);
		g.fillRect(0, 260, 1024, 56);
		g.setColor(Color.lightGray);
		g.fillRect(250, 500, 300, 40);
		g.fillRect(600, 500, 40, 40);
		g.fillRect(650, 500, 40, 40);
		g.fillRect(700, 500, 40, 40);
		g.fillRect(650, 450, 40, 40);
		g.setColor(Color.black);
		g.drawString("Main Menu Screen", 482, 280);
		g.drawString("Press 'Space' to play", 475, 300);
		g.setColor(Color.darkGray);
		g.drawString("Change Gravity", 370, 525);
		g.drawString("Jump", 655, 468);
		g.drawString("Up", 662, 482);
		g.drawString("Jump", 655, 518);
		g.drawString("Down", 654, 532);
		g.drawString("Dash", 705, 525);
	}
}
