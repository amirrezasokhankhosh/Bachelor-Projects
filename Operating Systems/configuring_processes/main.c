#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>

void writeInput(int firsts, int seconds, int sleeps[]);

int main(int argc, char *argv[])
{
    printf("\nCalculating...\n");
    FILE *input_main = fopen("./Input_main.txt", "r");
    int firsts, seconds;
    fscanf(input_main, "%d %d", &firsts, &seconds);
    int sleeps[firsts * seconds];
    for (int i = 0; i < firsts * seconds; i++)
    {
        fscanf(input_main, "%d", &sleeps[i]);
    }
    int configs[4][2];
    configs[0][0] = 1;
    configs[0][1] = firsts * seconds;
    if (firsts < seconds)
    {
        configs[1][0] = firsts;
        configs[1][1] = seconds;
        configs[2][0] = seconds;
        configs[2][1] = firsts;
    }
    else
    {
        configs[1][0] = seconds;
        configs[1][1] = firsts;
        configs[2][0] = firsts;
        configs[2][1] = seconds;
    }
    configs[3][0] = firsts * seconds;
    configs[3][1] = 1;

    double answers[4];

    for (int i = 0; i < 4; i++)
    {
        writeInput(configs[i][0], configs[i][1], sleeps);
        system("./app");
        FILE *output = fopen("./output.txt", "r");
        fscanf(output, "%lf", &answers[i]);
        fclose(output);
    }

    FILE *output_main = fopen("./output_main.txt" , "w");
    for (int i = 0 ; i < 4 ; i++){
        fprintf(output_main , "%d\n%d\n%f\n" , configs[i][0] , configs[i][1] , answers[i]);
    }
    fclose(output_main);

    return 0;
}

void writeInput(int firsts, int seconds, int sleeps[])
{
    FILE *input = fopen("./input.txt", "w");
    fprintf(input, "%d %d\n", firsts, seconds);
    for (int i = 0; i < firsts * seconds; i++)
    {
        fprintf(input, "%d\n", sleeps[i]);
    }
    fclose(input);
}