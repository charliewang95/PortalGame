import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is part of my code masterpiece
 * 
 * This class contains a rectangle that can toggle its visibility
 *  each time the player enters a portal of the same color
 * When a door is created, some dotted lines are created as open doors. Therefore, toggling the rectangle will do the
 *  the trick, instead of having to add dotted lines each time a door opens. This saves a lot of time. 
 * A door object might be associated with a list of portals, which means these portals are on the door itself. When the door
 *  opens, the portals in this list toggles visibility as well. This is why Door and Portal are made into two different 
 *  classes - to avoid conflict.
 *
 * @author Charlie Wang
 *
 */
	/**
	 * @author charliewang95
	 *
	 */
	public class Door {
		private Rectangle myDoor;
		private ArrayList<Rectangle> myFakeDoor = new ArrayList<Rectangle>(); //dotted (open) doors
		private boolean isVisible;
		private static final int DOTTEDRECLENGTH = 10;
		private ArrayList<Portal> myList = new ArrayList<Portal>();

		/**
		 * @param doorX the x coordinates of the door
		 * @param doorY the y coordinates of the door
		 * @param length the length of the door
		 * @param width the width of the door
		 * @param c door's color
		 */
		public Door(int doorX, int doorY, int length, int width, Color c) {
			myDoor = new Rectangle(doorX, doorY, length, width);
			myDoor.setFill(c);
			
			//add dotted lines for opened doors using a counter
			int count=0;
			while (count+DOTTEDRECLENGTH < ((length>width)?length:width)) {
				Rectangle temp;
				if (length>width) 
					temp = new Rectangle(doorX+count, doorY, DOTTEDRECLENGTH, width);
				else 
					temp = new Rectangle(doorX, doorY+count, length, DOTTEDRECLENGTH);
				temp.setFill(c);
				myFakeDoor.add(temp);
				count+=2*DOTTEDRECLENGTH;
			}
		}
		
		/**
		 * The method to bond a portal (and its counterpart) to a door;
		 * when the door opens, the portal on it disappears
		 * @param port
		 */
		public void connectPortal(Portal port) {
			myList.add(port);
		}
		
		/**
		 * @return the rectangle shape
		 */
		public Rectangle getDoor() {
			return myDoor;
		}
		
		/**
		 * @return the dotted lines
		 */
		public ArrayList<Rectangle> getFakeDoor() {
			return myFakeDoor;
		}
		
		/**
		 * @return whether the door is visible
		 */
		public boolean getVisibility() {
			return isVisible;
		}
		
		/**
		 * Change the visibility of the door
		 */
		public void toggleVisibility() {
			isVisible=!isVisible;
		}
		
		/**
		 * @param visible Sets the visibility of the door
		 */
		public void setVisibility(boolean visible) {
			isVisible=visible;
		}
		
		/**
		 * @return the portals on this door
		 */
		public ArrayList<Portal> getPortal() {
			return myList;
		}
	}