This is how the square diamond algorithm works:

First of all, the map size must be (2^n)+1 by (2^n)+1, where n is an integer greater than 0. This means the size of your map can be 2x2, 3x3, 5x5, 9x9, 17x17, 33x33, etc.

Let . = empty cell
Let x = current cell
Let o = previously filled cell

We will assume a depth (i.e. n value) of 3, or a 9x9 map.

########################################################

x . . . . . . . x
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
x . . . . . . . x

In this step, the first thing we do is initialize the corners to some random value.

########################################################

o . . . . . . . o
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . x . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
o . . . . . . . o

Some people argue what consitutes as the square part or the diamond part of the algorithm. I consider this the square part, as the points around this x make a square around it. 
You take the average of the four corners, then add some random value. For now it's not important, the details will be explained later.

########################################################

o . . . x . . . o
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
x . . . o . . . x
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
o . . . x . . . o

Now we perform the diamond step (as the points around each x form a diamond shape). Now for the edges, there are different ways to deal with them, but I usually just have it wrap around. E.g., For the value above the top x, we can treat it like it was an x on the bottom of the map. This isn't the best solution, but it's one way to handle it. Now take the 4 values on the top, left, right and bottom, average them out and add a random value.

########################################################

o . . . o . . . o
. . . . . . . . .
. . x . . . x . .
. . . . . . . . .
o . . . o . . . o
. . . . . . . . .
. . x . . . x . .
. . . . . . . . .
o . . . o . . . o

Another square step. Notice it makes a recurring pattern (though this problem is not solved with recursion. Don't do it. Ever).

########################################################

o . x . o . x . o
. . . . . . . . .
x . o . x . o . x
. . . . . . . . .
o . x . o . x . o
. . . . . . . . .
x . o . x . o . x
. . . . . . . . .
o . x . o . x . o

Another diamond step. Treat the same way as before.

########################################################

o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o
. x . x . x . x .
o . o . o . o . o

Another square step.

########################################################

o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o
x o x o x o x o x
o x o x o x o x o

The final diamond step

########################################################

o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o
o o o o o o o o o

This will work with any map of the dimensions specified above.