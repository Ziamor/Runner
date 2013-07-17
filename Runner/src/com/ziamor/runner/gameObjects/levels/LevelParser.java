package com.ziamor.runner.gameObjects.levels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
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

	ArrayList<GameObject> GameObjectData;
	Document dom;

	String fileName;

	private LevelParser(String fileName) {
		this.fileName = fileName;

	}

	private ArrayList<GameObject> loadFromXML() {
		// create a list to hold the GameObjects
		GameObjectData = new ArrayList<GameObject>();

		// parse the xml file and get the dom object
		parseXmlFile();

		// get each GameObjects element and create a GameObject
		parseDocument();

		return (ArrayList<GameObject>) GameObjectData;
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

		// get a nodelist of <GameObject> elements
		NodeList nl = docEle.getElementsByTagName("GameObject");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the GameObjects element
				Element el = (Element) nl.item(i);

				// get the GameObject
				GameObject lvl = getGameObject(el);

				// add it to list
				GameObjectData.add(lvl);
			}
		}
	}

	/**
	 * Take the element data and create a GameObject
	 * 
	 * @param gobj
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
		GameObject e = GameObjectFactory.getById(id).create(X, Y);
		return e;
	}

	/**
	 * Get the value from a text element
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

	public void saveToXML(String fileName, ArrayList<GameObject> GameObjectData) {
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Entities");
			doc.appendChild(rootElement);

			for (GameObject gobj : GameObjectData) {
				//Make sure the object we are saving is a valid game object
				if(gobj.getgobjFactorty().getId() == -1)
					continue;
				
				// gobj elements
				Element egobj = doc.createElement("GameObject");
				rootElement.appendChild(egobj);

				// set attribute to gobj element
				// TODO possible use for this
				/*
				 * Attr attr = doc.createAttribute("id"); attr.setValue("1");
				 * gobj.setAttributeNode(attr);
				 * 
				 * // shorten way // gobj.setAttribute("id", "1");
				 */

				// ID elements
				Element firstname = doc.createElement("ID");
				firstname.appendChild(doc.createTextNode(gobj.getgobjFactorty().getId() + ""));
				egobj.appendChild(firstname);

				// X elements
				Element lastname = doc.createElement("X");
				lastname.appendChild(doc.createTextNode(gobj.getX() + ""));
				egobj.appendChild(lastname);

				// Y elements
				Element nickname = doc.createElement("Y");
				nickname.appendChild(doc.createTextNode(gobj.getY() + ""));
				egobj.appendChild(nickname);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer tr = transformerFactory.newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			tr.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static ArrayList<GameObject> loadLevel() {
		String fileName = "res\\maps\\" + Runner.world + "-" + Runner.level
				+ ".xml";
		LevelParser lp = new LevelParser(fileName);

		return lp.loadFromXML();
	}

	public static ArrayList<GameObject> saveLevel(
			ArrayList<GameObject> GameObjectData) {
		String fileName = "res\\maps\\" + Runner.world + "-" + Runner.level
				+ ".backup.xml";
		LevelParser lp = new LevelParser(fileName);
		lp.saveToXML(fileName, GameObjectData);

		return lp.loadFromXML();
	}
}