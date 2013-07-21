package com.ziamor.runner;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionHandler {

	public static boolean isColliding(GameObject gobj1, GameObject gobj2) {
		Rectangle rect1 = new Rectangle(gobj1.getX()+gobj1.getOffsetX(), gobj1.getY()+gobj1.getOffsetY(),
				gobj1.width, gobj1.height);
		Rectangle rect2 = new Rectangle(gobj2.getX()+gobj2.getOffsetX(), gobj2.getY()+gobj2.getOffsetY(),
				gobj2.width, gobj2.height);
		// check for collision
		if (rect1.intersects(rect2))
			return true;
		return false;
	}

	public static boolean isColliding(GameObject gobj1,
			ArrayList<GameObject> gameObjects) {
		for (GameObject gobj2 : gameObjects)
			if (isColliding(gobj1, gobj2))
				return true;
		return false;
	}

}
