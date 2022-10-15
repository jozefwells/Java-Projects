#include<stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include "Student.h"

int main(int argc, char * argv[]) {
   
   if (argc != 2) {
      fprintf(stderr, "Usage: %s <catalog.csv>\n", argv[0]);
      exit(1);
   }

   char * userFileName = argv[1];
   int listSize = 8;

   errno = 0;
   FILE * dataFile = fopen(userFileName, "r");
   if (dataFile == NULL) {
      fprintf(stderr, "fopen: No such file or directory\n");
      perror("fopen");
      exit(1);
   }

   Student ** studentList = (Student **) malloc(sizeof(Student *) * listSize);
   if (studentList == NULL) {
      fprintf(stderr, "Error, Unable to allocate memory for studentList\n");
      exit(1);
   }

   const int MAX_FIELD_SIZE = 80;
   char lastName[MAX_FIELD_SIZE];
   char firstName[MAX_FIELD_SIZE];
   int id;
   int score;

   int studentCount = 0;
   int numRead = 0;

   while (!feof(dataFile)) {
      numRead = fscanf(dataFile,"%79[^,],%79[^,],%d,%d\n", lastName, firstName, &id, &score);
      if (numRead == 4) {
         studentList[studentCount++] = CreateStudent(lastName, firstName, id, score);
         if (studentCount >= listSize) { //list is full 
            printf("Growing Array: %d -> ", listSize);
            listSize = listSize * 2;
            printf("%d\n", listSize);
            /* Expand Capacity */
            studentList = (Student **) realloc(studentList, sizeof(Student *) * listSize); 
            if (studentList == NULL) {
               fprintf(stderr, "Error, Unable to allocate memory for studentList\n");
               exit(1);
            }
         }
      } else {
         fprintf(stderr,"Error:  Only read %d of 4\n", numRead);
      }
   }
   fclose(dataFile);
   printf("Successfully loaded %d Students!\n", studentCount);
   qsort(studentList, studentCount, sizeof(Student *), CompareStudents);

   for (int i = 0; i < studentCount; i++) {
      PrintStudent(studentList[i]);
      DestroyStudent(studentList[i]);
   }
   free(studentList);

   return 0;
}
