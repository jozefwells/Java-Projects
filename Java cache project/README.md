****************
* Lab 1
* CS321-002
* 08/29/2022
* Jozef Wells
**************** 

OVERVIEW:

The cache.java program is meant to be a representation of a cache implementation using the linked list data structure
meant to read from a file and store read data into a single or two layer cache. The Test.java program is meant to test the functionality 
of a cache data structure in cache.java. 


INCLUDED FILES:

cache.java - source file
Test.java - source file
README - this file
 

COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class with the command:
 $ javac Test.java 

 Run the compile class file with the command:
 $ java Test 1 <cache size> <input textfile name> or $ java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

cache.java

cache.java represents a cache while utilizing linked list, meaning that each location or index, is represented by a node.
A cache needs a hit, clearCache, add, and removeLast methods for proper functionality. I also implemented a size and toString 
method to test the contents of the cache. The hit method returns true when the cache already contains the item specified. 
The add methods will add each read data type to the front of the cache. If that data is already present, it is removed and 
added to the front. clearCache removes all the contents of the cache. 

TESTING:

The primary resource used for testing was Test.java

The test class takes 33 seconds in Onyx to run.
The test class I wrote is meant to create a single or two layer cache and keep track of the references and hits read from the text file while adding 
each newly read piece of data to the top of the cache. I utilized the cache.java class's add method to add new words to the top of the cache. I also utilize
the boolean hit method to find out if a newly read word is already in the cache. In addition I used the toString method to test that the cache is working properly. 
With the two level cache, my approach was similar. I counted the number of hits that should occur with the first layer and closely followed the 
if and else statement provided in the lab1.pdf assignment outline. I utilized the result1k2k and result1k5k test files to make certain that my program was
operating properly. 
 


DISCUSSION:
 
 The concept of a cache was a new one to me that I have not had much experience with, so I did some extra research on caches and 
 how they function. I had to create drawings to help me visualize the functionality of the two layer cache. I found that my double-linked
 list from CS221 helped me in the writing of my cache. I also found the drawings that Professor Yeh illustrated during class very informative
 and helpful to the planning and execution of the project. 
 
 