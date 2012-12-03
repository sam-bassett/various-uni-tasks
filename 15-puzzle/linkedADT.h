#include "priqADT.h"

typedef struct _lnode *Link;

void freeList (Link head);
Link makeList (void);
void addLNode (Link head, Node value);
void deleteLNode(Link head, Node val);
//Make this return a pointer to the node, NULL if not found.
int  searchNode(Link head, Node val, int size);
Node getLNode (Link head, Node val, int size);
int  searchState(Link head, State s, int size);
