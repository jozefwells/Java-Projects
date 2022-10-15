/* 
 * Author: Luke Hindman
 * Date: Thu 05 Nov 2020 08:10:44 AM PST
 * Description:  Adapted from the Simple Directory Lister Mark II example
 *    provided in the libc manual.
 * https://www.gnu.org/software/libc/manual/html_node/Simple-Directory-Lister-Mark-II.html
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>

#define MAX_PATH_LENGTH 4096

#define UNUSED(x) (void)x

int noSort(const struct dirent ** entryA, const struct dirent ** entryB) {
    UNUSED(entryA);
    UNUSED(entryB);
    return 0;
}

static int defaultFilter(const struct dirent *current) {
    UNUSED(current);
    if (current->d_name[0] != '.') {
        return 1;
    } else {
        return 0;
    }    
}

static int showAll(const struct dirent *current) {
    UNUSED(current);
    return 1;
}

static int showFilesOnly(const struct dirent *current) {
    UNUSED(current);
    if (current->d_type == DT_REG) {
        return 1;
    } else {
        return 0;
    }
}

void printUsage(void);

int main (int argc, char * argv[]) {
    struct dirent **eps;
    int n;
    int opt;

    /* Declare filterFunction */
    int (*filterFunction)(const struct dirent *);
    filterFunction = defaultFilter;

    /* Declare sortFunction pointer */
    int(*sortFunction)(const struct dirent **, const struct dirent **);
    sortFunction = noSort;

    /* Declare dirPath and set default to current directory */
    char dirPath[MAX_PATH_LENGTH];
    strcpy(dirPath, "./");

    bool reverse; //bool for reverse sort

    /*Use getopt() to process command line arguments */
    while ( (opt = getopt(argc, argv, "hfd:sra")) != -1) {
        switch(opt) {
            case 'd':
                strncpy(dirPath, optarg, MAX_PATH_LENGTH);
                break;
            case 's':
                sortFunction = alphasort;
                reverse = false;
                break;
            case 'a': 
                for (int i = 0; i < argc; i++) {
                    if (strcmp(argv[i], "-f") == 0) {
                        fprintf(stderr, "Error: Behavior is undefined\n");
                        exit(1);
                    }
                }
                filterFunction = showAll;
                break;
            case 'f': 
                for (int i = 0; i < argc; i++) {
                    if (strcmp(argv[i], "-a") == 0) {
                        fprintf(stderr, "Error: Behavior is undefined\n");
                        exit(1);
                    }
                }
                filterFunction = showFilesOnly;
                break;
            case 'r':
                sortFunction = alphasort;
                reverse = true;
                break;
            case 'h':
                printUsage();
                exit(1);
                break;
            default:
                fprintf(stderr, "Error: Invalid Option Specified\n");
                fprintf(stderr, "Usage: %s [-d <path>] \n", argv[0]);
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

    /* display the names on stdout stream */
    if (reverse == true) {
        for (int i = n-1; i >= 0; i--) {
            fprintf(stdout,"%s\n", eps[i]->d_name);
        }
    } else {
        for (int cnt = 0; cnt < n; ++cnt) {
            fprintf(stdout,"%s\n", eps[cnt]->d_name);
        }
        
    }     
    
    /* Cleanup memory */
    for (int i = 0; i < n; i++) {
        free(eps[i]);
    }
    free (eps);

    return 0;
}

void printUsage(void) {
    printf("Usage: ./myls [-d <path>] [-s] [-a] [-f] [-r]\n");
    printf("-d <path> Directory to list the contents of\n");
    printf("-a        Display all files, including hidden files\n");
    printf("-f        Display only regular files\n");
    printf("-r        Display entries alphabetically in descending order\n");
    printf("-s        Display entries alphabeitcally in ascending ordder\n");
}
