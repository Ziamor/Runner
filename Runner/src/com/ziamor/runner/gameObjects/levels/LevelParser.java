package com.ziamor.runner.gameObjects.levels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.screens.GamePlayScreen;

public class LevelParser {

	List<GameObject> GameObjectData;
	Document dom;

	GameScreen parent;
	String fileName;
	
	private LevelParser(GameScreen parent,String fileName) {
		this.parent = parent;
		this.fileName = fileName;
		
		// create a list to hold the employee objects
		GameObjectData = new ArrayList<GameObject>();

		// parse the xml file and get the dom object
		parseXmlFile();

		// get each employee element and create a Employee object
		parseDocument();
	}

	private void parseXmlFile() {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(fileName);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocument() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("GameObject");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the employee element
				Element el = (Element) nl.item(i);

				// get the Employee object
				GameObject lvl = getGameObject(el);

				// add it to list
				GameObjectData.add(lvl);
			}
		}
	}

	/**
	 * I take an employee element and read the values in, create an Employee
	 * object and return it
	 * 
	 * @param empEl
	 * @return
	 */
	private GameObject getGameObject(Element gobj) {
		int id = getIntValue(gobj, "ID");
		int X = getIntValue(gobj, "X");
		int Y = getIntValue(gobj, "Y");

		if (X > GamePlayScreen.levelWidth)
			GamePlayScreen.levelWidth = X;
		if (Y > GamePlayScreen.levelHeight)
			GamePlayScreen.levelHeight = Y;
		
		// Create a new GameObject with the value read from the xml nodes
		GameObject e = GameObjectFactory.getById(id).create(X,Y);
		return e;
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get the text
	 * content i.e for <employee><name>John</name></employee> xml snippet if the
	 * Element points to employee node and tagName is name I will return John
	 * 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 * 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele, tagName));
	}

	public static ArrayList<GameObject> loadLevel(GameScreen parent) {
		String fileName = "res\\maps\\" + Runner.world + "-" + Runner.level + ".xml";
		LevelParser lp = new LevelParser(parent,fileName);
		
		return (ArrayList<GameObject>) lp.GameObjectData;
	}
}