#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
void cls();
void arrayshow(int show[14][30]);
int check1(int x, int y, int bomb[14][30], int show[14][30]);
void check2(int x, int y, int bomb[14][30], int show[14][30]);
void arraybomb(int bomb[14][30]);
int main()
{
    int bomb[14][30], show[14][30], flag[14][30], test[14][30];
    for (int i = 0; i < 14; i++)
    {
        for (int j = 0; j < 30; j++)
        {
            bomb[i][j] = flag[i][j] = test[i][j] = 0;
            show[i][j] = 35;
        }
    }
    srand(time(NULL));
    int k = 1;
    while (k != 40)
    {
        int i = rand() % 14;
        int j = rand() % 30;
        if (bomb[i][j] != 1)
        {
            bomb[i][j] = test[i][j] = 1;
            k = k + 1;
        }
    }
    char cmd[4];
    arrayshow(show);
    while (1)
    {
        int end = 0;
        for (int i = 0; i < 14; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                if (test[i][j] != flag[i][j])
                {
                    end = 1;
                }
            }
        }
        if (end == 0)
        {
            break;
        }
        fgets(cmd, sizeof(cmd), stdin);
        if (strcmp(cmd, "fla") == 0)
        {
            int x, y;
            scanf("%d %d", &x, &y);
            cls();
            show[x][y] = 80;
            flag[x][y] = 1;
            arrayshow(show);
        }
        if (strcmp(cmd, "ufl") == 0)
        {
            int x, y;
            scanf("%d %d", &x, &y);
            cls();
            show[x][y] = 35;
            flag[x][y] = 0;
            arrayshow(show);
        }
        if (strcmp(cmd, "cho") == 0)
        {
            int x, y;
            scanf("%d %d", &x, &y);
            cls();
            if (bomb[x][y] == 1)
            {
                for (int i = 0; i < 14; i++)
                {
                    for (int j = 0; j < 30; j++)
                    {
                        if (bomb[i][j] == 1)
                        {
                            if (flag[i][j] == 0)
                            {
                                show[i][j] = 42;
                            }
                        }
                    }
                }
                arrayshow(show);
                break;
            }
            else
            {
                check2(x, y, bomb, show);
                arrayshow(show);
            }
        }
    }
    return 0;
}
void check2(int x, int y, int bomb[14][30], int show[14][30])
{
    if (x > -1 || x < 14 || y > -1 || y < 30)
    {
        int ans;
        ans = check1(x, y, bomb, show);
        if (ans != 0)
        {
            show[x][y] = ans;
        }
        else
        {
            bomb[x][y] = 7;
            show[x][y] = 32;
            if (bomb[x][y + 1] != 7)
            {
                if (y < 29)
                {
                    check2(x, y + 1, bomb, show);
                }
            }
            if (bomb[x][y - 1] != 7)
            {
                if (y > 0)
                {
                    check2(x, y - 1, bomb, show);
                }
            }
            if (bomb[x + 1][y] != 7)
            {
                if (x < 13)
                {
                    check2(x + 1, y, bomb, show);
                }
            }
            if (bomb[x + 1][y - 1] != 7)
            {
                if (x < 13 && y > 0)
                {
                    check2(x + 1, y - 1, bomb, show);
                }
            }
            if (bomb[x + 1][y + 1] != 7)
            {
                if (x < 13 && y < 29)
                {
                    check2(x + 1, y + 1, bomb, show);
                }
            }
            if (bomb[x - 1][y] != 7)
            {
                if (x > 0)
                {
                    check2(x - 1, y, bomb, show);
                }
            }
            if (bomb[x - 1][y + 1] != 7)
            {
                if (x > 0 && y < 29)
                {
                    check2(x - 1, y + 1, bomb, show);
                }
            }
            if (bomb[x - 1][y - 1] != 7)
            {
                if (x > 0 && y > 0)
                {
                    check2(x - 1, y - 1, bomb, show);
                }
            }
        }
    }
}
int check1(int x, int y, int bomb[14][30], int show[14][30])
{
    int ans;
    ans = 0;
    if (bomb[x][y + 1] == 1)
    {
        if (y < 29)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x][y - 1] == 1)
    {
        if (y > 0)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x + 1][y + 1] == 1)
    {
        if (x < 13 && y < 29)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x + 1][y - 1] == 1)
    {
        if (x < 13 && y > 0)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x + 1][y] == 1)
    {
        if (x < 13)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x - 1][y + 1] == 1)
    {
        if (x > 0 && y < 29)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x - 1][y - 1] == 1)
    {
        if (x > 0 && y > 0)
        {
            ans = ans + 1;
        }
    }
    if (bomb[x - 1][y] == 1)
    {
        if (x > 0)
        {
            ans = ans + 1;
        }
    }
    return ans;
}
void arrayshow(int show[14][30])
{
    printf("   ");
    for (int k = 0; k < 30; k++)
    {
        if (k < 10)
        {
            printf("%d  ", k);
        }
        else
        {
            printf("%d ", k);
        }
    }
    printf("\n");
    int k = 0;
    for (int i = 0; i < 14; i++)
    {
        if (k < 10)
        {
            printf("%d  ", k);
        }
        else
        {
            printf("%d ", k);
        }
        k = k + 1;
        for (int j = 0; j < 30; j++)
        {
            if (show[i][j] < 9)
            {
                printf("%d  ", show[i][j]);
            }
            else
            {
                putchar(show[i][j]);
                printf("  ");
            }
        }
        printf("\n");
    }
}
void cls()
{
    system("cls");
}