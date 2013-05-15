package com.ziamor.runner.screens;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;

public class MainMenuScreen extends GameScreen{

	public MainMenuScreen(){
		Runner.gameScreenManager.addScreen(new GamePlayScreen());
	}
}
