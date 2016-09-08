import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
	 * This class contains a rectangle that can toggle its visibility
	 *  each time the player enters a portal of the same color
	 */
	public class Door {
		public Rectangle myDoor;
		public boolean isVisible;
		public boolean initVisible;
		public ArrayList<Portal> myList = new ArrayList<Portal>();

		public Door(int doorX, int doorY, int length, int width, Color c) {
			myDoor = new Rectangle(doorX, doorY, length, width);
			myDoor.setFill(c);
		}
		
		public void connectPortal(Portal port) {
			myList.add(port);
		}
	}