# CS 321 Bioinformatics Project

<hr/>

This repository contains:
- the expected project package structure, in the [src/](src/) folder
- some partial implementation of classes, in the [src/main/java/cs321/](src/main/java/cs321/) folder
- sample [JUnit](https://junit.org/) tests, in the [src/test/java/cs321/](src/test/java/cs321/) folder
- sample input data and expected results, in the [data/](data/) folder
- a wrapper for the [gradle](https://gradle.org/) build tool, which simplifies installing and running gradle. In turn, gradle facilitates and handles:
    - Java library (e.g., JUnit) dependency management
    - Compiling the code
    - Generating self-containing jars
    - Running classes
    - Running unit tests

This project **does not work** with JDK 19.

Instead it is recommended to use either JDK 8, JDK 11 or JDK 17.

## Compile and Run the Project from the Command Line
Gradle allows running unit tests and code from IDEs, or the command line, as described below.

Run all the JUnit tests and print a summary of the results:
```bash
$ ./gradlew test
```

Run the `main` method from `GeneBankCreateBTree.java` and pass the [appropriate `<arguments>`](#51-program-arguments):
```bash
$ ./gradlew createJarGeneBankCreateBTree
$ java -jar build/libs/GeneBankCreateBTree.jar <arguments>
```

Run the `main` method from `GeneBankSearchBTree.java` and pass the [appropriate `<arguments>`](#51-program-arguments):
```bash
$ ./gradlew createJarGeneBankSearchBTree
$ java -jar build/libs/GeneBankSearchBTree.jar <arguments>
```


## Run the project from an IDE: IntelliJ IDEA, VSCode or Eclipse
### Eclipse
This repository is an [Eclipse](https://www.eclipse.org/) project, and can be directly opened in
[Eclipse](https://www.eclipse.org/).

:book: See this [wiki page for additional instructions to run this project in Eclipse](https://github.com/BoiseState/CS321_Bioinformatics/wiki/Instructions-to-run-in-Eclipse).

### IntelliJ IDEA
This project can be opened with [IntelliJ IDEA](https://www.jetbrains.com/idea/).

:book: See this [wiki page for additional instructions to run this project in IntelliJ IDEA](https://github.com/BoiseState/CS321_Bioinformatics/wiki/Instructions-to-run-in-IntelliJ-IDEA).

### VSCode
Alternatively, this project can be opened with [VSCode](https://code.visualstudio.com/).

:book: See this [wiki page for detailed instructions to run this project in VSCode](https://github.com/BoiseState/CS321_Bioinformatics/wiki/Instructions-to-run-in-VSCode).

## Notes for creating additional files and tests, while keeping the Gradle project structure
You can add as many classes as you want in `src/main/java`, and gradle should build
them automatically. In other words, you should not have to make any changes to the `build.gradle`.

Also, you can add new test files with new tests cases in `src/test/java` and those will be run
automatically by gradle or your IDE.

<hr/>

## 1. Introduction
In this assignment, we will solve a problem from the field of Bioinformatics using BTrees.
The amount of data that we have to handle is large and any data structure is not likely to
fit in memory. Hence a BTree is a good choice for the data structure.

## 2. Background
_Bioinformatics_ is the field of science in which biology, computer science, and information
technology merge to form a single discipline. One of the primary aims of Bioinformatics is to
attempt to determine the structure and meaning of the human genome. The _human genome_
is a complete set of human DNA. The Human Genome project was started in 1990 by the
United States Department of Energy and the U.S. National Institutes of Health. By April
14, 2003 99% of the Human Genome had been sequenced with 99.9% accuracy.
The Human Genome is a big strand of 4 different organic chemicals, known as bases, which
are:
- _Adenine_
- _Cytosine_
- _Thiamine_
- _Guanine_

Biologists often call them `A`, `C`, `T`, `G` for short. The bases `A` and `T` are always paired
together. Similarly the bases `C` and `G` are always paired together. So when we look at
the DNA representation, only one side is listed. 
For example: the DNA sequence: `AATGC`
actually represents two sequences: `AATGC` and its complement `TTACG` (replace `A` by `T`, `T`
by `A`, `C` by `G` and `G` by `C`). 
Even with only half the bases represented in a DNA sequence,
the human genome is about **2.87 billion characters** long!

See below an image of the DNA as well as the chemical structure of the bases.

![DNA_physical_and_chemical_structure.png](../../CS321_Bioinformatics/docs/DNA_physical_and_chemical_structure.png "Physical (left) and Chemical (right) Structure of DNA")

The primary source for getting the human genome (as well as all other mapped organisms)
is in the National Center for Biotechnology Information (NCBI) website
(http://www.ncbi.nlm.nih.gov/). See this page for downloading [human genome
data](https://www.ncbi.nlm.nih.gov/genome/guide/human/)

We will be using the GeneBank files from NCBI. The format is described with a sample file
at http://www.ncbi.nlm.nih.gov/Sitemap/samplerecord.html

Most of the information in a GeneBank file is of interest to biologists. We will only be
interested in the actual DNA sequences that are embedded in these files rather than in the
intervening annotations.

## 3. Specifications

### 3.1. Input Files
The GeneBank files have a bunch of annotations followed by the keyword `ORIGIN`. The DNA
sequences start from the next line. Each line has 60 characters (one of `A`, `T`, `C`, `G`, could
be lower/upper case) until the end of sequence, which is denoted by `//` on a line by itself.
Sometimes you will see the character `N`, which denotes that the sequence is not known at
that character. You would skip these characters. One GeneBank file may have several DNA
sequences in it, each marked by `ORIGIN` and `//` tags.

When we reach a character `N`, we assume that the sequence has ended. Similarly, when
we reach `//`, we also assume that the sequence has ended. So at those points, we reset
the sequence that we were building and start over when we find the next `ORIGIN` or the
next valid character after seeing a `N`.

Some sample genome files (having the *.gbk extension) can be found in the [data/files_gbk/](data/files_gbk/) folder.

### 3.2. Problem
For a given GeneBank file, we want to convert it into a BTree with each object being a
DNA subsequence of specified length `k` (where `1` ≤ `k` ≤ `31`). We will take the DNA sequence
from the GeneBank file and break it into subsequences of length `k` each. We are interested in
all subsequences with length `k`. For example, in the sequence `AATTCG`, the subsequences
of length three are: `AAT`, `ATT`, `TTC` and `TCG`. Once we have a BTree for a length `k`, we
want to be able to search for query subsequences of length `k`. The search returns the frequency
of occurrence of the query string (which can be zero if it is not found).

The biological motivation behind is to study the frequency of different length subsequences
to see if they are random or that some subsequences are more likely to be found in the DNA.

## 4. Design Issues

### 4.1. Saving memory
Since we only have four possible bases (`A`, `C`, `G` and `T`), we can optimize
on space by converting our DNA strings to base 4 numbers. Then we can represent each
base by a 2-bit binary number, as shown below:

DNA Base | 2-bit binary number
-------- | -------------------
A        | 00
T        | 11
C        | 01
G        | 10

Note that we have made the binary representations for complementary bases be binary
complements as well. With this compact representation, we can store a 31 length subsequence
in a 64-bit `long` primitive type in Java.

### 4.2. Key Values
Note that the binary compact representation of the subsequences will result
in a unique 64-bit integer value. Hence we can directly use that as our key value.

### 4.3. Class Design
We will need a `BTree` class as well as a `BTreeNode` class. The objects that
we store in the BTree will be similar to the objects we stored in the previous Hashtable
assignment. You may call the relevant class `TreeObject` to represent the objects.

## 5. Implementation
You will have two programs:
- one that **creates a BTree** from a given GeneBank file and
- another for **searching in the specified BTree** for subsequences of given length. The search program
assumes that the user specified the proper BTree to use depending upon the query length.

The main Java classes should be named `GeneBankCreateBTree` and `GeneBankSearchBTree`.

### 5.1. Program Arguments
The required arguments for the two programs are shown below:

```bash
java -jar build/libs/GeneBankCreateBTree.jar --cache=<0|1>  --degree=<btree degree> 
	--gbkfile=<gbk file> --length=<sequence length> [--cachesize=<n>] [--debug=0|1|2|3]


java -jar build/libs/GeneBankSearchBTree.jar --cache=<0/1> --degree=<btree degree> 
	--btreefile=<BTree file> --length=<sequence length> --queryfile=<query file> 
	[--cachesize=<n>] [--debug=0|1|2]
```

**Note that the arguments can be provided in any order.**

- `<0 (no cache) | 1 (cache)>` specifies whether the program should use cache (value `1`) or
no cache (value `0`); if the value is `1`, the `<cache_size>` has to be specified

- `<degree>` is the degree to be used for the B-Tree. If the user specifies `0`, then your
program should choose the optimum degree based on a disk block size of `4096` bytes and the
size of your B-Tree node on disk

- `<gbk_file>` is the input `*.gbk` file containing the input DNA sequences

- `<subsequence_length>` is an integer that must be between `1` and `31` (inclusive)

- `<b-tree_file>` is the B-Tree file generated by the `GeneBankCreateBTree` program

- `<query file>` contains all the DNA strings of a specific subsequence length that we want
to search for in the specified B-Tree file. The strings are one per line and they all must
have the same length as the DNA subsequences in the B-Tree file. The DNA strings use `A`, `C`,
`T`, and `G` (either lower or upper case)

- `[<cache size>]` is an integer between `100` and `500` (inclusive) that represents the maximum
number of `BTreeNode` objects that can be stored in memory

- `[<debug level>]` is an optional argument with a default value of zero.

    - It must support at least the following values for `GeneBankSearchBTree`:

        - `0`: The output of the queries should be printed on the standard output stream. Any diagnostic messages, help and status messages must be be printed on standard error stream.

    - It must support at least the following values for `GeneBankCreateBTree`:

        - `0`: Any diagnostic messages, help and status messages must be be printed on standard error stream.

        - `1`: The program writes a text file named `dump`, containing the frequency and the DNA string (corresponding to the key stored) in an inorder traversal, and has the following line format:

```bash
<frequency> <DNA string>
```

### 5.2. Additional Implementation Remarks

#### 5.2.1. Your programs should always keep the root node in the memory
Write the root node to disk file only at the end of the program and read it in when the program
starts up. In addition, your program can only hold a few nodes in memory. In other words,
you can only use a few BTreeNode variables (including the root) in your program (e.g., root,
parent, current, child, temporary node for split). However, using the `<1 (cache)>` option,
you can store `<cache_size>` `BTreeNode` objects in the cache.

#### 5.2.2. Metadata storage
We need to store some metadata about the B-Tree on disk. For example, we can store the degree
of the tree, the byte offset of the root node (so we can find it), the number of nodes, and
so on. This information could be stored in separate metadata file or it can be stored at the
beginning of the B-Tree file. That is a design choice that is left to the student team.

#### 5.2.3. The B-Tree should be stored as a binary data file on the disk (and not as a text file)
If the name of the GeneBank file is `xyz.gbk`, the subsequence length is `<k>` and the B-Tree
degree is `<t>`, then the name of the B-Tree file should be `xyz.gbk.btree.data`.`<k>.<t>`.

:book: Describe the layout of the B-Tree file on disk as well as any other relevant observations
in the [`README-submission.md`](/README-submission.md) file.

#### 5.2.4 To query for a subsequence, we also query for its complement

Note that our BTree stores only one side of the DNA strand but there is a complementary strand as
well. This implies that when we search for a subsequence, we also need to search for its complement
to get the correct answer. 
For example: the DNA sequence: `AATGC`
actually represents two sequences: `AATGC` and its complement `TTACG` (replace `A` by `T`, `T`
by `A`, `C` by `G` and `G` by `C`). To search for `AATGC`, we will search for both `AATGC` and its
complement `TTACG` to get the result. 

#### 5.2.5. Running Tests
The [data/files_gbk/](data/files_gbk/) folder contains:
- sample gbk data files

The [data/queries/](data/queries/) folder contains:
- sample query files
- a sample program named [`QueryGenerator.java`](data/queries/QueryGenerator.java) that
generates random queries for testing


## 9. Test Scripts

Several test gbk files are provided in the folder: 
[data/files_gbk](https://github.com/BoiseState/CS321_Bioinformatics/tree/master/data/files_gbk).

These include:
```bash
test0.gbk
test1.gbk
test2.gbk
test3.gbk
test4.gbk
test5.gbk
```

Results are provided for the following sample query files. 
```bash
query1
query2
query3
query4
query5
query6
query7
query8
query9
query10
query20
query31
```

To generate additional queries, we can use the `data/queries/QueryGenerator.java` program.


Three test scripts are provided at the top-level of the project:

```bash
./create-btrees.sh 
Usage:  create-btrees.sh  <datafile> 

./check-dumpfiles.sh 
Usage:  check-dumpfiles.sh  <datafile> 

./check-queries.sh 
Usage:  check-queries.sh  <datafile> 
```

Please note that the scripts require the name of the test file and then it will add the
appropriate path to the name to fnd it.  The `check-btrees.sh` script creates B-Trees for the given
data file for strings of length 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 31. The `check-dumpfiles.sh`
compares the dump files from our code to the reference dump files. The `check-queries.sh`
script compares the results of queries to our program with the reference results.

The results are provided for test0.gbk and test5.gbk data files. You can use the test scripts
to run and compare results using the three test scripts as follows.

```bash
./gradlew createJarGeneBankCreateBTree
./gradlew createJarGeneBankSearchBTree

./create-btrees.sh test0.gbk
./check-dumpfiles.sh test0.gbk
./check-queries.sh test0.gbk
```

Then repeat for test5.gbk.  
