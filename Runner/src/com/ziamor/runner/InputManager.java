package com.ziamor.runner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener {

	private boolean[] key_pressed = new boolean[256];
	private boolean[] key_released = new boolean[256];

	public static Map<String, Integer> _keys = new HashMap<String, Integer>() {
		{
			// Use site:
			// http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
			// for more key codes
			put("w", 87);
			put("a", 65);
			put("d", 68);
			put("s", 83);
			put("enter", 13);

			put("left", 37);
			put("up", 38);
			put("right", 39);
			put("down", 40);
			put("space", 32);
		}
	};

	public boolean isKeyPressed(int keyCode) {
		/*
		 * System.out.println("Key: " + keyCode + " is pressed: " +
		 * key_released[keyCode]);
		 */
		return key_pressed[keyCode];
	}

	public boolean isKeyReleased(int keyCode) {
		return key_released[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < 256 && keyCode > 0)
			key_pressed[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < 256 && keyCode > 0) {
			key_pressed[keyCode] = false;
			key_released[keyCode] = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void update() {
		key_released = new boolean[256];
	}
}
