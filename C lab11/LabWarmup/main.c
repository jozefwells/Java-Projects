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

#define MAX_PATH_LENGTH 4096

#define UNUSED(x) (void)x

int noSort(const struct dirent ** entryA, const struct dirent ** entryB) {
    UNUSED(entryA);
    UNUSED(entryB);
    return 0;
}

static int defaultFilter(const struct dirent *current) {
    UNUSED(current);
    return 1;
}


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

    /*Use getopt() to process command line arguments */
    while ( (opt = getopt(argc, argv, "d:s")) != -1) {
        switch(opt) {
            case 'd':
                strncpy(dirPath, optarg, MAX_PATH_LENGTH);
                break;
            case 's':
                sortFunction = alphasort;
                break;
            default:
                fprintf(stderr, "Error: Invalid Option Specified\n");
                fprintf(stderr, "Usage: %s [-d <path>] \n", argv[0]);
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
    int cnt;
    for (cnt = 0; cnt < n; ++cnt) {
        fprintf(stdout,"%s\n", eps[cnt]->d_name);
    }

    /* Cleanup memory */
    for (int i = 0; i < n; i++) {
        free(eps[i]);
    }
    free (eps);

    return 0;
}
