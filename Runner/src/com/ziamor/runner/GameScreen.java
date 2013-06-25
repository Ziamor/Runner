package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.gameObjects.Fireball;

public class GameScreen {
	private boolean blockRender;
	private boolean blockUpdate;
	private boolean remove;
	protected ArrayList<GameObject> gameObjects;
	protected ArrayList<GameObject> objectsToAdd;

	public GameScreen() {
		gameObjects = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
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
		objectsToAdd.add(gobj);
	}

	public void clearGameObjects() {
		gameObjects.clear();
	}

	public void removeThisScreen() {
		Runner._gameScreenManager.removeScreen(this);
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
		// add to main list from objectsToAdd
		if (!objectsToAdd.isEmpty())
			gameObjects.addAll(objectsToAdd);
		objectsToAdd = new ArrayList<GameObject>();

		// stop the update if needed
		if (!blockUpdate) {
			return;
		}

		// update all game objects
		ArrayList<GameObject> gameObjectsToUpdate = gameObjects;
		for (GameObject gameObject : gameObjectsToUpdate) {
			gameObject.update();
		}

		// check if the screen should be removed
		if (isRemove()) {
			Runner._gameScreenManager.removeScreen(this);
		}

	}

	public void paintComponent(Graphics g) {

		// paint all game objects
		for (GameObject gameObject : gameObjects) {
			gameObject.paintComponent(g);
		}
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}
