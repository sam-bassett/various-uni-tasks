#include "puzzleADT.h"

int checkInput(int*rawDataStart,int*rawDataGoal,int bflag,int lenS,int lenG) {
    int ret = TRUE;
    if (!bflag) {
        fprintf(stderr, "Error: No blank found!\n");
        ret = FALSE;
    } else {
        if (lenS != lenG) {
            fprintf(stderr, "Error: unequal numbers of tiles\n");
            ret = FALSE;
        }
        //This array indexes each tile, in the 8-puzzle that is the
        //numbers 1 through 8. It sets them to zero, then checks the 
        //raw data and sets each location in the array indexed by the
        //number being examined to 1. If they're not all 1 by the end,
        //the numbers haven't been input correctly.
        int visited[lenS];
        int i;
        for (i = 0; i < lenS; i++) {
            visited[i] = 0;
        }
        for (i = 0; i < lenS; i++) {
            if (rawDataStart[i] != BLANK && rawDataStart[i] < lenS) {
                visited[rawDataStart[i]] = 1;
            } else if (rawDataStart[i] > lenS) {
                fprintf(stderr, "Please only enter numbers in the range ");
                fprintf(stderr, "of the puzzle\n");
                ret = FALSE;
            }
            if (rawDataStart[i] == BLANK) {
                visited[0] = 1;
            }
        }
        for (i = 0; i < lenS; i++) {
            if (rawDataGoal[i] != BLANK && rawDataGoal[i] < lenS) {
                visited[rawDataGoal[i]] = 1;
            } else if (rawDataGoal[i] > lenS) {
                fprintf(stderr, "Please only enter numbers in the range ");
                fprintf(stderr, "of the puzzle\n");
                ret = FALSE;
            }
            if (rawDataGoal[i] == BLANK) {
                visited[0] = 1;
            }
        }
        for (i = 0; i < lenS; i++) {
            if (visited[i] != TRUE) {
                fprintf(stderr, "Wrong sequence of tiles entered!\n");
                ret = FALSE;
            }
        }
    }
    return ret;
}

int isSolvable(State start, State goal, int len) {
    int size = 0;
    int solvable = TRUE;
    //hard-coding this for now, no time...
    if (len == 4) {
        size = 2;
    } else if (len == 9) {
        size = 3;
    } else if (len == 16) {
        size = 4;
    } else if (len == 25) { //Unlikely...
        size = 5;
    }
    int dStart = getDisorder(start, size, len);
    int dGoal  = getDisorder(goal , size, len);
    if (dStart%2 != dGoal%2) {
        solvable = FALSE;
    }
    return solvable;
}

int getDisorder (State start, int size, int len) {
    int i, j;
    int disorder = 0;
    int blankRow = 0;
    for (i = 0; i < len; i++) {
        for (j = i; j < len; j++) {
            if (start[j] != BLANK && start[i] != BLANK && start[j] < start[i]) {
                disorder++;
            }
            if (start[j] == BLANK) {
                blankRow = j/size + 1;
            }
        }
    }
    if (size % 2 == 0) {
        disorder += blankRow;
    }
    return disorder;
}

void printStart(State start, State goal, int len, int heuristic) {
    printf("start:");
    int i = 0;
    for (; i < len; i++) {
        if (start[i] == BLANK) {
            printf(" b");
        } else {
            printf(" %d", start[i]);
        }
    }
    printf("\n");
    printf("goal:");
    for (i = 0; i < len; i++) {
        if (goal[i] == BLANK) {
            printf(" b");
        } else {
            printf(" %d", goal[i]);
        }
    }
    printf("\n");
    if (heuristic == OUT_OF_PLACE) {
        printf("out of place\n");
    } else if (heuristic == MANHATTAN) {
        printf("manhattan\n");
    }
}

Children generateChildren(Node parent,State goal,int boardSize,int heuristic) {
    Children c;
    c = malloc(100);
    if (c == NULL) {
        fprintf(stderr, "Out of memory!\n");
        exit(1);
    }

    int i = 0;
    while (i < 4) {
        if (i == 0) {
            State current = malloc(sizeof(State));
            current = createState(getState(parent),boardSize*boardSize);
            if (isLegal(current,UP,boardSize)) {
                makeMove(current,UP,boardSize); 
                c[i] = createNode(current,getDepth(parent)+1,
                    NOTHING,parent);
            } else {
                c[i] = NULL;
            }
        } else if (i == 1) {
            State current = malloc(sizeof(State));
            current = createState(getState(parent),boardSize*boardSize);
            if (isLegal(current,DOWN,boardSize)) {
                makeMove(current,DOWN,boardSize); 
                c[i] = createNode(current,getDepth(parent)+1,
                    NOTHING,parent);
            } else {
                c[i] = NULL;
            }
        } else if (i == 2) {
            State current = malloc(sizeof(State));
            current = createState(getState(parent),boardSize*boardSize);
            if (isLegal(current,LEFT,boardSize)) {
                makeMove(current,LEFT,boardSize); 
                c[i] = createNode(current,getDepth(parent)+1,
                    NOTHING,parent);
            } else {
                c[i] = NULL;
            }
        } else if (i == 3) {
            State current = malloc(sizeof(State));
            current = createState(getState(parent),boardSize*boardSize);
            if (isLegal(current,RIGHT,boardSize)) {
                makeMove(current,RIGHT,boardSize); 
                c[i] = createNode(current,getDepth(parent)+1,
                    NOTHING,parent);
            } else {
                c[i] = NULL;
            }
        }
        i++;
    }
    return c;
}

Solution findPathToStart (Node done) {
    Node parent = getParent(done);
    Solution s = malloc(sizeof(Node)*getDepth(done));
    if (s == NULL) {
        fprintf(stderr, "out of memory\n");
        exit(1);
    }
    s[0] = NULL;
    s[getDepth(done)] = done;
    while (parent != NULL) {
        parent = getParent(done);
        s[getDepth(done)] = done;
        done = parent;
        freeNode(parent);
    }
    return s;
}
