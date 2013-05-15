package com.ziamor.runner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.MainMenuScreen;

public class Runner extends JPanel {
	private boolean _isRunning = true;
	public static int _width, _height;
	public static GameScreenManager _gameScreenManager;
	public static InputManager _input;

	public Runner() {
		this.setFocusable(true);

		// Get the dimensions of the screen
		this._width = 1000;
		this._height = 600;

		// Set the game window
		this.setPreferredSize(new Dimension(_width, _height));

		this._input = new InputManager();
		this.addKeyListener(_input);

		_gameScreenManager = new GameScreenManager();
		_gameScreenManager.addScreen(new MainMenuScreen());

		// Start the game loop
		Thread t = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		t.start();
	}

	public void gameLoop() {
		while (_isRunning) {
			this.repaint();
			try {
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		GameScreenManager.update();
	}

	public void paintComponent(Graphics g) {
		this.update();
		g.setColor(Color.white);
		g.fillRect(0, 0, _width, _height);
		GameScreenManager.paintComponent(g);
	}
}
