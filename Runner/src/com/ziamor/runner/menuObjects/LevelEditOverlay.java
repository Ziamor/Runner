package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.screens.LevelEditScreen;

public class LevelEditOverlay extends GameObject {
	ArrayList<GameObject> tiles;

	public LevelEditOverlay(int x, int y) {
		super(x, y);
		this.width = Runner._width;
		this.height = 60;
		this.y = y - height;
		tiles = new ArrayList<GameObject>();
		for (int i = 0; i < GameObjectFactory.getSize(); i++) {
			try {
				GameObject gobj = GameObjectFactory.getById(i).create(x * i, y);
				if (gobj != null)
					tiles.add(gobj);
			} catch (IllegalArgumentException e) {
				break;
			}
		}
	}

	public void paintComponent(Graphics g) {
		// call the gameScreen paintComponent(g);
		super.paintComponent(g);
		// draw the editor panel
		g.setColor(new Color(200, 0, 200, 200));
		g.fillRect(x, y, width, height);
		for (int tile = 1; tile < 10; tile++) {
			drawTile(tile, tile * 50 - 40, 554, g);
		}

		g.setColor(Color.yellow);
		g.drawRect(LevelEditScreen.selectedTile * 50 - 43, 551, 37, 53);
		g.drawRect(LevelEditScreen.selectedTile * 50 - 44, 550, 39, 55);
	}

	private void drawTile(int id, int x, int y, Graphics g) {
		if (id == 1) { // wall
			g.setColor(Color.black);
			g.fillRect(x, y, 32, 48);
			g.setColor(Color.gray);
			g.fillRect(x + 4, y + 4, 24, 40);
		} else if (id == 2) { // player start position
			g.setColor(Color.blue);
			g.fillRect(x, y, 32, 48);
			// startPortalX = x - 32;
		} else if (id == 3) { // end portal
			g.setColor(Color.blue);
			g.drawRect(x + 2, y + 2, 28, 44);
			g.drawRect(x + 4, y + 4, 24, 40);
			// endPortalX = x - 32;
		} else if (id == 4) { // breakable wall
			g.setColor(Color.gray);
			g.fillRect(x, y, 32, 48);
			g.setColor(Color.lightGray);
			g.fillRect(x + 4, y + 4, 24, 40);
		} else if (id == 5) { // coin
			g.setColor(new Color(230, 230, 0, 255));
			g.fillRect(x + 9, y + 17, 14, 14);
			g.fillRect(x + 13, y + 15, 6, 18);
			g.fillRect(x + 7, y + 21, 18, 6);
		} else if (id == 6) { // star
			g.setColor(new Color(230, 230, 0, 255));
			g.fillRect(x + 4, y, 24, 24);
		}
	}
	
	public void selectTile()
	{
		if(Runner._input.isMouseClicked(this))
			// select a different tile
						if (InputManager.mouse_y > 548) {
							for (int tile = 1; tile < 10; tile++) {
								if (Runner._input.isMouseClicked(tile * 50 - 40, 554, 32,
										48)) {
									LevelEditScreen.selectedTile = tile;
								}
							}
						}
	}
}
