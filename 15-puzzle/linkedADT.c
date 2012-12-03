#include "linkedADT.h"

struct _lnode {
    Node value;
    Link next;
    Link prev;
};


Link makeList (void) {
    //I'm going to do something strange here.
    //As the head node doesn't need a ptr to prev,
    //I'll make it a pointer to last - making the 
    //search time very quick.
    Link head = malloc(sizeof(struct _lnode));
    if (head == NULL) {
        fprintf(stderr, "Out of memory!\n");
        exit(1);
    }
    head->value = createNode(NULL, NOTHING, NOTHING, NULL);
    head->next = NULL;
    head->prev = NULL;
    return head;
}

void addLNode (Link head, Node value) {
    Link temp = head;
    if (head->prev == NULL) {
        Link new = malloc(sizeof(struct _lnode));
        new->value = value;
        new->next = NULL;
        new->prev = temp;
        temp->next = new;
        head->prev = new;
    } else {
        temp = head->prev;
        Link new = malloc(sizeof(struct _lnode));
        new->value = value;
        new->next = NULL;
        new->prev = temp;
        temp->next = new;
        head->prev = new;
    }
    /*
    while (temp->next != NULL) {
        temp = temp->next;
    }
    Link new = malloc(sizeof(struct _lnode));
    new->value = value;
    new->next = NULL;
    new->prev = temp;
    temp->next = new;
    */
}

void deleteLNode (Link head, Node val) {
    Link temp = head;
    while (temp->next != NULL && temp->value != val) {
        temp = temp->next;
    }
    Link previous = temp->prev;
    previous->next = temp->next;
    if (temp->next != NULL) {
        temp->next->prev = previous;
    }
    free (temp);
    temp = NULL;
    previous = NULL;
}

int searchNode (Link head, Node val,int size) {
    int ret = FALSE;
    Link temp = head;
    while (temp->next != NULL && temp->value != val) {
        temp = temp->next;
        if (isGoal(getState(temp->value),getState(val),size)) {
            ret = TRUE;
        }
    }
    return ret;
}
/*
Node getLNode(Link head, Node val, int size) {
    Link temp = head;
    int finished = FALSE;
    if (head->next != NULL) {
        temp = temp->next;
    } else {
        //BAD STYLE
        return NULL;
    }
    State goal;
    goal = getState(val);
    State current = getState(temp->value);
    while (temp->next != NULL && !finished) {
        if (isGoal(current,goal,size)) {
            finished = TRUE;
        } else {
            temp = temp->next;
            current = getState(temp->value);
        }
    }

    return temp->value;
}
*/

Node getLNode (Link head, Node val, int size) {
    Link temp = head->next;
    State goal = getState(val);
    State current = NULL;
    if (temp != NULL) {
        current = getState(temp->value);
    }
    
    int finished = FALSE;
    while (temp != NULL && !finished) {
        if (isGoal(current,goal,size)) {
            return temp->value;
        } else {
            temp = temp->next;
            if (temp != NULL) {
                current = getState(temp->value);
            }
        }
    }
    return NULL;
}

int searchState (Link head, State s, int size) {
    int ret = FALSE;
    Link temp = head;
    State state = NULL;
    while (temp->next != NULL && !isGoal(state,s,size)) {
        state = getState(temp->value);
        if (isGoal(state,s, size)) {
            ret = TRUE;
        }
        temp = temp->next;
    }
    return ret;
}

void freeList(Link head) {
    Link next;
    while (head->next != NULL) {
        next = head;
        head = head->next;
        freeNode(next->value);
        free (next);
    }
}
