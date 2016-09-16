
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is part of my code masterpiece
 * 
 * This class contains the structure and methods needed for builder portals.
 * The Portal constructor receives all the parameters needed to know their locations, the exit points, and their pose.
 * Each Portal contains two HalfPortal (the subclass). The subclass may not be necessary for now, but it might useful when the 
 *  creator wants to add more feature to the portal. This increases flexibility of this games future improvement.
 * The portal instance can add a Door object of the same color. There are two ways to add Doors: knowing its location and create a 
 *  door, or add an existing door. When making a level, the creator can thus do something on the door first, then connect it to
 *  the portal. This also increases flexibility.  
 * The toggleVisibility method is called each time the player passes through a portal. the "count" variable stores whether the 
 *  doors need to be toggled back when the level is being reset. 
 * The reason why Door class is not made into the Portal class as a subclass is that a door has to associate some portals, 
 *  as will be described below.
 *
 * @author charliewang95
 *
 */
/**
 * @author charliewang95
 *
 */
public class Portal {
	private static final int LENGTH = 50;
	private static final int WIDTH = 10;
	private int myExit1X, myExit1Y, myExit2X, myExit2Y;
	private HalfPortal h1, h2;
	private int count = 0;
	private Color myColor;
	private ArrayList<Door> allMyDoor = new ArrayList<Door>();

	/**
	 * This class contains a rectangle that represents a portal. Two halfPortals
	 * are one complete pair of portals
	 */
	public class HalfPortal {
		private Rectangle rec;

		/**
		 * @param portX
		 *            the x coordinate of a portal (one in a set of two)
		 * @param portY
		 *            the y coordinate of a portal (one in a set of two)
		 * @param pos
		 *            whether the half-portal is vertical or horizontal
		 * @param c
		 *            color
		 */
		public HalfPortal(int portX, int portY, int pos, Color c) {
			int w = (pos == 1) ? WIDTH : LENGTH;
			int l = (pos == 1) ? LENGTH : WIDTH;
			rec = new Rectangle(portX, portY, w, l);
			rec.setFill(c);
		}
		
		public Rectangle getRec() {
			return rec;
		}
	}

	/**
	 * Portal constructor
	 * 
	 * @param port1X
	 *            the first half-portal's x coordinate
	 * @param port1Y
	 *            the first half-portal's y coordinate
	 * @param port2X
	 *            the second half-portal's x coordinate
	 * @param port2Y
	 *            the second half-portal's y coordinate
	 * @param pos1
	 *            whether the half-portal(s) is horizontal or vertical
	 *            (1-vertical, 2-horizontal)
	 * @param pos2
	 *            whether the half-portal(s) is horizontal or vertical
	 * @param c
	 *            the color of the portal (and its doors)
	 * @param exit1X
	 *            the x coordinate to exit half-portal 1
	 * @param exit1Y
	 *            the y coordinate to exit half-portal 1
	 * @param exit2X
	 *            the x coordinate to exit half-portal 2
	 * @param exit2Y
	 *            the y coordinate to exit half-portal 2
	 */
	public Portal(int port1X, int port1Y, int port2X, int port2Y, int pos1, int pos2, Color c, int exit1X, int exit1Y,
			int exit2X, int exit2Y) {
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
		count = (count == 1) ? 0 : 1;
		for (Door d : allMyDoor) {
			d.toggleVisibility();
			d.getDoor().setVisible(d.getVisibility());
			for (Portal p : d.getPortal()) { //if there are portals on the door, make it disappear too
				p.h1.rec.setVisible(!p.h1.rec.isVisible());
				p.h2.rec.setVisible(!p.h2.rec.isVisible());
			}
		}
	}

	/**
	 * method for level building add a door to the arraylist that stores all the
	 * doors in the set of portals
	 *
	 * @param doorX
	 *            the x coordinates of the door
	 * @param doorY
	 *            the y coordinates of the door
	 * @param length
	 *            the length of the door
	 * @param width
	 *            the width of the door
	 * @param visible
	 *            the visibility of the door
	 */
	public void addDoor(int doorX, int doorY, int length, int width, boolean visible) {
		Door d1 = new Door(doorX, doorY, length, width, myColor);
		addDoor(d1, visible);
	}

	/**
	 * @param d1
	 *            Door objects to be added
	 * @param visible
	 *            the visibility of the door
	 */
	public void addDoor(Door d1, boolean visible) {
		d1.getDoor().setVisible(visible);
		d1.setVisibility(visible);
		allMyDoor.add(d1);
	}
	
	/**
	 * @return the x-coordinate of the exit point of h1
	 */
	public int getExit1X() {
		return myExit1X;
	}
	
	/**
	 * @return the y-coordinate of the exit point of h1
	 */
	public int getExit1Y() {
		return myExit1Y;
	}
	
	/**
	 * @return the x-coordinate of the exit point of h2
	 */
	public int getExit2X() {
		return myExit2X;
	}
	
	/**
	 * @return the y-coordinate of the exit point of h2
	 */
	public int getExit2Y() {
		return myExit2Y;
	}
	
	/**
	 * @return the half-portal h1
	 */
	public HalfPortal geth1() {
		return h1;
	}
	
	/**
	 * @return the half-portal h2
	 */
	public HalfPortal geth2() {
		return h2;
	}
	
	/**
	 * @return the number of times the portal is passed
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * @return the doors associated with the portal
	 */
	public ArrayList<Door> getAllDoors() {
		return allMyDoor;
	}
}
