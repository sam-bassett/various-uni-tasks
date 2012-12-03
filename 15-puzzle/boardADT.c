#include "boardADT.h"

struct _board {
    int size;
    int **board;
};

Board createBoard(int size) {
    Board b;
    //The size of the struct will be size^2 + an int
    //for size param.
    //b = malloc(size*size*sizeof(int) + sizeof(int));
    b = malloc(sizeof(struct _board));
    //b = malloc(sizeof(struct _board)+sizeof(b->board[size][size]));
    b->board = malloc (sizeof(int)*size*size);
    int k;
    for (k = 0; k < size; k++) {
        b->board[k] = malloc(sizeof(int)*size);
    }
    b->size = size;
    if (b == NULL) {
        fprintf(stderr, "Out of memory! Exiting...\n");
        exit(1);
    }
    int i,j;
    for (i = 0; i < size; i++) {
        for (j = 0; j < size; j++) {
            b->board[i][j] = NOTHING;
        }
    }
    return b;
}

Board populateBoard (Board b, State s) {
    //This assumes the size of the board is the same as the number
    //of things in s. Change this (later).
    int i,j,k; //i scans rows, j columns, k scans state.
    k = 0;
    for(i = 0; i < b->size; i++) {
        for (j = 0; j < b->size; j++) {
            b->board[i][j] = s[k++];
        }
    }
    return b;
}

State createState (int *rawData, int len) {
    State s;
    s = malloc (len*sizeof(int));
    int i;
    for (i = 0; i < len; i++) {
        s[i] = rawData[i];
    }
    return s;
}

State boardToState (Board b) {
    State s;
    s = malloc(b->size * b->size * sizeof(int));
    if (s == NULL) {
        fprintf(stderr, "Out of memory! Aborting\n");
        exit(1);
    }
    int i,j,k;
    k = 0;
    for (i = 0; i < b->size; i++) {
        for (j = 0; j < b->size; j++) {
            s[k++] = b->board[i][j];
        }
    }
    return s;
}

void makeMove (State s, int direction, int size) {
    int position = getPosition(s,BLANK,size);
    if (isLegal(s,direction,size)) {
        if (direction == UP) {
            int swap = s[position];
            s[position] = s[position-size];
            s[position-size] = swap;
        } else if (direction == DOWN) {
            int swap = s[position];
            s[position] = s[position+size];
            s[position+size] = swap;
        } else if (direction == LEFT) {
            int swap = s[position];
            s[position] = s[position-1];
            s[position-1] = swap;
        } else if (direction == RIGHT) {
            int swap = s[position];
            s[position] = s[position+1];
            s[position+1] = swap;
        }
    } else {
        fprintf(stderr, "Illegal direction!\n");
        exit(1);
    }
}

int isLegal (State s, int direction, int size) {
    int ret = TRUE;
    int position = getPosition(s,BLANK,size);
    if (direction == UP) {
        if (position/size == 0) {
            ret = FALSE;
        }
    } else if (direction == DOWN) {
        if (position/size == size-1) {
            ret = FALSE;
        }
    } else if (direction == LEFT) {
        if (position%size == 0) {
            ret = FALSE;
        }
    } else if (direction == RIGHT) {
        if (position%size == size-1) {
            ret = FALSE;
        }
    } else {
        ret = FALSE;
        fprintf(stderr, "Invalid move code!\n");
    }
    return ret;
}

int getPosition (State s, int value, int size) {
    int i = -1;
    int found = FALSE;
    while (!found && i < size*size) {
        i++;
        if (s[i] == value) {
            found = TRUE;
        }
    }
    return i;
}

int findCost (State start, State goal, int heuristic, int size) {
    int cost = NOTHING;
    if (heuristic == MANHATTAN) {
        cost = findManhattan(start,goal, size);
    } else if (heuristic == OUT_OF_PLACE) {
        cost = findOutOfPlace(start,goal,size);
    }
    return cost;
}

int findManhattan (State start, State goal, int size) {
    //Not as easy!
    //Same as just difference in row + diff columns.
    //So, first get position of a point, then compare with
    //the goal position. ret = sum of cost.
    //cost(one point) = diff(row) + diff(col)

    //If you can, (please) find a way of doing this using
    //just a state...
    //This has been staring you in the face this whole time.
    //What is findPosition? It returns the offset of a number
    //from the start of the board. Replace this with the index of
    //an array, derp

    int rowBoard, colBoard;
    int rowGoal, colGoal;
    int cost = 0;
    int i;
    int j,k;
    j = 0;
    k = 0;

    //all that needs to be done here is to find (x,y) of one,
    //(x,y) of goal and compare.
    for (i = 1; i < size*size; i++) {
        while (start[j] != i && j < size*size) {
            j++;
        }
        while (goal[k] != i && k < size*size) {
            k++;
        }
        rowBoard = j/size;
        colBoard = j%size;
        rowGoal  = k/size;
        colGoal  = k%size;
        cost += abs(rowGoal-rowBoard) + abs(colGoal-colBoard);
        j = 0;
        k = 0;
    }

    return cost;
}

int findOutOfPlace (State start, State goal, int size) {
    //Easy! Just scan both strings using for, if not in place
    //increment a counter, return counter.
    int ret = 0;
    int i = 0;
    while (i < size*size) {
        if (start[i] != goal[i] && start[i] != BLANK
                && goal[i] != BLANK) {
            ret++;
        }
        i++;
    }
    return ret;
}

void showBoard (Board b) {
    int i,j;
    i = 0;
    j = 0;
    while (i < b->size) {
        while (j < b->size) {
            printf("[%d] ", b->board[i][j]);
            j++;
        }
        j = 0;
        i++;
        printf("\n");
    }
    printf("\n");
}

int isGoal (State current, State goal, int size) {
    int ret = TRUE;
    if (current == NULL || goal == NULL) {
        ret = FALSE;
    } else {
        int i;
        for (i = 0; i < size*size && ret == TRUE; i++) {
            if (current[i] != goal[i]) {
                ret = FALSE;
                break;
            }
        }
    }
    return ret;
}

int getSize (Board b) {
    return b->size;
}

int getValue (Board b, int row, int col) {
    return b->board[row][col];
}

void freeBoard (Board b) {
    int i;
    //I get glibc errors if this isn't 1...
    //I don't know, and I haven't the time.
    for (i = 1; i < b->size; i++) {
        free(b->board[i]);
    }
    free(b->board);
}
