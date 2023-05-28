#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>

struct timeval tv1, tv2 , tv3;

int main(int argc, char *argv[])
{
    gettimeofday(&tv1, NULL);

    FILE *inputFile = fopen("./input.txt", "r");

    int numberOfFirstChilds, numberOfSecondChilds;
    fscanf(inputFile, "%d %d", &numberOfFirstChilds, &numberOfSecondChilds);

    int sleeps[numberOfFirstChilds * numberOfSecondChilds];
    for (int i = 0; i < numberOfFirstChilds * numberOfSecondChilds; i++)
    {
        fscanf(inputFile, "%d", &sleeps[i]);
    }

    int parentProcessId = getpid();

    int firstChildIds[numberOfFirstChilds];
    int secondChildIds[numberOfSecondChilds];


    int fd[numberOfFirstChilds][numberOfSecondChilds][2];
    for (int i = 0; i < numberOfFirstChilds; i++)
    {
        for (int j = 0; j < numberOfSecondChilds; j++)
        {
            pipe(fd[i][j]);
            int temp = sleeps[(i * numberOfSecondChilds) + j];
            write(fd[i][j][1], &temp, sizeof(int));
        }
    }
    for (int i = 0; i < numberOfFirstChilds; i++)
    {
        firstChildIds[i] = fork();
        if (firstChildIds[i] == 0)
        {
            for (int j = 0; j < numberOfSecondChilds; j++)
            {
                secondChildIds[j] = fork();
                if (secondChildIds[j] == 0)
                {
                    int sleepTime;
                    read(fd[i][j][0], &sleepTime, sizeof(int));
                    sleep(sleepTime);
                    int number = (i * numberOfSecondChilds) + j;
                    write(fd[i][j][1], &number, sizeof(int));
                    exit(1);
                    break;
                }
                else if (secondChildIds[j] != 0)
                {
                    int current_process_id = getpid();
                    if (j == numberOfSecondChilds - 1)
                    {
                        for(int k = 0 ; k < numberOfSecondChilds ; k++){
                            wait(NULL);
                        }
                        exit(1);
                    }
                }
            }
            break;
        }
        else if (firstChildIds[i] != 0)
        {
            wait(NULL);
        }
    }

    wait(NULL);

    FILE * outputFile = fopen("./output.txt" , "w");

    gettimeofday(&tv2, NULL);

    fprintf(outputFile , "%f", (double)(tv2.tv_sec - tv1.tv_sec) + ((double)(tv2.tv_usec - tv1.tv_usec) / 1000000));
    return 0;
}

