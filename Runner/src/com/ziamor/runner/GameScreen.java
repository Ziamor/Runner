package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {
	private boolean blockRender;
	private boolean blockUpdate;
	protected ArrayList<GameObject> gameObjects;

	public GameScreen() {
		gameObjects = new ArrayList<GameObject>();
	}

	public void setBlockRender(boolean value) {
		this.blockRender = value;
	}

	public void setBlockUpdate(boolean value) {
		this.blockUpdate = value;
	}

	public boolean getBlockRender() {
		return this.blockRender;
	}

	public boolean getBlockUpdate() {
		return this.blockUpdate;
	}
	
	public void addGameObject(GameObject gobj) {
		gobj.parent = this;
		gameObjects.add(gobj);
	}
	
	public void clearGameObjects() {
		gameObjects.clear();
	}

	public ArrayList<GameObject> getGameObjectsByID(String id) {
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
