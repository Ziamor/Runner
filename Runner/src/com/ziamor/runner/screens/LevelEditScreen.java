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
import com.ziamor.runner.gameObjects.StartPortal;
import com.ziamor.runner.gameObjects.levels.LevelParser;
import com.ziamor.runner.menuObjects.LevelEditOverlay;

public class LevelEditScreen extends GameScreen {

	public static int viewX;
	public static int viewY;
	public static int selectedTile;

	private LevelEditOverlay levelOverLay;
	private GameObject gobjToRemove;

	public LevelEditScreen() {
		this.setDisableObjectUpdate(true);
		Player.spawningAnimation = false;
		this.levelOverLay = new LevelEditOverlay(0, 608);
		this.levelOverLay.setIsInterface(true);
		this.addGameObject(levelOverLay);
		this.addGameObject(LevelParser.loadLevel());
		selectedTile = 1;
	}

	public void update() {
		if (getDisableUpdate())
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

		// Left Click - add a tile
		if (Runner._input.isMouseClicked()) {
			int x = InputManager.mouse_x + viewX;
			int y = InputManager.mouse_y + viewY;
			if (LevelEditOverlay.snapToGrid) {
				x = x - x % 32;
				y = y - y % 24;
			}

			if (Runner._input.isMouseClicked(levelOverLay))
				levelOverLay.selectTile();
			else {
				this.addGameObject(GameObjectFactory.getById(selectedTile)
						.create(x, y));
			}
		}

		// Right click - remove a tile
		if (Runner._input.isMouseClickedRight()) {
			gobjToRemove = null;
			for (GameObject gobj : gameObjects) {
				int x = gobj.getX() - viewX + gobj.getOffsetX();
				int y = gobj.getY() - viewY + gobj.getOffsetY();
				int width = gobj.getWidth();
				int height = gobj.getHeight();
				if (Runner._input.isMouseClickedRight(x, y, width, height)) {
					gobjToRemove = gobj;
				}
			}
		}
		if (gobjToRemove != null) {
			removeGameObject(gobjToRemove);
			gobjToRemove = null;
		}

		// when the user presses "escape", save and play
		if (Runner._input.isKeyPressed(InputManager._keys.get("escape"))) {
			LevelParser.saveLevel(gameObjects);
			// go to the GamePlayScreen
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);

		}

		// call the gameScreen update();
		super.update();
	}

	public void paintComponent(Graphics g) {

		// draw the border
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 720, 608);
		g.setColor(Color.white);
		g.fillRect(-viewX, -viewY, GamePlayScreen.levelWidth,
				GamePlayScreen.levelHeight);

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
		for (GameObject gobj : gameObjects)
			gobj.paintComponent(g);

		// draw the start portal, if required
		if (!getGameObjectsByID("player").isEmpty()) {
			g.setColor(new Color(0, 255, 255, 100));
			g.fillRect(Player.x - viewX - 32, -viewY, 96,
					GamePlayScreen.levelHeight);
		}

		// draw the end portal, if required
		// (multiple end portals can be used)
		for (GameObject gobj : getGameObjectsByID("endPortal")) {
			g.setColor(new Color(0, 255, 255, 100));
			g.fillRect(gobj.getX() - viewX - 32, -viewY, 96, gobj.getY() + 48);
		}

		// draw the edit overlay
		levelOverLay.paintComponent(g);
	}
}
