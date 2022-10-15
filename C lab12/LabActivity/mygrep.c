/* 
 * Author: Luke Hindman
 * Date: Fri 13 Nov 2020 12:21:37 PM PST
 * Description: Starter code for stream processing lab
 */

#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>

#define MAX_FILTER_SIZE 256
#define READ_BUFFER_SIZE 1024
#define MAX_FILEPATH_SIZE 4096

int FilterDataStream(const char * filter, FILE * in, FILE * out);

int main (int argc, char * argv[]) {

    /* Allocate space for filePath on stact and 
     *   initialize filePath to empty string */
    char filePath[MAX_FILEPATH_SIZE];
    filePath[0] = '\0';

    /* Allocate space for searchFilter on stack and 
    *   initialize searchFilter to empty String */
   char searchFilter[MAX_FILTER_SIZE];
    searchFilter[0] = '\0';

    int opt;
    while ((opt = getopt(argc, argv, "hf:s:")) != -1) {
        switch (opt) {
            case 'h': 
                fprintf(stderr, "Usage: %s -s <filter> [-f <file>] [-h]\n", argv[0]);
                exit(0);
                break;
            case 'f':
                strncpy(filePath, optarg, MAX_FILEPATH_SIZE);
                break;
            case 's':
                strncpy(searchFilter, optarg, MAX_FILTER_SIZE);
                break;
            default:
                fprintf(stderr, "Usage: %s -s <filter> [-f <file>] [-h]\n", argv[0]);
                exit(1);
                break;
        }   
    }

    /* Validate that the user specified a search filter */
    if (strcmp(searchFilter, "") == 0) {
        fprintf(stderr, "Usage: %s -s <filter> [-f <file>] [-h]\n", argv[0]);
        exit(1);
    }

    /* Do the stream processing */

    int status = 0;
    if (strcmp(filePath, "") == 0) {
        status = FilterDataStream(searchFilter, stdin, stdout);
    } else {
        FILE * inputStream = fopen(filePath, "r");
        status = FilterDataStream(searchFilter, inputStream, stdout);
        fclose(inputStream);
    }

    if (status != 0) {
        fprintf(stderr, "Error: Grep did not complete successfully :(\n");
        exit(1);
    }

    return 0;
}

int FilterDataStream(const char * filter, FILE * in, FILE * out) {

    char buffer[READ_BUFFER_SIZE];
    int readCount = 0;
    int writeCount = 0;

    while (fgets(buffer, READ_BUFFER_SIZE, in) != NULL) {

        if (strstr(buffer, filter) != NULL) {
            readCount = strlen(buffer);
            writeCount = fwrite(buffer, sizeof(char), readCount, out);

            if (writeCount != readCount) {
                fprintf(stderr, "Error: An error occurred while writing to output stream\n");
                return -1;
            }
        }
        
    }

    return 0;
}