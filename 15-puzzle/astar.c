#include "astar.h"
//Implementation of A* searching algorithm 
//Copyright Sam Bassett, 2011
//z3372468 COMP1927

//A* is a heuristic search. It is passed two states,
//a start and a goal, and requires information such as
//the cost of moves made, as well as the cost of potential moves.

//It also has two sets, a CLOSED and an OPEN. These
//should be implemented using a PriQ.
//CLOSED has nothing in it initially, and nodes are added as they're
//evaluated. The lower f(x) (f(x) = g(x) + h*(x)), the higher
//it's priority in the queue. At each step of the algorithm,
//the node with the best f(x) is removed from the queue, and
//the things it links to are updated. (f(x) and h*(x)). These
//things are added to the queue (and fixUp'd)

//In node: State, cost, depth, parent
//In board: A board (of values), the size of the board
//In LL: A node, a pointer to next and to previous.

int main (int argc, char *argv[]) {
    int costSwitch = NOTHING;

    if (argc != 2) {
        fprintf(stderr, "Usage error: %s [-m|-p]\n", argv[0]);
    } else {
        if (argv[1][0] != '-') {
            fprintf(stderr, "Usage error: %s [-m|-p]\n", argv[0]);
        } else {
            if (argv[1][1] == 'm') {
                costSwitch = MANHATTAN;
                playGame(costSwitch);
            } else if (argv[1][1] == 'p') {
                costSwitch = OUT_OF_PLACE;
                playGame(costSwitch);
            } else {
                fprintf(stderr, "Usage error: %s [-m|-p]\n", argv[0]);
            }
        }
    }

    return EXIT_SUCCESS;
}

void playGame(int costSwitch) {
    //This should do all the input stuff.
    int *rawDataStart;
    rawDataStart = malloc(sizeof(int)*16);
    int *rawDataGoal;
    rawDataGoal = malloc(sizeof(int)*16);

    char *temp = malloc(sizeof(char)*2);
    int i = 0;
    int bflag = FALSE;
    int flag = 1;

    while (flag != EOF) {
        flag = scanf("%s",temp);
        if (temp[0] == 'b') { 
            bflag = TRUE;
            rawDataStart[i] = BLANK;
        } else {
            rawDataStart[i] = atoi(temp);
        }
        i++;
    }
    //This (and j-- ) are hacky. I know.
    i--;
    flag = 1;
    printf("Entering start done!\n");
    int j = 0;
    while (flag != EOF) {
        flag = scanf("%s",temp);
        if (temp[0] == 'b') { 
            bflag = TRUE;
            rawDataGoal[j] = BLANK;
        } else {
            rawDataGoal[j] = atoi(temp);
        }
        j++;
    }
    j--;
    int size = 0;
    if (i == 4) {
        size = 2;
    } else if (i == 9) {
        size = 3;
    } else if (i == 16) {
        size = 4;
    } else if (i == 25) {
        size = 5;
    } else {
        fprintf(stderr, "Please enter a square amount of tiles\n");
        exit(1);
    }
    flag = checkInput(rawDataStart, rawDataGoal,bflag,i,j);
    if (flag == FALSE) {
        fprintf(stderr, "Error: entered data is wrong. Recheck presence of");
        fprintf(stderr, " b, as well as matching number of tiles\n");
        free(rawDataStart);
        free(rawDataGoal);
        free(temp);
        playGame(costSwitch);
    }
    State startState = createState(rawDataStart, i);
    State goalState  = createState(rawDataGoal , i);
    printStart(startState,goalState,i,costSwitch);
    flag = isSolvable(startState,goalState,i);
    Board start = createBoard(size);
    start = populateBoard(start, startState);
    Board goal = createBoard(size);
    goal = populateBoard(goal, goalState);
    if (!flag) {
        printf("no solution\n");
    } else {
        flag = astar(start,goal,costSwitch);
    }
    free(rawDataStart);
    free(rawDataGoal);
    free(temp);
    freeBoard(start);
    freeBoard(goal);
    free(startState);
    free(goalState);
}

//That A* may need a slight rethink - I'm not sure I actually know 
//what it is I'm doing.
//Re-plan it here. 
//So you need two things, right? An open and a closed set.
//The open set stores the nodes
//you are about to evaluate, starting with the start state.
//The closed set is initially empty, but
//will contain the set of nodes already evaluated. 
//Here, you're using a PriQ for open and a 
//linked list for closed, as it needs no sorting (only finding). 
//A* must delMin from open, to get best next possible node.
//It then generates it's children (and calculates f*(x)).
//The node from open must then be placed into closed, as it's already
//been evaluated. The open thing will always only give the best
//heuristic value, so each node must be linked with it's parent - 
//A* will jump between different paths a lot. (side note - each time
//generateChildren is called, increment "expanded"). It will continue
//doing this until a solution is reached. It will then follow the 
//'parent' element in each node back from the goal to the start config,
//giving the path taken.

