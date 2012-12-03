//Implement a priority queue, will use a heap.
//The PriQ will have in it nodes, consisting of the actual
//state of the board, the f(x), the depth (or level) and a pointer
//to its parent state.
#include "boardADT.h"

#define TRUE 1
#define FALSE 0
#define NOTHING -99

typedef struct _pqRep *PriQ;
typedef struct _node  *Node;

PriQ createPriQ (int size);
Node delMin     (PriQ q);
int  isEmpty    (PriQ q);
void printPriQ  (PriQ q);
void freePriQ   (PriQ q);
int  searchPriQ (PriQ q, Node n);
//searches PriQ for a given State
int  searchPQS  (PriQ q, State s, int size);

void addNode    (PriQ q, Node n);
Node getNode    (PriQ q, int pos);
void replaceNode(PriQ q, int pos, Node n);
Node deleteNode (PriQ q, int pos);
Node createNode (State s, int depth, int cost, Node parent);
void printNode  (Node n,int size);
void freeNode   (Node n);
void printState (Node n, int len);

void showState  (State s, int size);

void giveCost   (Node n, int cost);
void giveParent (Node n, Node parent);

State getState  (Node n);
int   getDepth  (Node n);
int   getSizePQ (PriQ q);
Node  getParent (Node n);
int   getNumItm (PriQ q);
int   getCost   (Node n);
//returns 1 if n1 > n2, 0 otherwise
int   cmpCosts  (Node n1, Node n2);
