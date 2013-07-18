package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Portal;
import com.ziamor.runner.gameObjects.levels.LevelParser;
import com.ziamor.runner.menuObjects.LevelEditOverlay;

public class LevelEditScreen extends GameScreen {

	public static int viewX;
	public static int viewY;
	public static int levelWidth;
	public static int levelHeight;
	public static int selectedTile;
	// private int spawnX, spawnY;
	private int startPortalX;
	private int endPortalX;
	private Portal startPortal;
	private Portal endPortal;
	private Player player;
	private LevelEditOverlay levelOverLay;
	private ArrayList<GameObject> gameObjectsData;
	private GameObject gobjToRemove;

	public LevelEditScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		this.setDisableUpdate(true);
		this.levelOverLay = new LevelEditOverlay(0, 608);
		this.levelOverLay.setIsInterface(true);
		this.addGameObject(levelOverLay);
		selectedTile = 1;
		gameObjectsData = new ArrayList<GameObject>();

		ArrayList<GameObject> pregameObjectsData = LevelParser.loadLevel();
		for (GameObject gobj : pregameObjectsData) {
			if (gobj.getObjID().equals("portal")) {
				if (gobj.getgobjFactorty() == GameObjectFactory.SPORTAL)
					// startPortalX = gobj.getX();
					startPortal = (Portal) gobj;
				else if (gobj.getgobjFactorty() == GameObjectFactory.EPORTAL)
					// endPortalX = gobj.getX();
					endPortal = (Portal) gobj;
			} else if (gobj.getObjID().equals("player")) {
				player = (Player) gobj;
				// spawnX = player.getX();
				// spawnY = player.getY();
				// gameObjectsData.add(gobj);
			} else
				gameObjectsData.add(gobj);
		}
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// move the camera with the arrow keys
		if (Runner._input.isKeyPressed(InputManager._keys.get("left")))
			viewX -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("right")))
			viewX += 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("up")))
			viewY -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("down")))
			viewY += 10;

		// change the level size with WASD
		if (Runner._input.isKeyHit(InputManager._keys.get("a")))
			levelWidth -= 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("d")))
			levelWidth += 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("w")))
			levelHeight -= 24;
		if (Runner._input.isKeyHit(InputManager._keys.get("s")))
			levelHeight += 24;

		if (levelHeight < 552)
			levelHeight = 552;
		if (levelHeight > 50 * 24)
			levelHeight = 50 * 24;

		GamePlayScreen.viewX = LevelEditScreen.viewX;
		GamePlayScreen.viewY = LevelEditScreen.viewY;

		// if the user clicks
		if (Runner._input.isMouseClicked()) {
			// put a tile onto the map
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 24;

			if (Runner._input.isMouseClicked(levelOverLay))
				levelOverLay.selectTile();
			else {
				if (selectedTile == GameObjectFactory.EPORTAL.getId())
					startPortal = (Portal) GameObjectFactory.getById(
							selectedTile).create(x * 32, y * 24);
				else if (selectedTile == GameObjectFactory.SPORTAL.getId())
					endPortal = (Portal) GameObjectFactory
							.getById(selectedTile).create(x * 32, y * 24);
				else if (selectedTile == GameObjectFactory.PLAYER.getId())
					player = (Player) GameObjectFactory.getById(selectedTile)
							.create(x * 32, y * 24);
				else
					gameObjectsData.add(GameObjectFactory.getById(selectedTile)
							.create(x * 32, y * 24));
			}
		}

		if (Runner._input.isMouseClickedRight()) { // remove a tile from the map
			gobjToRemove = null;
			for (GameObject gobj : gameObjectsData) {
				int x = gobj.getX() - viewX;
				int y = gobj.getY() - viewY;
				int width = gobj.getWidth();
				int height = gobj.getHeight();
				if (Runner._input.isMouseClickedRight(x, y, width, height)) {
					gobjToRemove = gobj;
				}
			}
		}
		if (gobjToRemove != null) {
			gameObjectsData.remove(gobjToRemove);
			gobjToRemove = null;
		}
		// when the user presses "escape", save and play
		if (Runner._input.isKeyPressed(InputManager._keys.get("escape"))) {
			// player = new Player(64,192);
			gameObjectsData.add(player);
			// Portal sPortal = new Portal(player.getX(), player.getY(), false);
			gameObjectsData.add(0, startPortal);
			// Portal ePortal = new Portal(endPortalX, 600, true);
			gameObjectsData.add(0, endPortal);
			LevelParser.saveLevel(gameObjectsData);
			// go to the GamePlayScreen
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);

			startPortalX = -1;
			endPortalX = -1;
		}
	}

	public void paintComponent(Graphics g) {
		// draw the grid
		for (int x = 0; x < levelWidth / 32; x++) {
			for (int y = 0; y < levelHeight / 24; y++) {
				if (y % 2 == 0) {
					g.setColor(Color.lightGray);
					g.drawRect(x * 32 - viewX, y * 24 - viewY, 31, 47);
				} else {
					g.setColor(new Color(0, 0, 0, 40));
					g.drawLine(x * 32 - viewX, y * 24 - viewY, x * 32 - viewX
							+ 32, y * 24 - viewY);
				}
			}
		}

		// draw the tiles
		for (GameObject gobj : gameObjectsData)
			gobj.paintComponent(g);

		// Draw the player spawn
		g.setColor(Color.blue);
		g.fillRect(player.getX() - viewX, player.getY() - viewY, 32, 48);

		// Draw end portal
		g.setColor(Color.blue);
		g.drawRect(endPortal.getX() + 2 - viewX, endPortal.getY() + 2 - viewY,
				28, 44);
		g.drawRect(endPortal.getX() + 4 - viewX, endPortal.getY() + 4 - viewY,
				24, 40);

		// draw the portals last so they are on top
		/*
		 * g.setColor(new Color(0, 255, 255, 100)); if (startPortalX != -1)
		 * g.fillRect(startPortalX, -viewY, 96, levelHeight); if (endPortalX !=
		 * -1) g.fillRect(endPortalX, -viewY, 96, levelHeight);
		 */

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);
	}
}
