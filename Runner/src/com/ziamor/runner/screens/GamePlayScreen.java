package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;
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
		wall.setX(400);
		wall.setY(350+80);
		this.addGameObject(player);
		this.addGameObject(wall);
		
		for(int i = 0; i < Runner._width / 32; i++)
		{
			Wall tempWall = new Wall();
			tempWall.setX(i*32);
			tempWall.setY(430);
			this.addGameObject(tempWall);
		}
	}
}
