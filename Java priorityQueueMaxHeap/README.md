# Project 2: Stardew Valley using a Priority Queue

* Author: Your Name
* Class: CS321 Section 002
* Semester: Fall 2022

## Overview

This program implements and uses a Priority Queue abstract using a MaxHeap 
data structure represented as an array to simulate a priority based task 
management algorithm. This program illustrates a simple example of a 
discrete event simulation.

## Reflection

For this project I was fortunate to already have experience with the
MaxHeap data structure from a Udemy course I took over the summer. This
helped a lot when constructing the MaxHeap class. The implementation of 
a priority queue using MaxHeap was relatively straight forward. The use
of probability when deciding whether to make new tasks was something that
took a bit of thought to figure out at first. 

To successfully implmement a generic priority queue and MaxHeap I had to 
change the PriorityQueueInterface and the MyLifeInStarDew driver class to 
instantiate a generic priority queue. I was not able to match the expected 
output for the MultiplayerLifeInStarDew, but I think I was very close.
I think that my problem lies within the compareTo method of the Player class.

## Compiling and Using

 From the directory containing all source files, compile the
 driver class with the command:
 $ javac MyLifeInStarDew.java 

 Run the compile class file with the command:
 $ java MyLifeInStarDew <max-priority-level> <time-to-increment-priority>
<total simulation-time in days> <task-generation-probability> [<seed>]

Where: 
	<max-priority-level>: Highest possible priority for any given task
	<time-to-increment-priority>: The duration after which the Task's 
	priority will be increased by one if the Task is not finished.
	<total simulation-time in days>: Total time in number of days for simulation 
	<task-generation-probability>: The probability between 0 and 1 used to 
	decide whether to generate a new Task during each hour.
	[<seed>]: A seed for a random number generator for testing to ensure the
	simulation can be replicated with the same results. 

 Console output will give the results after the program finishes.

## Results 

For accurate measurement of the programs functionality, I ran tests using 
the in# and compared the results to those of the corresponding out# test-cases.

## Sources used

Class notes, Udemy coarse: Data Structures and Algorithms: Deep Dive Using Java by: Tim Buchalka and Goran Lochert 