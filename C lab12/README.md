![CS253 Banner](images/CS253-BANNER.svg)
# Module 12 Lab Guide
[Lab Introduction Video](https://youtu.be/rWtosHpSOzw)

### Code Style Requirements
Please review the [CS253 Style Guide](https://docs.google.com/document/d/1zKIpNfkiPpDHEvbx8XSkZbUEUlpt8rnZjkhCSvM-_3A/edit?usp=sharing) and apply it in all lab warmups, lab activities and projects this semester. Coding Style will assessed as part of your lab and project grades.

### Code Quality Requirements
- Code must compile without warnings using the provided Makefile
- Programs must handle unexpected user input and either reprompt (loops) or gracefully exit with a non-zero exit status.
- Programs must handle error conditions gracefully, without crashing, ideally by checking function returns codes (if available) and returning a non-zero exit status.
- Programs should be free of memory related errors, buffer overflows, stack smashing, leaks, etc... Whether the program crashes or not. This will be validated using valgrind.


## Lab Activity - Writing Basic System Tools
### Lab Overview
In this lab, we will implement four of the most common command-line system tools: cat, grep, sort, and wc.  Alone, each of these tools provides minimal functionality, but when combined together using pipes, these tools become incredibly powerful. The sections below include a description of each tool, requirements and a walkthrough video showing step-by-step implementation of each tool.  

Learning the C programming component is an important aspect of this activity.  Equally important is developing an understanding of how simply software tools can be developed in a modular fashion and interconnected using pipes to solve difficult problems.  After all, at its heart, Computer Science is all about problem solving. :)

### Activity 1 - The mycat tool
The **mycat*** tool provides a similar function to the [**cat**](https://github.com/coreutils/coreutils/blob/master/src/cat.c) tool available on most Unix/Linux based systems. It will open the file specified with the '-f' option, read the contents of the file and write the data (unaltered) to *stdout*.  If no file is specified, it will read input from *stdin* and write the data (unaltered) to *stdout*. 

[Lab Walkthrough - mycat](https://youtu.be/fcjQP87dzxg)  


#### Example usage of mycat tool
```
Usage: ./mycat [-f <file>] [-h]
```

#### Testing: Check for memory errors using valgrind
```
make memtest-mycat

==16199== Memcheck, a memory error detector
==16199== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==16199== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==16199== Command: ./mycat -f american-english.dict.txt
==16199== 
==16199== 
==16199== HEAP SUMMARY:
==16199==     in use at exit: 0 bytes in 0 blocks
==16199==   total heap usage: 3 allocs, 3 frees, 8,664 bytes allocated
==16199== 
==16199== All heap blocks were freed -- no leaks are possible
==16199== 
==16199== For lists of detected and suppressed errors, rerun with: -s
==16199== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

#### Testing: Check mycat results against cat using diff tool
```
./mycat -f american-english.dict.txt > mycat.results.out
cat american-english.dict.txt > cat.results.out
diff mycat.results.out  cat.results.out
echo $?
<diff should produce no output and the exit status of diff should be zero>
```


### Activity 2 - The mygrep tool
The **mygrep** tool provides a similar function to the [**grep**](https://git.savannah.gnu.org/cgit/grep.git/tree/src/grep.c) tool available on most Unix/Linux based systems. It will open the file specified with the '-f' option, read the contents of the file and write any lines that contain the search filter specified with the '-s' option to *stdout*.  If no file is specified, it will read input from *stdin* and write the matching lines to *stdout*. If no search filter is specified, the program should display the usage message to stderr and exit with a non-zero exit status.

[Lab Walkthrough - mygrep](https://youtu.be/3jTkpduuRew) 

#### Example usage of mygrep tool
```
Usage: ./mygrep -s <filter> [-f <file>] [-h]
```

#### Testing: Check for memory errors using valgrind
```
make memtest-mygrep 
valgrind --tool=memcheck --leak-check=yes --show-reachable=yes ./mygrep -f american-english.dict.txt -s tree > /dev/null

==18033== Memcheck, a memory error detector
==18033== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==18033== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==18033== Command: ./mygrep -f american-english.dict.txt -s tree
==18033== 
==18033== 
==18033== HEAP SUMMARY:
==18033==     in use at exit: 0 bytes in 0 blocks
==18033==   total heap usage: 3 allocs, 3 frees, 8,664 bytes allocated
==18033== 
==18033== All heap blocks were freed -- no leaks are possible
==18033== 
==18033== For lists of detected and suppressed errors, rerun with: -s
==18033== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

#### Testing: Check mygrep results against grep using diff tool
```
./mygrep -s war -f War-and-Peace.txt > mygrep.results.out
grep "war" War-and-Peace.txt > grep.results.out
diff mygrep.results.out grep.results.out
echo $?
<diff should produce no output and the exit status of diff should be zero>
```


### Activity 3 - The mywc tool
The **mywc** tool provides a similar function to the [**wc**](https://github.com/coreutils/coreutils/blob/master/src/wc.c) tool available on most Unix/Linux based systems. It will open the file specified with the '-f' option, read the contents of the file and display the number of characters, number of words and number of lines to *stdout*.  If no file is specified, it will read input from *stdin* and display the files stats to *stdout*. The user may optionally specify any combination of the options '-c', '-w', and '-l' to limit the stats to characters, words and lines respectively.

From the **wc** manpage: *A word is a non-zero-length sequence of characters delimited by white space.*

[Lab Walkthrough - mywc](https://youtu.be/ffTZzEePYTI) 

#### Example usage of mywc tool
```
Usage: ./mywc [-f <file>] [-c] [-w] [-l] [-h]
```

#### Testing: Check for memory errors using valgrind
```
make memtest-mywc 
valgrind --tool=memcheck --leak-check=yes --show-reachable=yes ./mywc -f american-english.dict.txt > /dev/null

==19185== Memcheck, a memory error detector
==19185== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==19185== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==19185== Command: ./mywc -f american-english.dict.txt
==19185== 
==19185== 
==19185== HEAP SUMMARY:
==19185==     in use at exit: 0 bytes in 0 blocks
==19185==   total heap usage: 3 allocs, 3 frees, 8,664 bytes allocated
==19185== 
==19185== All heap blocks were freed -- no leaks are possible
==19185== 
==19185== For lists of detected and suppressed errors, rerun with: -s
==19185== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

#### Testing: Check mygrep results against grep using diff tool
```
./mywc -f War-and-Peace.txt 
  66055  580201 3359550
wc  War-and-Peace.txt 
  66055  566309 3359550 War-and-Peace.txt
WTF?
```

### Activity 4 - The mysort tool
The **mysort** tool provides a similar function to the [**sort**](https://github.com/wertarbyte/coreutils/blob/master/src/sort.c) tool available on most Unix/Linux based systems. It will open the file specified with the '-f' option, read the contents of the file and write the lines, sorted lexicographically in ascending order, to *stdout*.  If no file is specified, it will read input from *stdin*. The user may optionally specify the '-r' option to reverse the ordering and write the results in descending order. 

NOTE: The memory usage for this tool should dynamically adjust based upon the number of lines read in the input.  Begin with sufficient capacity to store 16 lines, then use the heuristic of doubling the capacity each time the storage array fills.

[Lab Walkthrough - mysort](https://youtu.be/7SZWxQ9fMxo) 

#### Example usage of mysort tool
```
Usage: ./mysort [-f <file>] [-r] [-h]
```

#### Testing: Check for memory errors using valgrind
```
make memtest-mysort 
valgrind --tool=memcheck --leak-check=yes --show-reachable=yes ./mysort -f american-english.dict.txt -r > /dev/null

==21116== Memcheck, a memory error detector
==21116== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==21116== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
==21116== Command: ./mysort -f american-english.dict.txt -r
==21116== 
Growing Array: 16 -> 32
Growing Array: 32 -> 64
Growing Array: 64 -> 128
Growing Array: 128 -> 256
Growing Array: 256 -> 512
Growing Array: 512 -> 1024
Growing Array: 1024 -> 2048
Growing Array: 2048 -> 4096
Growing Array: 4096 -> 8192
Growing Array: 8192 -> 16384
Growing Array: 16384 -> 32768
Growing Array: 32768 -> 65536
Growing Array: 65536 -> 131072
==21116== 
==21116== HEAP SUMMARY:
==21116==     in use at exit: 0 bytes in 0 blocks
==21116==   total heap usage: 102,419 allocs, 102,419 frees, 3,999,695 bytes allocated
==21116== 
==21116== All heap blocks were freed -- no leaks are possible
==21116== 
==21116== For lists of detected and suppressed errors, rerun with: -s
==21116== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

#### Testing: Manually confirm that lines are sorted lexicographically in ascending (default) and descending (-r) order
```
./mysort -f random_words.txt 
Ankara
Romulus
banyans
bridesmaids
pixel's

./mysort -f random_words.txt -r
pixel's
bridesmaids
banyans
Romulus
Ankara
```



### Implementation Guide (Complete the following for each tool)
1. Expand the folder named LabActivity and open the .c file for the current system tool: mycat, mygrep, mysort or mywc
2. Enter the program code to create an application as described in the tool description
3. Test each program using the provided [Lab Activity Test Plan](LabActivityTestPlan.md) to ensure it functions as expected.
4. Run the program with valgrind to catch any memory leaks or errors
5. Commit the changes to your local repository with a message stating that the tool implementation is completed.
6. Push the changes from your local repository to the github classroom repository.
7. Update the Coding Journal with an entry describing your experience using the steps outlined below.

## Coding Journal (Optional)
Keep a journal of your activities as you work on this lab. Many of the best engineers that I have worked with professionally have kept some sort of engineering journal. I personally packed notebooks around with me for nearly 8 years before I began keeping my notes electronically.   

Your journal can track ideas, bugs, cool links, code snippets, shell commands, rants, or simply a reflection on what worked well or not-so-well with this lab activity. I will not be grading the content of your journal, but I will expect at least two timestamped journal entries of at least a 75 to 150 words each added to the provided Journal.md file.  The purpose of this component is to help develop the habit of taking notes and creating documentation while you code. The more detail you provide the better as that will help you if you ever need to refer back to this project in the future.

## Markdown Resources
Markdown is a notation that is used to format text documents.  It is widely used in Software Development shops around the world, which is why we're asking you to use it in your lab documentation.  

Github provides a guide for getting started:  [Mastering Markdown](https://guides.github.com/features/mastering-markdown/)
