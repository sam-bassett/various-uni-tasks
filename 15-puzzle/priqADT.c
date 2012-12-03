#include "priqADT.h"

void fixUp  (Node *heap, int child);
void fixDown(Node *heap, int parent, int len);

struct _node {
    State s;
    int cost;
    int depth;
    Node parent;
};

struct _pqRep {
    int numItems;
    Node *heap;
    int arraySize;
};

PriQ createPriQ (int size) {
    PriQ q;

    q = malloc(sizeof(struct _pqRep));
    if (q == NULL) {
        fprintf(stderr, "Out of memory! Exiting...\n");
        exit(1);
    }
    q->heap = malloc(size * sizeof(struct _node));
    if (q->heap == NULL) {
        fprintf(stderr, "Out of memory! Exiting...\n");
        exit(1);
    }
    q->numItems = 0;
    q->arraySize = size;
    Node n = NULL; 
    n = createNode (NULL, NOTHING, NOTHING,NULL);
    q->heap[0] = n;

    return q;
}

Node delMin (PriQ q) {
    Node n;
    n = q->heap[1];
    q->heap[1] = q->heap[q->numItems];
    fixDown(q->heap, 1, q->numItems);
    q->numItems--;
    return n;
}

int isEmpty (PriQ q) {
    return (q->numItems == 0);
}

void freePriQ (PriQ q) {
    int i = 0;
    while (i < q->numItems) {
        freeNode(q->heap[i]);
        i++;
    }
    free(q->heap);
    free(q);
}

int searchPriQ (PriQ q, Node n) {
    //returns index in PQ, uses linear search.
    int success = FALSE;
    int i = 0;
    Node temp = NULL;
    while (i < q->arraySize && temp != n) {
        i++;
        temp = q->heap[i];
        if (temp == n) {
            success = TRUE;
        }
    }
    if (success == FALSE) {
        i = NOTHING;
    }
    return i;
}

void addNode (PriQ q, Node n) {
    if (q == NULL) {
        fprintf(stderr, "PriQ uninitialised! Insert failed.\n");
        exit(1);
    }
    if (q->numItems == q->arraySize) {
        fprintf(stderr, "PriQ full! Insert failed.\n");
        fprintf(stderr, "This may help debug:\n");
        fprintf(stderr, "%d, %d\n", q->numItems, q->arraySize);
        fprintf(stderr, "Node depth: %d\n", getDepth(n));
        exit(1);
    }

    q->numItems++;
    q->heap[q->numItems] = n;
    fixUp(q->heap, q->numItems);
}

Node getNode (PriQ q, int arrayPos) {
    return q->heap[arrayPos];
}

void replaceNode (PriQ q, int arrayPos, Node n) {
    q->heap[arrayPos] = n;
}

Node deleteNode (PriQ q, int arrayPos) {
    Node ret = q->heap[arrayPos];
    free(q->heap[arrayPos]);
    q->numItems--;
    return ret;
}

Node createNode (State s, int depth, int cost, Node parent) {
    Node n = NULL;
    n = malloc(sizeof(struct _node));
    if (n == NULL) {
        fprintf(stderr, "Out of memory! Exiting...\n");
        exit(1);
    }
    n->s = s;
    n->depth = depth;
    n->cost = cost;
    n->parent = parent;
    return n;
}

void printNode (Node n, int size) {
    printf("\nNew State!\n");
    printf("\n");
    printf("State:");
    printState(n, size*size);
    printf("\n");
    printf("Depth: %d\n",n->depth);
    printf("Cost: %d\n", n->cost);
}

State getState (Node n) {
    return n->s;
}

int getDepth (Node n) {
    return n->depth;
}

int getCost (Node n) {
    return n->cost;
}

int getSizePQ (PriQ q) {
    return q->arraySize;
}

int getNumItm (PriQ q) {
    return q->numItems;
}

Node getParent (Node n) {
    return n->parent;
}

void freeNode (Node n) {
    //if this isn't commented out, the system can't
    //find malloc.c
    /*
    if (n!= NULL) {
        if (n->s != NULL) {
            free(ns);
        }
    }
    */
}

int cmpCosts (Node n1, Node n2) {
    return (n1->cost > n2->cost);
}

int searchPQS (PriQ q, State s, int size) {
    //returns index in PQ, uses linear search.
    int success = FALSE;
    int i = 0;
    State temp = q->heap[1]->s;
    while (i < q->arraySize && temp != NULL &&
            !isGoal(temp,s,size)) {
        i++;
        if (q->heap[i] != NULL) {
            temp = q->heap[i]->s;
        
            if (isGoal(temp,s,size)) {
                success = TRUE;
            }
        }
    }
    if (success == FALSE) {
        i = NOTHING;
    }
    return i;
}

void printState (Node n, int len) {
    int i;
    for (i = 0; i < len; i++) {
        if (n->s[i] == BLANK) {
            printf("b ");
        } else {
            printf("%d ", n->s[i]);
        }
    }
    printf("\n");
}

void showState (State s, int size) {
    int i;
    for (i = 0; i < size*size; i++) {
        if (s[i] == BLANK) {
            printf("b ");
        } else {
            printf("%d ", s[i]);
        }
    }
    printf("\n");
}

void giveCost (Node n, int cost) {
    n->cost=cost;
}

void giveParent (Node n, Node parent) {
    n->parent = parent;
}

void fixUp(Node *heap, int child) {
    //Remember, this must have the node with the min cost out
    //the front. 
    while (child > 1 && heap[child]->cost < heap[child/2]->cost) {
        Node temp;
        temp = heap[child];
        heap[child] = heap[child/2];
        heap[child/2] = temp;
        child = child/2;
    }
}

void fixDown (Node *heap, int parent, int len) {
    int finished = FALSE;
    while (2*parent <= len && !finished) {
        int child = 2*parent;
        if (child < len && heap[child]->cost > heap[child+1]->cost) {
            child++;
        }
        if (heap[child]->cost < heap[parent]->cost) {
            Node temp = heap[parent];
            heap[parent] = heap[child];
            heap[child] = temp;
            parent = child;
        } else {
            finished = TRUE;
        }
    }
}
