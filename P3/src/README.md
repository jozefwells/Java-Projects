# Project 3: HashTable

* Author: Jozef Wells
* Class: CS321 Section 002
* Semester: Fall 2022

## Overview

This program is meant to represent a HashTable that implements linear probing
and double hashing. The objects to be inserted are stored as key values in 
HashObjects. There are three data types to be inserted into the hashTables, 
a word file, dates, and random numbers.

## Reflection

This project was much easier in some aspects than the last, but also came with
some unforeseen difficulties. It took a while to figure out how to find a prime
number using the binary values, but I can see how much more efficient that method 
was. The second issue I found difficulty with was the double hashing algorithm, 
I had to make sure that the hashed key was being generated properly and that if the 
double hash method produced a value outside the table then it would loop around 
as a circular array does. 

## Compiling and Using

From the directory containing all source files, compile the
driver class with the command:
$ javac HashTest.java

Run the compile class file with the command:
$ java HashTest <input type> <load factor> [<debug level>]

Where:
<input type>: 1 - each HashObject contains an Integer object with a random int value.
              2 - each HashObject contains a Date object.
              3 - each HashObject contains a word from the word-list text file
<load factor>: The percentage that the table should be filled to.
[<debug level>]: The method in which the results should be printed
              0 - results are printed to the console
              1 - results are printed to the console and are printed to two files: linear-dump and double-dump

Console output will give the results after the program finishes.

## Results

For accurate measurement of the program's functionality, I ran tests on word-list
and compared the results to the sample results provided.


Input source 1: random number

| alpha | linear | double |
|-------|--------|--------| 
| 0.5   | 1.499  | 1.390  |
| 0.6   | 1.731  | 1.523  |
| 0.7   | 2.162  | 1.725  |
| 0.8   | 3.017  | 2.016  |
| 0.9   | 5.641  | 2.548  |
| 0.95  | 10.075 | 3.157  |
| 0.98  | 22.897 | 3.986  |
| 0.99  | 45.938 | 4.654  |

Input source 2: date

| alpha | linear | double |
|-------|--------|--------| 
| 0.5   | 1.0    | 1.0    |
| 0.6   | 1.0    | 1.0    |
| 0.7   | 1.0    | 1.0    |
| 0.8   | 1.0    | 1.0    |
| 0.9   | 1.0    | 1.0    |
| 0.95  | 1.0    | 1.0    |
| 0.98  | 1.0    | 1.0    |
| 0.99  | 1.0    | 1.0    |

Input source 3: word-list

| alpha | linear  | double |
|-------|---------|--------| 
| 0.5   | 1.597   | 1.390  |
| 0.6   | 2.149   | 1.534  |
| 0.7   | 3.604   | 1.721  |
| 0.8   | 6.708   | 2.016  |
| 0.9   | 19.815  | 2.569  |
| 0.95  | 110.594 | 3.186  |
| 0.98  | 324.206 | 4.020  |
| 0.99  | 471.671 | 4.696  |
## Sources used

Class notes, Udemy coarse: Data Structures and Algorithms: Deep Dive Using Java by: Tim Buchalka and Goran Lochert 