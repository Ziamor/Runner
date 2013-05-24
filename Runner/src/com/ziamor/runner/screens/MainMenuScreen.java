package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class MainMenuScreen extends GameScreen{

	public MainMenuScreen(){
		
	}	
	
	public void update(){
		if (Runner._input.isKeyPressed(InputManager._keys.get("a"))) {
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(0,280,1024,56);
		g.setColor(Color.black);
		g.drawString("Main Menu Screen", 482, 300);
		g.drawString("Press 'A' to play", 490, 320);
		}
}
