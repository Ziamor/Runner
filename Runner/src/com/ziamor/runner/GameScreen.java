package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {
	private boolean blockRender;
	private boolean blockUpdate;
	private boolean remove;
	private boolean disableUpdate;
	public int offX;
	public int offY;
	protected ArrayList<GameObject> gameObjects;
	protected ArrayList<GameObject> objectsToAdd;
	protected ArrayList<GameObject> objectsToRemove;
	protected ArrayList<GameObject> interfaceOverlay;

	public GameScreen() {
		gameObjects = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
		objectsToRemove = new ArrayList<GameObject>();
		interfaceOverlay = new ArrayList<GameObject>();
		offX = 0;
		offY = 0;
		disableUpdate = false;
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

	public void setDisableUpdate(boolean value) {

		this.disableUpdate = value;
	}

	public void setOffX(int value) {
		offX = value;
	}

	public void setOffY(int value) {
		offY = value;
	}

	public void addGameObject(GameObject gobj) {
		if (gobj != null) {
			gobj.parent = this;
			objectsToAdd.add(gobj);
		}
	}

	public void addGameObject(ArrayList<GameObject> gobjList) {
		for (GameObject gobj : gobjList) {
			addGameObject(gobj);
		}
	}

	public void removeGameObject(GameObject gobj) {
		if (gobj != null)
			objectsToRemove.add(gobj);
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

	// Returns two types of objects in an arraylist
	public ArrayList<GameObject> getGameObjectsByID(String id1, String id2) {
		// Temporary ArrayList to store game objects with the specified ID
		ArrayList<GameObject> gameObjectsWithID = new ArrayList<GameObject>();
		// Loop through all GameObjects and pull any GameObject that uses the ID
		for (GameObject gobj : gameObjects)
			if (gobj.getObjID().equals(id1) || gobj.getObjID().equals(id2))
				gameObjectsWithID.add(gobj);
		return gameObjectsWithID;
	}

	public void update() {
		// add to main list from objectsToAdd
		if (!objectsToAdd.isEmpty())
			for (GameObject gobj : objectsToAdd) {
				if (gobj.isInterface)
					interfaceOverlay.add(gobj);
				else
					gameObjects.add(gobj);
			}
		objectsToAdd = new ArrayList<GameObject>();

		// remove from main list from objectsToRemove
		if (!objectsToRemove.isEmpty())
			for (GameObject gobj : objectsToRemove) {
				if (gobj.isInterface)
					interfaceOverlay.remove(gobj);
				else
					gameObjects.remove(gobj);
			}
		objectsToRemove = new ArrayList<GameObject>();

		// stop the update if needed
		if (!blockUpdate) {
			return;
		}

		// update all game objects
		ArrayList<GameObject> gameObjectsToUpdate = gameObjects;
		if (!disableUpdate)
			for (GameObject gameObject : gameObjectsToUpdate) {
				gameObject.update();
			}

		// update the interface
		ArrayList<GameObject> interfaceOverlayToUpdate = interfaceOverlay;
		for (GameObject gameObject : interfaceOverlayToUpdate) {
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

		// paint all game interfaces
		for (GameObject gameObject : interfaceOverlay) {
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
