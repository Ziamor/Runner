package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {
	private boolean blockRender;
	private boolean blockUpdate;
	public static ArrayList<GameObject> gameObjects;

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

	public ArrayList<GameObject> getGameObjectByID(String id) {
		// Temporary ArrayList to store game objects with the specified ID
		ArrayList<GameObject> gameObjectsWithID = new ArrayList<GameObject>();
		// Loop through all GameObjects and pull any GameObject that uses the ID
		for (GameObject gobj : gameObjects)
			if (gobj.getObjID().equals(id))
				gameObjectsWithID.add(gobj);
		return gameObjectsWithID;
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
