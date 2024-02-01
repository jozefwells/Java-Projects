#include "ProcEntry.h"
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>


/* CreateProcEntry: Allocate a ProcEntry struct in the heap using malloc.
 *   All fields will be initialized to zero or NULL based upon data type.
 * 
 * returns - Pointer to ProcEntry allocated on the heap, NULL on error
 */
ProcEntry * CreateProcEntry(void) {

    ProcEntry * newProcEntry = (ProcEntry *) malloc(sizeof(ProcEntry));

    if (newProcEntry == NULL) {
        return NULL;
    }

    newProcEntry->comm = NULL;
    newProcEntry->number_of_threads = 0;
    newProcEntry->parent_process_id = 0;
    newProcEntry->path = NULL;
    newProcEntry->process_id = 0;
    newProcEntry->state = 0;
    newProcEntry->stime = 0;
    newProcEntry->utime = 0;

    return newProcEntry;
}

/* CreateProcEntryFromFile: Allocate a ProcEntry struct in the heap 
 *    using malloc and initialize the fields with data provided from
 *    the specified statFile. The command field (comm) and path field 
 *    will be allocated on the heap using malloc. 
 * 
 * The statFile parameter must reference a stat file, formatted as 
 *    described in the /proc manpage entry. The statFile will be
 *    opened and the required fields extract to properly initialize
 *    the ProcEntry struct.
 *
 * If the statFile parameter is NULL or if the file does not exist, 
 *    return NULL. 
 * 
 * If the statFile exists, but an error occurs while attempting to 
 *    extract the field data because is not a properly formatted
 *    stat file, return NULL.
 *  
 * returns - Pointer to ProcEntry allocated on the heap, NULL on error
 */
ProcEntry * CreateProcEntryFromFile(const char statFile[]) {
    int process_id; // 1
    char comm[255]; // 2
    char state; //  3
    int parent_process_id;  // 4
    unsigned long int utime;  // 14
    unsigned long int stime;  // 15
    long number_of_threads; // 20
    
    /* Validate Parameters */ 
    if (statFile == NULL) {
        return NULL;
    }

    FILE * dataFile = fopen(statFile, "r");
    if (dataFile == NULL) {
        return NULL;
    }

    ProcEntry * newProcEntry = CreateProcEntry();
    if (newProcEntry == NULL) {
        return NULL;
    }

    newProcEntry->comm = (char*) malloc(255);
    newProcEntry->path = (char*) malloc(strlen(statFile) + 1);

                                /*   1  2  3  4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20 */                                                             
    int numRead = fscanf(dataFile, "%d %s %c %d %*s %*s %*s %*s %*s %*s %*s %*s %*s %lu %lu %*s %*s %*s %*s %ld", &process_id, comm, &state, &parent_process_id, &utime, &stime, &number_of_threads);
    if (numRead != 7) {
        DestroyProcEntry(newProcEntry);
        fclose(dataFile);
        return NULL;
    }
    newProcEntry->process_id = process_id;
    strncpy(newProcEntry->comm, comm, 255);
    newProcEntry->state = state;
    newProcEntry->parent_process_id = parent_process_id;
    newProcEntry->utime = utime;
    newProcEntry->stime = stime;
    newProcEntry->number_of_threads = number_of_threads;
    strncpy(newProcEntry->path, statFile, strlen(statFile) + 1);

    fclose(dataFile);
    return newProcEntry;
}

/* DestroyProcEntry: Release memory allocated by malloc in the
 *    CreateProcEntry or CreateProcEntryFromFile functions.  
 *    Does nothing if thisNode is NULL.
 * 
 * entry - Pointer to ProcEntry object to be freed.
 */
void DestroyProcEntry(ProcEntry * entry) {

    if(entry == NULL) {
        return;
    }

    free(entry->comm);
    free(entry->path);
    free(entry);
}

/* PrintProcEntry: Write the fields of the specified ProcEntry
 *    to stdout (console) using the exact format specified
 *    in the project guide.
 * 
 * entry - Pointer to ProcEntry object to be displayed
 */
void PrintProcEntry(ProcEntry *entry)
{
     unsigned long int utime = entry->utime / sysconf(_SC_CLK_TCK);
     unsigned long int stime = entry->stime / sysconf(_SC_CLK_TCK);
     fprintf(stdout, "%7d %7d %5c %5lu %5lu %7ld %-25s %-20s\n",
             entry->process_id,
             entry->parent_process_id,
             entry->state,
             utime,
             stime,
             entry->number_of_threads,
             entry->comm,
             entry->path);
}