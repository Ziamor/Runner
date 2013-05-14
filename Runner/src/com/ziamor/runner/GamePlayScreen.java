package com.ziamor.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Wall;

public class GamePlayScreen extends GameScreen{
	
	public static Player player;
	public static Wall wall;

	public GamePlayScreen()
	{
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		player = new Player();
		wall = new Wall();
	}
	
	public void update(Graphics g) {
		player.update(g);
		wall.update(g);
	}

	public void paintComponent(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 600);
		player.paintComponent(g);
		wall.paintComponent(g);
		
	}
}
