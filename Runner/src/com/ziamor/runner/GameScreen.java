package com.ziamor.runner;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameScreen {
	private boolean disableRender; // disables rendering of the screen
	private boolean disableUpdate; // disables updating of the screen
	private boolean disableObjectUpdate; // disables updating of objects only
	private boolean remove; // used for removing a screen from within an object
							// without throwing an error
	public int offsetX; // I can't think of a time where we would ever use these
	public int offsetY;
	protected ArrayList<GameObject> gameObjects;
	protected ArrayList<GameObject> objectsToAdd;
	protected ArrayList<GameObject> objectsToRemove;
	protected ArrayList<GameObject> interfaceOverlay;

	public GameScreen() {
		gameObjects = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
		objectsToRemove = new ArrayList<GameObject>();
		interfaceOverlay = new ArrayList<GameObject>();
		offsetX = 0;
		offsetY = 0;
		disableObjectUpdate = false;
	}

	public void setDisableRender(boolean value) {
		this.disableRender = value;
	}

	public void setDisableUpdate(boolean value) {
		this.disableUpdate = value;
	}

	public boolean getDisableRender() {
		return this.disableRender;
	}

	public boolean getDisableUpdate() {
		return this.disableUpdate;
	}

	public void setDisableObjectUpdate(boolean value) {
		this.disableObjectUpdate = value;
	}

	public void setOffsetX(int value) {
		offsetX = value;
	}

	public void setOffsetY(int value) {
		offsetY = value;
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
		if (gobj != null) {
			objectsToRemove.add(gobj);
		}
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
		if (disableUpdate) {
			return;
		}

		// update all game objects
		ArrayList<GameObject> gameObjectsToUpdate = gameObjects;
		if (!disableObjectUpdate)
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

		// paint the objects (except walls)
		for (GameObject gameObject : gameObjects) {
			if (!(gameObject.getObjID() == "wall" || gameObject.getObjID() == "breakableWall"))
				gameObject.paintComponent(g);
		}

		// paint the walls
		for (GameObject gameObject : gameObjects) {
			if (gameObject.getObjID() == "wall"
					|| gameObject.getObjID() == "breakableWall")
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
