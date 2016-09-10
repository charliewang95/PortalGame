Analysis for Project 1  
====
Charlie Wang (qw42)  
9/10/2016


**Project Journal**
----
### Time Review

* Because I heard the first project is not as easy as other classes's first project, I started it two days before school starts. In total, I spent approximately 26 hours in two and a half weeks on this project. 
* The first few days (day 1-3), I was brainstorming about which game genre I'm going to use. It is not hard for me to decide on making a puzzle game, since I'm a huge fan of puzzle games, both 2D indie games like _Braid_ and 3D puzzle games like _Portal_ and _Talo's Principle_. After settling on a genre, I spent some time (day 4-5) considering what features I'm going to include in this game and its approximate length. That is the time I started and finished DESIGN.txt. Next, I began coding. Based on the Professor Duvall's example "game", I figured I could use 80% of his Main class with just a little change, so the main class doesn't take long. I began writing the game class by starting the _init_ method, and some teaching levels (day 6), then the “soul” of the game, the _Portal_ class (7). Once the portals are correctly set up, making each levels becomes easy. I completed 5 levels quickly (day 8-10). During the rest of time, on average, I completed one level per two days. Besides, I started fixing bugs and adding new features, hints, and cheats. On day 12, I separated the _Door_ class (formerly a subclass in the _Portal_ class) into an individual class, which makes coding and referencing easier. The rest of the time is just debugging and perfecting the quality of the code and comments. 
* I made 


### Commits
### Conclusions

**Design Review**
----
### Status

* I believe all of my variable names make sense and any user can figure out the fundamental use of that variable at first sight. For example, all the instances of Text are properly named as _winText_, _solutionText_, _cheatText_, _levelName_, etc. The list of map objects are named as _myPortal_, _myWall_, _myDoor_, etc. Final variables are named using all capital letters, like _INITLEVEL_, _ENDLEVEL_, _RADIUS_, etc. Also, the method names are self-explaining as well, for example, _setBall_, _reachedPortal_, _addDoor_, _connectPortal_, etc.

* There are very few in-line comments in these files. I tried as hard as possible to make the code logic clear and easy to understand. There might one slight problem, which is all the magic numbers appearing in each level setup method. As I have mentioned in the header of the game class, I could not find a way to efficiently represent these locations in variables except writing them out, which looks like they come out of nowhere. In fact, these locations are obtained by numerous trial and error, especially where to put the doors, which directly influences the game-play. However, there are some "magic numbers" which are easy to understand, like most positions of the wall. I kind of divided the map into a 4x4 grid to put the walls (but not the doors), the coordinates of walls seem uniform throughout each level.  

* In the _ImpasseGame_ class, there are many private global variables that all the methods can use. That is because there are many texts or cheats which can be toggled on or off throughout the game. When something's visibility needs to be toggled, using global variables makes things easy since I can use a one-line command to toggle and don't need to retrieve the texts. Some variables are used across different classes. All the shapes and methods in the _Portal_ class and _Door_ class are made public so that the set-up methods can access them. Besides, most of the methods and variables in the _ImpasseGame_ class are private, but some are shared by multiple classes. The _init_ method is public so that it can be called by _ImpasseMain_. One important "back channel" is that the _ButtonClicked_ method is defined in _ImpasseMain_, while the "Next Level" buttons are created in _ImpasseGame_. Whenever the button is pressed, the control is given back to _ImpasseMain_ so that it can change the scene into the next one.

* The following piece of code might be a little hard to understand, not because of the syntax, but because of the reason for creating an ArrayList _myFakeDoor_. This is because I want to make the open doors dotted lines instead of just disappearing. Since the closed doors will cover the dotted lines, it's easier to just draw the dotted lines and leave them there all the time. The dotted lines are created by generating small rectangles every _DOTTEDRECLENGTH_ and the orientation might change according to the doors orientation.
```java
	public Rectangle myDoor;
	public ArrayList<Rectangle> myFakeDoor = new ArrayList<Rectangle>(); //dotted (open) doors
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
```

* This following piece is easy to understand, since all it does is looping through all the doors to check if the player hits one of them. If so, return true immediately and stop the player from moving.
```java
	private boolean touchedDoor() {
		if (cheat==true) return false;
		for (Portal p : myPortal) {
			for (Door d : p.allMyDoor) {
				Shape intersect = Shape.intersect(myBall, d.myDoor);
				if (intersect.getBoundsInLocal().getWidth() != -1 && d.isVisible) {
					return true;
				}
			}
		}
		return false;
	}
```


### Design
### Alternate Designs