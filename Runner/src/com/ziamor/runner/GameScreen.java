package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {
	private boolean blockRender;
	private boolean blockUpdate;
	private ArrayList<GameObject> gameObjects;

	public GameScreen() {
		gameObjects = new ArrayList<GameObject>();
	}

	public void setBlockRender(boolean value) {
		this.blockRender = value;
	}

	public void setBlockUpdate(boolean value) {
		this.blockUpdate = value;
	}

	public void addGameObject(GameObject gobj) {
		gameObjects.add(gobj);

	}

	public void update() {
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}
	}

	public void paintComponent(Graphics g) {
		for (GameObject gameObject : gameObjects) {
			gameObject.paintComponent(g);
		}
	}
}
