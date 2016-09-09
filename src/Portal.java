
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author charliewang95
 *
 */
public class Portal {
	private static final int LENGTH = 50;
	private static final int WIDTH = 10;
	public int myExit1X, myExit1Y, myExit2X, myExit2Y;
	public HalfPortal h1, h2;
	public int count=0;
	public Color myColor;
	public ArrayList<Door> allMyDoor = new ArrayList<Door>();

	/**
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

	/**
	 * Portal constructor
	 * 
	 * @param port1X the first half-portal's x coordinate
	 * @param port1Y the first half-portal's y coordinate
	 * @param port2X the second half-portal's x coordinate
	 * @param port2Y the second half-portal's y coordinate
	 * @param pos1 whether the half-portal(s) is horizontal or vertical (1-vertical, 2-horizontal)
	 * @param pos2 whether the half-portal(s) is horizontal or vertical
	 * @param c the color of the portal (and its doors)
	 * @param exit1X the x coordinate to exit half-portal 1 
	 * @param exit1Y the y coordinate to exit half-portal 1 
	 * @param exit2X the x coordinate to exit half-portal 2 
	 * @param exit2Y the y coordinate to exit half-portal 2 
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

	/**
	 * returns the color of wanted set of portal-door
	 */
	public Color getColor() {
		return myColor;
	}

	/**
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
			for (Portal p : d.myList) {
				p.h1.rec.setVisible(!p.h1.rec.isVisible());
				p.h2.rec.setVisible(!p.h2.rec.isVisible());
			}
		}
	}

	/**
	 * method for level building
	 * add a door to the arraylist that stores all the doors in the set of portals
	 *
	 * @param doorX the x coordinates of the door
	 * @param doorY the y coordinates of the door
	 * @param length the length of the door
	 * @param width the width of the door
	 * @param visible the visibility of the door
	 */
	public void addDoor(int doorX, int doorY, int length, int width, boolean visible) {
		Door d1 = new Door(doorX, doorY, length, width, myColor);
		addDoor(d1, visible);
	}
	
	/**
	 * @param d1 Door objects to be added
	 * @param visible the visibility of the door
	 */
	public void addDoor(Door d1, boolean visible) {
		d1.myDoor.setVisible(visible);
		d1.isVisible = visible;
		d1.initVisible = visible;
		allMyDoor.add(d1);
	}
}
