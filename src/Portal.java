
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Portal {
	private static final int LENGTH = 50;
	private static final int WIDTH = 10;
	public int myExit1X, myExit1Y, myExit2X, myExit2Y;
	public HalfPortal h1, h2;
	public int count=0;
	public Color myColor;
	public ArrayList<Door> allMyDoor = new ArrayList<Door>();

	/*
	 * This class contains a rectangle that can toggle its visibility
	 *  each time the player enters a portal of the same color
	 */
	public class Door {
		public Rectangle myDoor;
		public boolean isVisible;
		public boolean initVisible;

		public Door(int doorX, int doorY, int length, int width, Color c) {
			myDoor = new Rectangle(doorX, doorY, length, width);
			myDoor.setFill(c);
		}
	}

	/*
	 * This class contains a rectangle that represents a portal.
	 * Two halfPortals are one complete pair of portals
	 */
	public class HalfPortal {
		public Rectangle rec;

		public HalfPortal(int portX, int portY, int pos, Color c) {
			int w = WIDTH;
			int l = LENGTH;
			if (pos == 0) {
				w = LENGTH;
				l = WIDTH;
			}
			rec = new Rectangle(portX, portY, w, l);
			rec.setFill(c);
		}
	}

	/*
	 * Portal constructor
	 * A portal is consisted of 2 half-portals.
	 * port1X, port1Y -- the first half-portal's coordinates
	 * port2X, port2Y -- the second half-portal's coordinates
	 * pos1, pos2 -- whether the half-portal(s) is horizontal or vertical
	 * c -- the color of the portal (and its doors)
	 * exit1X, exit1Y -- the coordinates to exit half-portal 1 
	 * exit2X, exit2Y -- the coordinates to exit half-portal 2  
	 */
	public Portal(int port1X, int port1Y, int port2X, int port2Y, int pos1, int pos2, Color c, 
			int exit1X, int exit1Y, int exit2X, int exit2Y) {
		h1 = new HalfPortal(port1X, port1Y, pos1, c);
		h2 = new HalfPortal(port2X, port2Y, pos2, c);
		myColor = c;
		myExit1X = exit1X;
		myExit1Y = exit1Y;
		myExit2X = exit2X;
		myExit2Y = exit2Y;
	}

	/*
	 * returns the color of wanted set of portal-door
	 */
	public Color getColor() {
		return myColor;
	}

	/*
	 * toggle the visibility of each door in the same set of portal
	 */
	public void toggleVisibility() {
		if (count==1) {
			count=0;
		}
		else if(count==0) {
			count=1;
		}
		for (Door d : allMyDoor) {
			d.isVisible=!d.isVisible;
			d.myDoor.setVisible(d.isVisible);
		}
	}

	/*
	 * method for level building
	 * add a door to the arraylist that stores all the doors in the set of portals
	 */
	public void addDoor(int doorX, int doorY, int length, int width, boolean visible) {
		Door d1 = new Door(doorX, doorY, length, width, myColor);
		d1.myDoor.setVisible(visible);
		d1.isVisible = visible;
		d1.initVisible = visible;
		allMyDoor.add(d1);
	}
}
