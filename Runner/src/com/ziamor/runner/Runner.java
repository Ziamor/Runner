package com.ziamor.runner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.ziamor.runner.screens.MainMenuScreen;

public class Runner extends JPanel {
	private boolean _isRunning = true;
	public static int _width, _height;
	public static GameScreenManager _gameScreenManager;
	public static InputManager _input;

	public static int world;
	public static int level;
	public static int score; // score in the current level
	public static int stars; // stars in the current level
	public static int[][] scoreHigh; // high score for each level
	public static int[][] starsHigh; // high stars for each level
	public static int scoreTotal; // total score over all levels
	public static int starsTotal; // total stars over all levels

	public static Font fontLarge = new Font("arial", Font.BOLD, 32);
	public static Font fontSmall = new Font("arial", Font.BOLD, 14);

	public Runner() {
		this.setFocusable(true);

		// Get the dimensions of the screen
		Runner._width = 720;
		Runner._height = 608;

		// Set the game window
		this.setPreferredSize(new Dimension(_width, _height));

		// Initialize the input manager
		Runner._input = new InputManager();
		this.addKeyListener(_input);
		this.addMouseListener(_input);
		this.addMouseMotionListener(_input);

		// Initialize game-wide variables
		Runner.world = 1;
		Runner.level = 1;
		scoreHigh = new int[4][10]; // size is 1 bigger than # of worlds and
									// levels
		starsHigh = new int[4][10]; // [0][0] remains empty

		// Make the main menu screen
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
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		GameScreenManager.update();
		Runner._input.update();
	}

	public void paintComponent(Graphics g) {
		this.update();
		g.setColor(Color.white);
		g.fillRect(0, 0, _width, _height);
		GameScreenManager.paintComponent(g);
	}
}
