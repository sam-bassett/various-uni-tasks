#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

#define NOTHING -99
#define BLANK -1

//Directions
#define UP 1
#define DOWN 2 
#define LEFT 3
#define RIGHT 4

//Switches
#define MANHATTAN 0
#define OUT_OF_PLACE 1

typedef struct _board *Board;


typedef int *State;

Board createBoard   (int size);
Board populateBoard (Board b, State s);
State createState   (int *rawData, int len);

void  makeMove (State s, int direction,int size);
int   isLegal  (State s, int direction,int size);

int getPosition (State s, int value, int size);

//Cost from current board to goal state
int findCost      (State start, State goal, int heuristic, int size);
int findManhattan (State start, State goal, int size);
int findOutOfPlace(State start, State goal, int size);

State boardToState(Board b);
//Getters:
int getSize (Board b);
int getValue(Board b, int row, int col);


int isGoal(State current, State goal, int size);

void showBoard (Board b);
void freeBoard (Board b);
