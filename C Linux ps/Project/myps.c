/* 
 * Author: Luke Hindman
 * Date: Fri 13 Nov 2020 12:21:37 PM PST
 * Description: Starter code for final project (myps)
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>
#include <ctype.h>
#include "ProcEntry.h"

#define MAX_PATH_LENGTH 4096

#define UNUSED(x) (void)x

int noSort(const struct dirent ** entryA, const struct dirent ** entryB) {
    UNUSED(entryA);
    UNUSED(entryB);
    return 0;
}

static int pidSort(const void *a, const void *b) {
     ProcEntry *f = *(ProcEntry **)a;
     ProcEntry *s = *(ProcEntry **)b;
     int rval = f->process_id - s->process_id;
     return rval;
}

static int commSort(const void *a, const void *b) {
    ProcEntry *f = *(ProcEntry **)a;
    ProcEntry *s = *(ProcEntry **)b;
    int rval = strcmp(f->comm, s->comm);
    return rval;
}

static int defaultFilter(const struct dirent *current) {
    UNUSED(current);
    return 1;    
}

static int defaultProcFilter(const ProcEntry *current) {
    UNUSED(current);
    return 1;
}

static int zombieFilter(const ProcEntry * current) {
    return current->state == 'Z';
}

void printUsage(void);

int main (int argc, char * argv[]) {
    struct dirent **eps;
    int n;
    int opt;

    /* Declare filterFunction */
    int (*filterFunction)(const struct dirent *);
    filterFunction = defaultFilter;

    int (*procFilterFunction)(const ProcEntry *);
    procFilterFunction = defaultProcFilter;

    /* Declare sortFunction pointer */
    int(*sortFunction)(const struct dirent **, const struct dirent **);
    sortFunction = noSort;

    int(*procSortFunction)(const void *a, const void *b);
    procSortFunction = pidSort;

    /* Declare dirPath and set default to current directory */
    char dirPath[MAX_PATH_LENGTH];
    strcpy(dirPath, "/proc");

    /*Use getopt() to process command line arguments */
    while((opt = getopt(argc, argv, "d:pczh")) != -1) {
        switch(opt) {
            case 'd':
                strncpy(dirPath,optarg,MAX_PATH_LENGTH);
                break;
            case 'p':
                procSortFunction = pidSort;
                break;
            case 'c':
                procSortFunction = commSort;
                break;
            case 'z':
                procFilterFunction = zombieFilter;
                break;
            case 'h': 
                printUsage();
                break;
            default:
                fprintf(stderr,"Error: Invalid Option Specified\n");
                fprintf(stderr,"Usage: %s [-d <path>] \n", argv[0]);
                exit(1);
                break;
        }
    }

    /* Perform the actual scan dir of the dirPath */
    errno = 0;
    n = scandir (dirPath, &eps, filterFunction, sortFunction);
    
    /* validate directory was opened successfully */
    if (n < 0) {
        perror("scandir: ");
        exit(1);
    }

    /* Counting the necessary size for array of PID directories */
    int listSize = 0; 
    for (int cnt = 0; cnt < n; ++cnt) {
        if (eps[cnt]->d_type == DT_DIR && isdigit(eps[cnt]->d_name[0])) {
            listSize++;
        }
    }

    /* Create directory array with listSize and fill with PID directories */
    const struct dirent ** dirArray = (const struct dirent **) (malloc(sizeof(const struct dirent *) * listSize));
    int index = 0;
    for (int cnt = 0; cnt < n; ++cnt) {
        if (eps[cnt]->d_type == DT_DIR && isdigit(eps[cnt]->d_name[0])) {
            dirArray[index] = eps[cnt];
            index++;
        }
    }

    /* Create Array of Proc entries and fill by using string concatenation for file paths */
    ProcEntry ** myProcs = (ProcEntry **) (malloc(sizeof(ProcEntry *) * listSize));
    for (int i = 0; i < listSize; i++) {
        int pathSize = strlen(dirPath) + strlen(dirArray[i]->d_name) + 7;
        char path[pathSize];
        strcpy(path, dirPath);
        strcat(path, "/");
        strcat(path, dirArray[i]->d_name);
        strcat(path, "/stat");
        path[pathSize-1] = '\0';
        myProcs[i] = CreateProcEntryFromFile(path);
    }
    qsort(myProcs, listSize, sizeof(ProcEntry *), procSortFunction);

    /* Print array of Proc entries under specified filters */
    fprintf(stdout,"%7s %7s %5s %5s %5s %7s %-25s %-20s\n","PID","PPID","STATE","UTIME","STIME","THREADS","CMD","STAT_FILE");
    for (int cnt = 0; cnt < listSize; cnt++) {
        if (procFilterFunction(myProcs[cnt])) {
            PrintProcEntry(myProcs[cnt]);
        }
    }
    
    /* Cleanup memory */
    for (int i = 0; i < n; i++) {
        free(eps[i]);
    }
    free(eps);
    free(dirArray);
    for (int j = 0; j < listSize; j++) {
        DestroyProcEntry(myProcs[j]);
    }    
    free(myProcs);

    return 0;
}

void printUsage(void) {
    printf("Usage: ./myps [-d <path>] [-p] [-c] [-z] [-h]\n");
    printf("        -d <path> Directory containing proc entries (default: /proc)\n");
    printf("        -p        Display proc entries sorted by pid (default)\n");
    printf("        -c        Display proc entries sorted by command lexicographically\n");
    printf("        -z        Display ONLY proc entries in the zombie state\n");
    printf("        -h        Display this help message\n");
}
