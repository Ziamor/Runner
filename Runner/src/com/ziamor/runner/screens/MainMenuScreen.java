package com.ziamor.runner.screens;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Wall;

public class MainMenuScreen extends GameScreen{

	public MainMenuScreen(){
		Wall wall = new Wall();
		wall.setX(Runner._width/2);
		wall.setY(Runner._height/2);
		this.addGameObject(wall);
	}	
	
	public void update(){
		if (Runner._input.isKeyPressed(InputManager._keys.get("a"))) {
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
		}
	}
}
