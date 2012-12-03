#include "boardADT.h"

struct _board {
    int size;
    int **board;
};

/*
int main (int argc, char *argv[]) {
    int costSwitch = NOTHING;
    Game g = NULL;

    if (argc != 2) {
        fprintf(stderr, "Usage error: %s [-m|-p]", argv[0]);
    } else {
        if (argv[1][0] != '-') {
            fprintf(stderr, "Usage error: %s [-m|-p]", argv[0]);
        } else {
            if (argv[1][1] == 'm') {
                costSwitch = MANHATTAN;
            } else if (argv[1][1] == 'p') {
                costSwitch = OUT_OF_PLACE;
            } else {
                fprintf(stderr, "Usage error: %s [-m|-p]", argv[0]);
            }
        }
        g = inputGame(costSwitch);
        printGame(g);
    }

    return EXIT_SUCCESS;
}
*/
Board createBoard(int size) {
    Board b;
    //The size of the struct will be size^2 + an int
    //for size param.
    //b = malloc(size*size*sizeof(int) + sizeof(int));
    //b = malloc(sizeof(struct _board));
    b = malloc(sizeof(struct _board)+sizeof(b->board[size][size]));
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

Board makeMove (Board b, int direction) {
    int position;
    int row;
    int column;
    position = findPosition(b, BLANK);
    row = position/b->size;
    column = position%b->size;
    if (isLegal(b,direction)) {
        if (direction == UP) {
            //Swap positions with position - size
            int temp;
            temp = b->board[row][column];
            b->board[row][column] = b->board[row - 1][column];
            b->board[row-1][column] = temp;
        } else if (direction == DOWN) {
            int temp;
            temp = b->board[row][column];
            b->board[row][column] = b->board[row + 1][column];
            b->board[row+1][column] = temp;
        } else if (direction == LEFT) {
            int temp;
            temp = b->board[row][column];
            b->board[row][column] = b->board[row][column - 1];
            b->board[row][column - 1] = temp;
        } else if (direction == RIGHT) {
            int temp;
            temp = b->board[row][column];
            b->board[row][column] = b->board[row][column + 1];
            b->board[row][column + 1] = temp;
        }
    }
    return b;
}

int isLegal (Board b, int direction) {
    int ret = TRUE;
    //What are the illegal cases? Moving outside arrays, obviously.
    int position, row, column;
    position = findPosition(b, BLANK);
    row = position/b->size;
    column = position%b->size;
    if (direction == UP) {
        if (row == 0) {
            ret = FALSE;
        }
    } else if (direction == DOWN) {
        if (row == (b->size - 1)) {
            ret = FALSE;
        } 
    } else if (direction == LEFT) {
        if (column == 0) {
            ret = FALSE;
        }
    } else if (direction == RIGHT) {
        if (column == (b->size - 1)) {
            ret = FALSE;
        }
    }
    if (ret == FALSE) {
        fprintf(stderr, "Error: Illegal move!\n");
    }
    return ret;
}

int findPosition (Board b, int value) {
    int temp = NOTHING;
    int i, j, k;
    i = 0;
    j = 0;
    k = 0;
    int found = FALSE;
    
    while (i < b->size && !found) {
        while (j < b->size && !found) {
            temp = b->board[i][j];
            if (temp == value) { 
                found = TRUE;
            } else {
                k++;
                j++;
            }
        }
        if (found != TRUE) {
            j = 0;
            i++;
        }
    }
    return k;
}

int findCost (Board b, State goal, int heuristic) {
    int cost = NOTHING;
    if (heuristic == MANHATTAN) {
        cost = findManhattan(b,goal);
    } else if (heuristic == OUT_OF_PLACE) {
        cost = findOutOfPlace(b,goal);
    }
    return cost;
}

int findManhattan (Board b, State goal) {
    //Not as easy!
    //Same as just difference in row + diff columns.
    //So, first get position of a point, then compare with
    //the goal position. ret = sum of cost.
    //cost(one point) = diff(row) + diff(col)
    Board g = createBoard(b->size*b->size);
    g = populateBoard(g, goal);
    
    int rowBoard, colBoard;
    int rowGoal, colGoal;
    int size = b->size;
    //HOLY MOTHER OF DERP
    int cost = 0;
    int i;

    for (i = 1; i < (b->size*b->size); i++) {
        int posBoard = findPosition(b, i);
        rowBoard = posBoard/size;
        colBoard = posBoard%size;
        int posGoal = findPosition(g,i);
        rowGoal = posGoal/size;
        colGoal = posGoal%size;
        cost += abs(rowGoal-rowBoard) + abs(colGoal-colBoard);
    }

    return cost;
}

int findOutOfPlace (Board b, State goal) {
    //Easy! Just scan both strings using for, if not in place
    //increment a counter, return counter.
    int len = b->size*b->size;
    State current;
    current = boardToState(b);
    int ret = 0;
    int i = 0;
    while (i < len) {
        if (current[i] != goal[i]) {
            if (goal[i] != BLANK) {
                ret++;
            }
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

int isGoal (Board b, State goal) {
    int ret = TRUE;
    State s;
    s = boardToState(b);
    int i;
    for (i = 0; i < b->size*b->size; i++) {
        if (s[i] != goal[i]) {
            ret = FALSE;
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
