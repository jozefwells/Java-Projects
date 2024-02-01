# Project: Double-Linked List Tester

* CS221-003
* 11/19/2021
* Jozef Wells

## OVERVIEW:

The IUDoubleLinkedList program is meant to be a representation of a Java API library based double-linked list. 
ListTester is a program with the purpose of testing the functionality and implementation of the Java API library double-linked list methods.


## INCLUDED FILES:

* IndexedUnsortedList.java - source file
* ListTester.java - source file
* IUDoubleLinkedList.java - source file
* Node.java - source file
* README - this file
 

## COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:

 ``
 $ javac ListTester.java
 ``

 Run the compiled class file with the command:

 ``
 $ java ListTester
``

 Console output will give the results after the program finishes.


## PROGRAM DESIGN AND IMPORTANT CONCEPTS:

IUDoubleLinkedList implements IndexedUnsortedList

IUDoubleLinkedList represents a double-linked list, meaning that each location or index, is represented by a node.
Each method of IUDoubleLinkedList is implemented from IndexedUnsortedList.java interface while implementing an iterator. 
Using a DoubleLinkedList means that we are able to add any element to the rear of the collection as an O(1) operation. 
The iterator implementation allows for a better way to navigate through index values. ListTester.java is a program with 
tests for an empty, single element, two element, and three element list. These tests were designed to test proper functionality
of each method in IUDoubleLinkedList.java as well as iterator methods. After each iteration through each node of the class, 
ListTester is meant to test that all methods still function and that each node represents the expected value. 

## TESTING:

The primary resource used for testing was ListTester.java

For designing tests for IUDoubleLinkedList, I mainly focused on implementing tests similar to those provided by the instructor, 
many of the tests were provided to us, therefore I was able to use those as a reference while designing new tests. Many of the 
tests are simple in that they test each and every method of the class with every possible list length. The iterator methods needed
to be tested for proper order of operations of method calls, such that next() had to be called before the remove method. In addition, 
I tested every method and iterator method after any changes to the length of those lists so that they all still function as expected.  


## DISCUSSION:
 
Creating a single and double-linked list had to be the most difficult part of this assignment. I have never encountered a 
double or single-linked list, but found it helpful to read the Java API library for more details. In addition, I found it
very helpful to make diagrams and drawings of the list and how each of the functions operates within that diagram. As for 
the tests, I actually found them very enjoyable to create. It was nice coming from the challenges of creating the linked-lists
to ListTester, because I had a test plan and always knew exactly what I needed to be testing, the only challenges were the 
naming conventions and keeping the formatting correct. In the end, this assignment was an overall enjoyable one, and I think 
that I learned a lot about lists in Java and how to properly test them for implementation and functionality. 
   