//OUT OF DATE NOW, FUNCTION DEFS CHANGED SIGNIFICANTLY
#include "boardADT.h"

void testCreate(void);
void testPopulate(void);
void testCreateState(void);
void testMakeMove(void);
void testFindPosition(void);
void testFindCost(void);
void testIsGoal(void);

int main (int argc, char *argv[]) {
    testCreate();
    testCreateState();
    testPopulate();
    testMakeMove();
    testFindPosition();
    testFindCost();
    testIsGoal();
    printf("All tests passed! You are AWESOME!!!\n");
    return EXIT_SUCCESS;
}

void testCreate(void) {
    Board b = NULL;
    b = createBoard(3);
    if (b == NULL) {
        fprintf(stderr, "Board not initialising properly\n");
        exit(1);
    }
    if (getSize(b) != 3) {
        fprintf(stderr, "Board size problem!\n");
        exit(1);
    }
}

void testCreateState(void) {
    State s = NULL;
    int *rawData = malloc(sizeof(int)*9);
    rawData[0] = BLANK;
    int j;
    for (j = 1; j < 9; j++) {
        rawData[j] = j;
    }
    s = createState(rawData, 9);
    int i;
    if (s[0] != BLANK) {
        fprintf(stderr, "Problem in createState\n");
        fprintf(stderr, "s[0] = %d, it should be %d\n",s[0],rawData[0]);
        exit(1);
    }
    for (i = 1; i < 9; i++) {
        if (s[i] != i) {
            fprintf(stderr, "Problem in createState\n");
            fprintf(stderr, "s[%d] = %d, should be %d\n",i,s[i],rawData[i]);
            exit(1);
        }
    }
}

void testPopulate(void) {
    Board b = NULL;
    b = createBoard(3);
    State s;
    int *rawData = malloc(sizeof(int)*9);
    rawData[0] = 1;
    rawData[1] = BLANK;
    int l;
    for (l = 2; l < 9; l++) {
        rawData[l] = l;
    }
    s = createState(rawData, 9);
    b = populateBoard(b, s);
    if (getValue(b,0,0) != 1 || getValue(b,0,1) != BLANK ||
            getValue(b,0,2) != 2) {
        fprintf(stderr, "Something went wrong in board insertion!\n");
        exit(1);
    }
    int i,j;
    int k = 3;
    for (i = 1; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            if (getValue(b,i,j) != k++) {
                fprintf(stderr, "Somethinw went wrong in board insertion\n");
                exit(1);
            }
        }
    }
}

void testMakeMove(void) {                           //Raw looks like this:
    Board b = NULL;                                 // [1] [b] [2]
    b = createBoard(3);                             // [3] [4] [5]
    State s;                                        // [6] [7] [8]
    int *rawData = malloc(sizeof(int)*9);
    rawData[0] = 1;
    rawData[1] = BLANK;
    int i;
    for (i = 2; i < 9; i++) {
        rawData[i] = i;
    }
    s = createState(rawData, 9);
    b = populateBoard(b, s);

    b = makeMove(b, DOWN);
    if (getValue(b,0,1) != 4 || getValue(b,1,1) != BLANK) {
        fprintf(stderr, "Problem with the making move function:DOWN\n");
        exit(1);
    }

    b = makeMove(b, LEFT);
    if (getValue(b,1,0) != BLANK || getValue(b,1,1) != 3) {
        fprintf(stderr, "Problem with the making move function:LEFT\n");
        exit(1);
    }
    b = makeMove(b, RIGHT);
    if (getValue(b,1,0) != 3 || getValue(b,1,1) != BLANK) {
        fprintf(stderr, "Problem with the making move function:RIGHT\n");
        exit(1);
    }
    b = makeMove(b, UP);
    if (getValue(b,0,1) != BLANK || getValue(b,1,1) != 4) {
        fprintf(stderr, "Problem with the making move function:UP\n");
        exit(1);
    }
}

void testFindPosition(void) {
    Board b = NULL;                                 
    b = createBoard(4);                         
    State s; 
    int *rawData = malloc(sizeof(int)*16);
    rawData[0] = 1;
    rawData[1] = BLANK;
    int i;
    for (i = 2; i < 16; i++) {
        rawData[i] = i;
    }
    s = createState(rawData, 16);
    b = populateBoard(b,s);

    int pos;
    pos = findPosition(b, 4);
    if (pos != 4) {
        fprintf(stderr, "Position finding doesn't work\n");
        exit(1);
    }
}

void testFindCost(void) {
    Board b = NULL;
    b = createBoard(2);
    State s;
    int *rawData = malloc(sizeof(int)*4);
    rawData[0] = BLANK;
    int i;
    for (i = 1; i < 4; i++) {
        rawData[i] = i;
    }                                              // [b] [1]
    s = createState(rawData, 4);                   // [2] [3]
    b = populateBoard(b,s);

    State goal;
    rawData[0] = 1;
    rawData[1] = 3;
    rawData[2] = 2;
    rawData[3] = BLANK;
                                                   // [1] [3]
    goal = createState(rawData, 4);                // [2] [b]
    int cost = findCost(b, goal, OUT_OF_PLACE);
    if (cost != 2) {
        fprintf(stderr, "OOP cost wrong: is %d, should be 2\n", cost);
        exit(1);
    }
    cost = findCost(b,goal,MANHATTAN);
    if (cost != 2) {
        fprintf(stderr, "Manhattan cost wrong: is %d, should be 2\n", cost);
        exit(1);
    }

}

void testIsGoal(void) {
    Board b = NULL;
    b = createBoard(2);
    State s;
    int *rawData = malloc(sizeof(int) * 4);
    rawData[0] = BLANK;
    int i;
    for (i = 1; i < 4; i++) {
        rawData[i] = i;
    }
                                                // [b] [1]
    s = createState(rawData, 4);                   // [2] [3]
    b = populateBoard(b,s);
    if (isGoal(b,s) != TRUE) {
        fprintf(stderr, "Problem in endgame\n");
        exit(1);
    }
}
