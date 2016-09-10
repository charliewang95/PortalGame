import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
	 * This class contains a rectangle that can toggle its visibility
	 *  each time the player enters a portal of the same color
	 */
	public class Door {
		public Rectangle myDoor;
		public ArrayList<Rectangle> myFakeDoor = new ArrayList<Rectangle>();
		public boolean isVisible;
		public boolean initVisible;
		private static final int DOTTEDRECLENGTH = 10;
		public ArrayList<Portal> myList = new ArrayList<Portal>();

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
			if (length>width) {
				int count=0;
				while (count+DOTTEDRECLENGTH<length) {
					Rectangle temp = new Rectangle(doorX+count, doorY, DOTTEDRECLENGTH, width);
					temp.setFill(c);
					myFakeDoor.add(temp);
					count+=2*DOTTEDRECLENGTH;
				}
			}
			else {
				int count=0;
				while (count+DOTTEDRECLENGTH<width) {
					Rectangle temp = new Rectangle(doorX, doorY+count, length, DOTTEDRECLENGTH);
					temp.setFill(c);
					myFakeDoor.add(temp);
					count+=2*DOTTEDRECLENGTH;
				}
			}
		}
		
		public void connectPortal(Portal port) {
			myList.add(port);
		}
	}