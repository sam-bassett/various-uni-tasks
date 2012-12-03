//OUT OF DATE NOW, FUNCTION DEFS CHANGED SIGNIFICANTLY
#include "priqADT.h"

void testCreate();
void testAdd();
void testDelMin();
void testEmpty();
void testSearch();
void testGet();
void testReplace();
void testDelete();
void testMakeNode();
void testGetState();
void testGetDepth();
void testGetParent();

int main (void) {
    testCreate();
    testMakeNode();
    testAdd();
    testDelMin();
    testEmpty();
    testSearch();
    printf("All tests passed! YOU ARE AWESOME!!!\n");
    return EXIT_SUCCESS;
}

void testCreate(void) {
    PriQ q = createPriQ(6);
    if (q == NULL || getSizePQ(q) != 6 || getNumItm(q) != 0) {
        fprintf(stderr, "Error in creation!\n");
        exit(1);
    }
    freePriQ(q);
}

void testAdd(void) {
    PriQ q = createPriQ(3);
    Node n = NULL;
    State s;
    int arr[4] = {1,2,3,BLANK};
    int *test = arr;
    s = createState(test, 4);
    n = createNode (s, 2, 2, NULL);
    addNode(q,n);
    n = createNode (s, 1, 4, NULL);
    addNode(q,n);
    Node m = getNode (q, 2);
    if (getState(m) != s || getCost(m) != 4 || getDepth(m) != 1
            || getParent(m) != NULL) {
        fprintf(stderr, "Problem in first add!\n");
        exit(1);
    }
    m = getNode (q, 1);
    if (getState(m) != s || getCost(m) != 2 || getDepth(m) != 2
            || getParent(m) != NULL) {
        fprintf(stderr, "Problem in second add!\n");
        exit(1);
    }
    freePriQ(q);
}

void testMakeNode (void) {
    Node n = NULL;
    State s;
    int arr[9] = {1,2,3,4,BLANK,5,6,7,8};
    int *test = arr;
    s = createState(test, 9);
    n = createNode (s, 2, 2, NULL);
    if (getState(n) != s || getCost(n) != 2 || getDepth(n) != 2
            || getParent(n) != NULL) {
        fprintf(stderr, "Problem in make!\n");
        printf("cost=%d, depth=%d", getCost(n), getDepth(n));
        exit(1);
    }

    n = createNode (s, 1, 1, NULL);
    if (getState(n) != s || getCost(n) != 1 || getDepth(n) != 1
            || getParent(n) != NULL) {
        fprintf(stderr, "Problem in second make!\n");
        printf("cost=%d, depth=%d", getCost(n), getDepth(n));
        exit(1);
    }
}

void testDelMin (void) {
    PriQ q = createPriQ(2);
    Node n = NULL;
    State s;
    int arr[9] = {1,2,3,4,BLANK,5,6,7,8};
    int *test = arr;
    s = createState(test, 9);
    n = createNode (s, 2, 4, NULL);
    addNode(q,n);
    n = createNode (s, 1, 2, NULL);
    addNode(q,n);
    Node m;
    m = delMin(q);
    if (getCost(m) != getCost(n) || getDepth(m) != getDepth(n)) {
        fprintf(stderr, "Error with delMin\n");
        exit(1);
    }
    freePriQ(q);
}

void testEmpty(void) {
    PriQ q = createPriQ(2);
    if (!isEmpty(q)) {
        fprintf(stderr, "Error in isEmpty\n");
        exit(1);
    }
    State s;
    int arr[9] = {1,2,3,4,BLANK,5,6,7,8};
    int *test = arr;
    s = createState(test, 9);

    Node n = NULL;
    n = createNode(s, 1, 6, NULL);
    addNode(q, n);
    n = NULL;
    if (isEmpty(q)) {
        fprintf(stderr, "Thinks it's empty when it's not!\n");
        exit(1);
    }
    n = delMin(q);
    if (!isEmpty(q)) {
        fprintf(stderr, "Won't delete min\n");
        printf("n->depth=%d,n->cost=%d\n",getDepth(n),getCost(n));
        exit(1);
    }
    freePriQ(q);
}

void testSearch (void) {
    PriQ q = createPriQ(3);
    Node n = NULL;
    State s;
    int arr[9] = {1,2,3,4,BLANK,5,6,7,8};
    int *test = arr;
    s = createState(test, 9);
    n = createNode (s, 1, 2, NULL);
    addNode(q,n);
    n = createNode (s, 2, 4, NULL);
    addNode(q,n);
    int index = searchPriQ(q, n);
    if (index != 2) {
        fprintf(stderr, "Error in first search!\n");
        exit(1);
    }
    n = createNode (s, 2, 1, NULL);
    addNode (q, n);
    index = searchPriQ(q, n);
    if (index != 1) {
        fprintf(stderr, "Error in second search!\n");
        exit(1);
    }
    freePriQ(q);
}
