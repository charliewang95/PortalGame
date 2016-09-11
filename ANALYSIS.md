Analysis for Project 1  
====
Charlie Wang (qw42)  
9/10/2016


**Project Journal**
----
### Time Review

* Because I heard the first project is not as easy as other classes's first project, I started it two days before school starts. In total, I spent approximately 26 hours in two and a half weeks on this project. 

* The first few days (day 1-3), I was brainstorming about which game genre I'm going to use. Since I'm a huge fan of puzzle games, it is not hard for me to decide on making a puzzle game, both 2D indie games like _Braid_ and 3D puzzle games like _Portal_ and _Talo's Principle_. After settling on a genre, I spent some time (day 4-5) considering what features I'm going to include in this game and its approximate length. That is the time I started and finished DESIGN.txt. Next, I began coding. Based on the Professor Duvall's example "game", I figured I could use 80% of his Main class with just a little change, so the main class doesn't take long. I began writing the game class by starting the _init_ method, and some teaching levels (day 6), then the “soul” of the game, the _Portal_ class (7). Once the portals are correctly set up, making each levels becomes easy. I completed 5 levels quickly (day 8-10). During the rest of time, on average, I completed one level per two days. Besides, I started fixing bugs and adding new features, hints, and cheats. On day 12, I separated the _Door_ class (formerly a subclass in the _Portal_ class) into an individual class, which makes coding and referencing easier. The rest of the time is just debugging and perfecting the quality of the code and comments. 


* 


### Commits
* I made xxx commits in total. 
* 

### Conclusions vvv

* My estimation of the project is 800 lines. In fact, I have about 1100 lines including all the classes, which is a little bit more than my estimation. Because I'm making a puzzle game, the size of the project depends greatly on how many levels I want to make. This time, each level is taking more space than I thought. To better estimate the size, I will determine the number of levels and the length of each level more properly. 

* The level setup methods require the most editing, because to make deliberate levels, I have to try different map setups and gaming flows, along with small coordinate changes multiple times. This requires I put much effort on setting up each map.

* To be a better designer, I should start writing comments (both on methods and in-line comments, if necessary). This will save me a lot of time, because in this project I added comments after I finished most parts of the codes, so it is relatively hard to remember each detail of each method, making the comments not so useful. I will keep factoring my code into small methods, making each method short to increase readability and fluency.

**Design Review**
----
### Status vvv

* I believe all of my variable names make sense and any user can figure out the fundamental use of that variable at first sight. For example, all the instances of Text are properly named as _winText_, _solutionText_, _cheatText_, _levelName_, etc. The list of map objects are named as _myPortal_, _myWall_, _myDoor_, etc. Final variables are named using all capital letters, like _INITLEVEL_, _ENDLEVEL_, _RADIUS_, etc. Also, the method names are self-explaining as well, for example, _setBall_, _reachedPortal_, _addDoor_, _connectPortal_, etc.

* There are very few in-line comments in these files. I tried as hard as possible to make the code logic clear and easy to understand. There might be one slight problem, which is all the magic numbers appearing in each level setup method. As I have mentioned in the header of the game class, I could not find a way to efficiently represent these locations in variables except writing them out, which looks like they come out of nowhere. In fact, these locations are obtained by numerous trial and error, especially where to put the doors, which directly influences the game-play. However, there are some "magic numbers" which are easy to understand, like most positions of the wall. I kind of divided the map into a 4x4 grid to put the walls (but not the doors), the coordinates of walls seem uniform throughout each level.  

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

* This following piece is easy to understand, because all it does is looping through all the doors to check if the player hits one of them. If so, return true immediately and stop the player from moving.
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

### Design vvv

* Since it's a puzzle game, I intended to make each level a different scene and redraw the stage when going to a new level. The scenes are made beforehand when the program starts so the program can use them directly. When a level is called, initialize all the texts first, even if some of them don't have to show immediately. Then based on which level I'm in, set up the board accordingly by calling the correct method. The key inputs change the position of the player and certain set up of the map (along with other features). When the player succeeded, the "Next Level" button shows up, and when it is pressed, jump to the main class and call the next level, and so on. 

* To add a new level, say level 9, just add a new method _private level9()_ in _ImpasseGame_ and add walls, portals, doors with deliberate coordinates like the other levels. Also remember to add an if statement in _chooseLevel_ method and initialize it in the _start_ method in _ImpasseMain_. The final variable _ENDLEVEL_ might need changing as well. Of course, you need to have a deliberate setup and a solution for this level to work. 

* As I will mention in the below section, I couldn't find an efficient way to switch scene automatically, so I added a button for the player to press to go to the next level. Therefore, I made all the levels in one class, and when the "switch scene" is called, make a new instance of the same class but with different map setup method. Also, because I have a "reset" feature, I have to store all the initial position of the map and all the elements, and that's why I put these objects in global variables.

* Feature 1: The last (and hardest) level connects a portal with a door, and they appear and disappear together, which has never happened in previous levels. To achieve this, I added an arraylist<Portal> to the _Door_ object and put all the connected portal to this arraylist. Whenever a door changes visibility, the Portal objects in this list also toggles visibility. Feature 2: The reset feature. I added a "count" variable in the _Portal_ class to store whether I have to toggle the visibility of the doors when reseting. When the "R" key is pressed, visibility is toggled according to _count_, the balls are set to invisible and the program draws them again. The winning message is toggled off of the player has already passed the level.  

### Alternate Designs vvv

* In the final version, I put all the level setup methods in the _ImpasseGame_ class. At some point during the project, I wanted to make a separate class for each level and make _ImpasseGame_ an interface. This will make the _ImpasseGame_ class much shorter. This is the pro. However, in this case, the _ImpasseMain_ class has to initialize each class once, which is more work than initializing a single class multiple times. Furthermore, since a lot of things in each level are similar (like level title, hint text, etc), putting them into one class can save a lot of duplicated code, and after refinement and factoring, each level's setup method is not that long. Also, making new levels becomes easier this way, since I can just add a new method. So in the end I gave up the "separate class" idea and keep the original one. 

* My current "Go to next level" function is by pressing a button after completing a level (or use cheats, of course). This approach asks the player to do action to go to the next level, so the player can replay this level to find new solutions if they want. However, the button approach (the one I implemented) can be buggy sometimes. If there is another button on the scene, they sometimes have conflicts. This is a problem I have not overcome. Another approach would be going to the next level automatically when the player completes this level. In this way, the player cannot replay this level, but it will increase the fluency of the game play. Because of this reason, I think the second approach is better, but I haven't been able to think of a good way to achieve this.

* The first bug is the button conflict described in the above paragraph; After cheat is activated, the ball might move out of the boarder of the map; if a portal is on a door, the player can go through the portal from the other side of the door, so I had to make the door thicker.