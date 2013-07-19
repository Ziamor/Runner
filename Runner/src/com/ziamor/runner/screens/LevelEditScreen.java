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
	public static int selectedTile;
	public static int endPortalX;
	public static int endPortalY;

	private LevelEditOverlay levelOverLay;
	private ArrayList<GameObject> gameObjectsData;
	private GameObject gobjToRemove;

	public static boolean editing;

	public LevelEditScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		this.setDisableUpdate(true);
		this.levelOverLay = new LevelEditOverlay(0, 608);
		this.levelOverLay.setIsInterface(true);
		this.addGameObject(levelOverLay);
		selectedTile = 1;

		gameObjectsData = LevelParser.loadLevel();

		GamePlayScreen.playerDead = false;
		editing = true;
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
			GamePlayScreen.levelWidth -= 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("d")))
			GamePlayScreen.levelWidth += 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("w")))
			GamePlayScreen.levelHeight -= 24;
		if (Runner._input.isKeyHit(InputManager._keys.get("s")))
			GamePlayScreen.levelHeight += 24;

		// apply limits on level height and width
		if (GamePlayScreen.levelHeight < 552)
			GamePlayScreen.levelHeight = 552;
		if (GamePlayScreen.levelHeight > 50 * 24)
			GamePlayScreen.levelHeight = 50 * 24;

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
			LevelParser.saveLevel(gameObjectsData);
			// go to the GamePlayScreen
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);

		}
	}

	public void paintComponent(Graphics g) {
		endPortalX = -1;
		endPortalY = -1;

		// draw the border
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 720, 608);
		g.setColor(Color.white);
		g.fillRect(-viewX, -viewY, GamePlayScreen.levelWidth, GamePlayScreen.levelHeight);

		// draw the grid
		for (int x = 0; x < GamePlayScreen.levelWidth / 32; x++) {
			for (int y = 0; y < GamePlayScreen.levelHeight / 24; y++) {
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

		g.setColor(new Color(0, 255, 255, 100));
		g.fillRect(Player.x - viewX - 32, -viewY, 96,
				GamePlayScreen.levelHeight);
		if (endPortalX != -1)
			g.fillRect(endPortalX - viewX - 32, -viewY, 96, endPortalY + 48);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);
	}
}
