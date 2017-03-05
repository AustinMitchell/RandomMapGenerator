NOTE: To use this project, it requires the .jar file for SimpleGui_V2, which can be found on my github page. A compatible version is included in the git project.

This is how the Square-Diamond (i.e. 2D Midpoint Displacement) algorithm works:

First of all, the map size must be (2^n)+1 by (2^n)+1, where n is an integer greater than 0. This means the size of your map can be 2x2, 3x3, 5x5, 9x9, 17x17, 33x33, etc.

Let . = empty cell
Let x = current cell
Let o = previously filled cell

We will assume a depth (i.e. n value) of 3, or a 9x9 map.

########################################################
```
x . . . . . . . x
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
x . . . . . . . x
```
In this step, the first thing we do is initialize the corners to some random value.

########################################################
```
o . . . . . . . o
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . x . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
o . . . . . . . o
```
Some people argue what consitutes as the square part or the diamond part of the algorithm. I consider this the square part, as the points around this x make a square around it. 
You take the average of the four corners, then add some random value. For now it's not important, the details will be explained later.

########################################################
```
o . . . x . . . o
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
x . . . o . . . x
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
o . . . x . . . o
```
Now we perform the diamond step (as the points around each x form a diamond shape). Now for the edges, there are different ways to deal with them, but I usually just have it wrap around. E.g., For the value above the top x, we can treat it like it was an x on the bottom of the map. This isn't the best solution, but it's one way to handle it. Now take the 4 values on the top, left, right and bottom, average them out and add a random value.

########################################################
```
o . . . o . . . o
. . . . . . . . .
. . x . . . x . .
. . . . . . . . .
o . . . o . . . o
. . . . . . . . .
. . x . . . x . .
. . . . . . . . .
o . . . o . . . o
```
Another square step. Notice it makes a recurring pattern. You can use recursion, but the clean solution will end up similar to the iterative pattern anyways, which is faster without having to generate tons of stack frames.

########################################################
```
o . x . o . x . o
. . . . . . . . .
x . o . x . o . x
. . . . . . . . .
o . x . o . x . o
. . . . . . . . .
x . o . x . o . x
. . . . . . . . .
o . x . o . x . o
```
Another diamond step. Treat the same way as before.

########################################################
```
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
```
Another square step.

########################################################
```
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
```
The final diamond step

########################################################
```
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
```
This will work with any map of the dimensions specified above. The traversal is quite simple: 

```java

/** Traversal for the map
	@param depth		Number of steps to perform, determines map size (1=3x3, 2=5x5, 3=9x9, 4=17x17, etc) 
	@param min 			Minimum value any point on the map can be
	@param max 			Maximum value any point on the map can be
	@param h 			'Roughness' constant; Should be from 0 to 1, 0 gives a rough map and 1 gives a smooth one **/
private static float[][] generateMap(int depth, float min, float max, float h) {	
	int mapSize = (int)Math.pow(2, depth) + 1; 		// Side length of map
	float randRange = (max-min)/2;					// Possible random range for the current set of points
	float[][] newMap = new float[mapSize][mapSize];	// Array where the values are mapped
	
	// Initializing the corners
	 newMap[0][0] = random(min, max);
	 newMap[0][mapSize-1] = random(min, max);
	 newMap[mapSize-1][0] = random(min, max);
	 newMap[mapSize-1][mapSize-1] = random(min, max);
	
	for (int n=depth; n>0; n--) {
		int step = (int)Math.pow(2,n-1);
		// Square algorithm step
		for (int i=step; i<mapSize-1; i+=step*2) {
			for (int j=step; j<mapSize-1; j+=step*2) {
				newMap[i][j] = square(i, j, step, min, max, randRange, newMap);
			}
		}
		int loopCount = 0;
		// Diamond algorithm step
		for (int i=0; i<mapSize; i+=step) {
			for (int j=((loopCount&1)==0?step:0); j<mapSize; j+=step*2) {
				newMap[i][j] = diamond(i, j, step, min, max, randRange, newMap);
			}
			loopCount += 1;
		}
		randRange *= Math.pow(2, -h);
	}
	
	return newMap;
}
```
First of all, we create this value called 'step'. This tells us how far into the map we should step to find the next value, or at least it gives us a basis to use to find how far to step.