//Other things to do include handling what happens if a state is evaluated
//more than once - but that can come later, once this elementary
//thing works.

int astar (Board start, Board goal, int heuristic) {
    PriQ open = NULL;
    Link closed = NULL;
    int found = FALSE;
    int size = getSize(start);
    closed = makeList();
    //Hard Coded, because I'll come back to it if I have time
    open = createPriQ(100000000);
    State startState = boardToState(start);
    State goalState  = boardToState(goal);
    int expanded = 0;
    int h = 0;
    h = findCost(startState,goalState,heuristic,size);
    Node solution = NULL;
    //This is important - first node on open, will have depth 1 (as
    //it's the starting node), cost will be the initial heuristic 
    //(as g(x) = 0), pointer to parent will be NULL. Have to remember
    //when backtracing that the node at index 0 of the PriQ has a 
    //pointer to NULL as well, so you should check depth as well.
    Node startNode = createNode(startState,1,h,NULL);
    addNode(open, startNode);
    while (!isEmpty(open) && !found) {
        Node temp = delMin(open);
        if (isGoal(getState(temp),goalState,size)) {
            found = TRUE;
            solution = temp;
        } else {
        //GenerateChildren wants a parent node, a goal state,
        //a board size and the heuristic. It returns a pointer to an
        //array of 4 pointers to nodes, being the children. (Indexed
        //by move - UP, DOWN, LEFT then RIGHT). If move is not possible,
        //the pointer will be NULL.
            Children c; 
            c = generateChildren(temp,goalState,size,heuristic);
            int i = 0;
            while (i < 4) {
                if (c[i] != NULL) {
                    State child = getState(c[i]);
                    Node isInClosed = getLNode(closed,c[i],size);
                    int locationPQ = searchPQS(open,child,size);
                    if (isInClosed == NULL && locationPQ == NOTHING) {
                         giveCost(c[i],findCost(getState(c[i]),
                                     goalState,heuristic,size)); 
                         addNode(open,c[i]);
                         expanded++;
                    } else if (locationPQ != NOTHING) {
                        Node inOpen = getNode(open,locationPQ);
                        if (getCost(inOpen) > getCost(c[i])) {
                            giveParent(inOpen, getParent(c[i]));
                        }
                    } else if (isInClosed != NULL) {
                        if (getCost(isInClosed) > getCost(c[i])) {
                            deleteLNode(closed,isInClosed);
                            addNode(open,isInClosed);
                        }
                    }
                }
                i++;
            }
            addLNode(closed,temp);
        }
        freeNode(temp);
    }
    if (found) {
        Solution s = findPathToStart(solution);
        int i;
        for (i = 1; i <= getDepth(solution); i++) {
            if (s[i] != NULL) {
                printf("%d + %d: ", getDepth(s[i]), getCost(s[i]));
                printState(s[i], size*size);
            }
        }
        printf("expanded:  %d\n", expanded);
    }
    freeList(closed);
    freePriQ(open);
    free(startState);
    free(goalState);
    freeNode(startNode);
    freeBoard(start);
    freeBoard(goal);
    return found;
}


//I don't think this code will work. Don't bother looking at it.

/*
int aStar (Board start, Board goal, int heuristic) {
    PriQ open = NULL;
    //Linked list used for memory purposes.
    Link closed = makeList();
    //NOT GOING TO WORK FOR LONG! REDO THIS LINE PROPERLY LATER!!!!!!!!!
    open = createPriQ(100000);
    State startState = boardToState(start);
    State goalState = boardToState(goal);
    Node startNode = createNode(startState,1,0);
    addNode(open, startNode);
    int found = FALSE;
    int size = getSize(start);
    while (!isEmpty(open) && !found) {
        Node temp = delMin(open);
        State x = getState(temp);
        if (isGoal(x, goalState, size)) {
            found = TRUE;
        } else {
            Children c = generateChildren(temp,goalState,size,heuristic);
            int i;
            for (i = 0; i < 4; i++) {
                if (c[i] != NULL) {
                    //These are states!
                    if (searchPQS(open,getState(c[i]),size) != NOTHING
                            && searchState(closed,getState(c[i]),size)) {
                        addNode(open, c[i]);
                    } else if (searchPQS(open, getState(c[i]),size)!= NOTHING) {
                        //Do things
                    } else if (searchState(closed,getState(c[i]), size)) {
                        if (cmpCosts(getLNode(closed,c[i]),c[i])) {
                            deleteLNode(closed, c[i]);
                            addNode(open, c[i]);
                        }
                    }
                }
            }
            makeNode(closed, temp);
        }
    }
    if (found) {
        //reconstructPath();
    }
    return found;
}
*/
