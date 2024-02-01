![CS253 Banner](images/CS253-BANNER.svg)
# Module 12 Lab Guide

## Lab Activity - Writing Basic System Tools
### Lab Overview
In this lab, we will implement four of the most common command-line system tools: cat, grep, sort, and wc.  Alone, each of these tools provides minimal functionality, but when combined together using pipes, these tools become incredibly powerful. The sections below include a description of each tool, requirements and a walkthrough video showing step-by-step implementation of each tool.  

Learning the C programming component is an important aspect of this activity.  Equally important is developing an understanding of how simply software tools can be developed in a modular fashion and interconnected using pipes to solve difficult problems.

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
