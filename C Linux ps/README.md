# CS253 Final Project: myps

## Project Background
The myps project is drawn from the pool of warmup projects used in the Operating Systems (CS453/CS452) course. These warmup projects are assigned to help students identify weak areas in their C programming skillset so they can quickly get up to speed before the first OS programming project. I chose this particular project because it aligns particularly well with the content we've covered over the last four weeks.  

In this project you will write a simplified version of the ps command found on Linux/Unix based systems. The purpose of this command is to display the current processes on the system and some basic metadata including the process id number (PID) as well as the associated command (COMM/CMD). For your final project you will develop a simple program that loads information from the proc file system and displays it to the user with options provided to change the order that processes are displayed. For debugging/testing purposes an option will also be added to specify an alternate directory to load process data from.


### Overview
The myps tool navigates to each PID directory in /proc (or other specified directory), opens the stat file, and extracts the required fields to build a ProcEntry struct. A pointer to this ProcEntry struct will be stored in an array of ProcEntry struct pointers.  Once all the PID directories have been processed and the associated ProcEntry structs have been created with pointers added to the array, the array will be sorted based upon user specified criteria and displayed in the console. 

The code to output both the array column headers as well as displaying individual ProcEntry structs has been provided and must not be changed. A portion of the grade for this project will depend upon exact output matching.


### Integration Testing

**Compare output for pid sort of onyx test data to expected using diff**
```
./myps -d test_data/onyx_proc -p > myps-pid_sort.out
diff myps-pid_sort.out test_data/onyx_proc_expected/myps-pid_sort.out
<< No output means success! >>
echo $?
0
```

**Compare output for command sort of onyx test data to expected using diff**  
**NOTE:** Due to duplicate command data and no requirement for secondary sorting of PIDs, we must strip unneeded fields and only focus on the commands themself in this validation test.
```
./myps -d test_data/onyx_proc -c | awk '{print $7}'> myps-cmd_sort-stripped.out
cat test_data/onyx_proc_expected/myps-cmd_sort.out | awk '{print $7}'> test_data/onyx_proc_expected/myps-cmd_sort-stripped.out
diff myps-cmd_sort-stripped.out test_data/onyx_proc_expected/myps-cmd_sort-stripped.out
<< No output means success! >>
echo $?
0
```

**Compare output for zombie only listing of onyx test data to expected using diff**
```
./myps -d test_data/onyx_proc -z > myps-zombie_only.out
diff myps-zombie_only.out test_data/onyx_proc_expected/myps-zombie_only.out
<< No output means success! >>
echo $?
0
```
