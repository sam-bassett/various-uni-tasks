#include "linkedADT.h"

typedef Node *Solution;

typedef Node *Children;

int  checkInput(int*rawDataStart,int*rawDataGoal,int bflag,int lenS,int lenG);
int  isSolvable (State start, State goal, int len);
void printStart (State start, State goal, int len, int heuristic);
//Children should be either states or nodes.
//Probably nodes, so you can update heuristic and depth
//easily in this step. So make Children an array of four (max)
//subsequent states (i.e. a pointer to the first element)
Children generateChildren(Node parent,State goal,int boardSize,int heuristic);
int  getDisorder (State start, int size, int len);
//This should return a pointer to the start of an array.
//The size of this array is given by the depth of the
//final node. This fn will fill in this array from the
//end to the start.
Solution findPathToStart (Node done);
