package com.ziamor.runner;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Runner");
		// Close the application fully when terminated
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Game cannot be resized
		frame.setResizable(false);
		// Add the main game panel to the frame
		frame.add(new Runner());
		/*
		 * Uses the preferred size set inside TetrisTest to combine the JFrame's
		 * size and border
		 */
		frame.pack();
		// Make this frame visible
		frame.setVisible(true);
	}

}
