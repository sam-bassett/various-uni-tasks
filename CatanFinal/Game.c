#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <assert.h>
#include "Game.h"

#define NUM_UNIS 3
#define NUM_DISCIPLINES 6
#define NUM_X 10
#define NUM_Y 10
#define NUM_ORNTN 6

#define MAX_KPI_POINTS 150
#define STARTING_CAMPUSES 2
#define STARTING_ARC_GRANTS 2
#define POINTS_PER_ARC_GRANT 2
#define POINTS_PER_CAMPUS 10
#define STARTING_POINTS ((STARTING_CAMPUSES*POINTS_PER_CAMPUS)+(STARTING_ARC_GRANTS*POINTS_PER_ARC_GRANT))

#define TRUE 1
#define FALSE 0

// added all the typedef's from game.h here because
// apparently we aren't meant to change game.h

//Landmarks should be implemented differently.
//X and Y need to be interdependant - 2d array.
//Still use coords though.

//Vertexes will store a code at a specific x and y reference, denoted
//by the two-d array.

//Probably move ARCS, Camps and GO8s into Assets for convinience.

typedef struct _landmarks {
   int xCoordinate;
   int yCoordinate;
   int ARCs;
   int camps;
   int GO8s;
   int vertices[NUM_X][NUM_Y];
} Landmarks;

typedef struct _location {
   int xCoordinate;
   int yCoordinate;
   int rightOrientation;
   int leftOrientation;
   int paths[NUM_X][NUM_Y][NUM_ORNTN];
} Location;

typedef struct _assets {
   int students[NUM_DISCIPLINES];
   int kpiPoints;
   Location playerLocation;
   Landmarks playerLandmarks;
   int IPs;
   int publications;
} Assets;

typedef struct _game {
   int turnNumber;
   int whoseTurn;
   Assets playerAssets[NUM_UNIS];
   int dice[NUM_REGIONS];
   int disciplines[NUM_REGIONS];
   int diceScore;
} game;



static Game convertPathToCoordinates(Game g,path move);

//Not finished! Have to implement paths etc. still and
//figure out how arc grants and things will work

//int main(int argc, char* argv[]) {
//    return EXIT_SUCCESS;
//}

Game newGame (int discipline[], int dice[]) {

    Game g = malloc(sizeof(struct _game));

    g->turnNumber = -1;
    g->whoseTurn = NO_ONE;
    int a = 0;
    int b = 0;
    while (a < NUM_UNIS) {
        while (b < NUM_DISCIPLINES) {
            g->playerAssets[a].students[b] = 0;
        }
        g->playerAssets[a].IPs = 0;
        g->playerAssets[a].kpiPoints = 24;
        g->playerAssets[a].publications = 0;
        a++;
    }



    int x = 0;
    int y = 0;

    while (x < NUM_X) {
        while (y < NUM_Y) {
            g->playerAssets[getWhoseTurn(g)].playerLandmarks.vertices[x][y] = VACANT_VERTEX;
            y++;
        }
        x++;
    }
    x = 0;
    y = 0;
    int z = 0;

    while (x < NUM_X){
        while (y < NUM_Y) {
            while (z < NUM_ORNTN){
                g->playerAssets[getWhoseTurn(g)].playerLocation.paths[x][y][z] = VACANT_ARC;
                z++;
            }
            y++;
        }
        x++;
    }

    while (a < NUM_UNIS) {
        g->playerAssets[a].playerLandmarks.vertices[2][7] = CAMPUS_A;
        g->playerAssets[a].playerLandmarks.vertices[0][2] = CAMPUS_B;
        g->playerAssets[a].playerLandmarks.vertices[3][0] = CAMPUS_C;
        g->playerAssets[a].playerLandmarks.vertices[8][3] = CAMPUS_A;
        g->playerAssets[a].playerLandmarks.vertices[10][8] = CAMPUS_B;
        g->playerAssets[a].playerLandmarks.vertices[7][10] = CAMPUS_C;

        g->playerAssets[a].playerLocation.paths[2][7][1] = ARC_A;
        g->playerAssets[a].playerLocation.paths[0][2][0] = ARC_B;
        g->playerAssets[a].playerLocation.paths[3][0][5] = ARC_C;
        g->playerAssets[a].playerLocation.paths[8][3][4] = ARC_A;
        g->playerAssets[a].playerLocation.paths[10][8][3] = ARC_B;
        g->playerAssets[a].playerLocation.paths[7][10][2] = ARC_C;
    }

    //Implement dice and regions next.

    return g;
}
void disposeGame (Game g) {
    assert(g!=NULL);
    free(g);
}

int getTurnNumber (Game g) {
    return g->turnNumber;
}

int getWhoseTurn (Game g) {
    return g->whoseTurn;
}

int getCampus(Game g, path pathToVertex) {

    g = convertPathToCoordinates(g,pathToVertex);

    int x = g->playerAssets[getWhoseTurn(g)].playerLandmarks.xCoordinate;
    int y = g->playerAssets[getWhoseTurn(g)].playerLandmarks.yCoordinate;


    return g->playerAssets[getWhoseTurn(g)].playerLandmarks.vertices[x][y];

}
int getARC(Game g, path pathToEdge) {

    //NOT DONE

    g = convertPathToCoordinates(g,pathToEdge);

    //int x = g->playerAssets[getWhoseTurn(g)].playerLocation.xCoordinate;
    //int y = g->playerAssets[getWhoseTurn(g)].playerLocation.yCoordinate;
    //int z = g->playerAssets.playerLandmarks.

    return VACANT_ARC;
}
int isLegalAction (Game g, action a) {
    //I will start by writing functions for creating a campus
    // Need bps, b?, mj, mtv for a normal campus.
    //THESE STILL NEED PATHS TO BE IMPLEMENTED, OTHERWISE
    //THEY WON'T WORK AT ALL.
    int buildLegal = FALSE;
    int bps = getStudents(g, getTurnNumber(g), STUDENT_BPS);
    int bq = getStudents(g, getTurnNumber(g), STUDENT_BQN);
    int mj = getStudents(g, getTurnNumber(g), STUDENT_MJ);
    int mtv = getStudents(g, getTurnNumber(g), STUDENT_MTV);
    int mmoney = getStudents(g, getTurnNumber(g), STUDENT_MMONEY);


    if (a.actionCode == BUILD_CAMPUS) {
        buildLegal = FALSE;
        if (bps == 0 || bq == 0 || mj == 0 || mtv == 0) {
            buildLegal = FALSE;
        }
        else buildLegal = TRUE;
    }


    if (a.actionCode == BUILD_GO8) {
        buildLegal=FALSE;
        int totalGO8;
        int i = 1;
        while (i<=3) {
            totalGO8 = totalGO8 + g->playerAssets[i].playerLandmarks.GO8s;
            i++;
        }
        if (totalGO8 >= 8){
            printf("No more GO8s allowed\n");
            buildLegal = FALSE;
        }
        if (mmoney >= 3 && mtv >= 2) {
            buildLegal = TRUE;
        }
        else if (mmoney < 3 || mtv < 2) {
            buildLegal = FALSE;
        }
    }

    if (a.actionCode == START_SPINOFF) {
        buildLegal = FALSE;
        if ((mj||mtv||mmoney) == 0) {
            buildLegal = FALSE;
        }
        else buildLegal = TRUE;
    }

    if (a.actionCode == OBTAIN_ARC) {
        buildLegal = FALSE;
        if (bps >= 1 && bq >= 1) {
            buildLegal = TRUE;
        }
        else buildLegal = FALSE;
    }

    if (a.actionCode == RETRAIN_STUDENTS) {
        //Check that there are 3 or 2 students
        //To retrain. Not 4, people start on retraining centre
        if (a.disciplineFrom == STUDENT_THD) {
            printf("ThDs are useless! You can't retrain them!\n");
            buildLegal = FALSE;
        }

        int retrainFrom = getStudents(g, getTurnNumber(g),
                                     a.disciplineFrom);
        int retrainTo = a.disciplineTo;
        //unfinished, need to properly implement exchange rate
        if (retrainFrom >=getExchangeRate(g,
            g->whoseTurn,a.disciplineFrom,retrainTo)) {
            /*g->Assets.students[retrainTo]++;
            g->Assets.students[action.disciplineFrom] =
                Assets.students[action.disciplineFrom] - 3;
            */
            buildLegal = TRUE;
        }
        else {
            printf("Not enough students!\n");
            buildLegal = FALSE;
        }

    }

    if (a.actionCode == OBTAIN_IP_PATENT
        ||a.actionCode == OBTAIN_PUBLICATION) {
            printf("Nice try, but that's illegal.\n");
            buildLegal = FALSE;
        }
    return buildLegal;
}

void makeAction (Game g, action a) {
    //Build Campus
    //Assume all actions are allowed, as rungame
    //already tests whether the move is legal
    //@#$%^&^%$#@!
    if (a.actionCode == BUILD_CAMPUS) {

        g = convertPathToCoordinates(g,a.destination);


        g->playerAssets[g->whoseTurn].playerLandmarks.camps++;
        //IMPLEMENT DIRECTION
        g->playerAssets[g->whoseTurn].students[STUDENT_BPS]--;
        g->playerAssets[g->whoseTurn].students[STUDENT_BQN]--;
        g->playerAssets[g->whoseTurn].students[STUDENT_MJ]--;
        g->playerAssets[g->whoseTurn].students[STUDENT_MTV]--;
    }
    //Start spinoff
    if (a.actionCode == START_SPINOFF) {
        int randInt = rand() % 3 + 1;
        if (randInt == 1 || randInt == 3) {
            printf("Publication granted!");
            g->playerAssets[g->whoseTurn].publications++;
            g->playerAssets[g->whoseTurn].kpiPoints =
            g->playerAssets[g->whoseTurn].kpiPoints + 10;
            g->playerAssets[g->whoseTurn].students[STUDENT_MJ]--;
            g->playerAssets[g->whoseTurn].students[STUDENT_MMONEY]--;
            g->playerAssets[g->whoseTurn].students[STUDENT_MTV]--;
        }
        else {
            printf("Major IP gained!");
            g->playerAssets[g->whoseTurn].IPs++;
            g->playerAssets[g->whoseTurn].students[STUDENT_MJ]--;
            g->playerAssets[g->whoseTurn].students[STUDENT_MMONEY]--;
            g->playerAssets[g->whoseTurn].students[STUDENT_MTV]--;
        }
    }//END OF SPINOFF

    //Retrain Students
    if (a.actionCode == RETRAIN_STUDENTS) {
        int retrainFrom = a.disciplineFrom;
        int retrainTo = a.disciplineTo;
        g->playerAssets[g->whoseTurn].students[retrainTo]++;
        g->playerAssets[g->whoseTurn].students[retrainFrom] =
        g->playerAssets[g->whoseTurn].students[retrainFrom] -
        getExchangeRate(g,g->whoseTurn,retrainFrom,
                          retrainTo);
    }
    //BUILD GO8
    if (a.actionCode == BUILD_GO8) {
        //Game coords = convertPathToCoordinates(g,a.destination);
        g->playerAssets[g->whoseTurn].playerLandmarks.camps--;
        g->playerAssets[g->whoseTurn].playerLandmarks.GO8s++;
        g->playerAssets[g->whoseTurn].students[STUDENT_MJ]=
        g->playerAssets[g->whoseTurn].students[STUDENT_MJ]-2;
        g->playerAssets[g->whoseTurn].students[STUDENT_MJ]=
        g->playerAssets[g->whoseTurn].students[STUDENT_MMONEY]-3;
        g->playerAssets[g->whoseTurn].playerLandmarks.GO8s++;
        g->playerAssets[g->whoseTurn].kpiPoints =
        g->playerAssets[g->whoseTurn].kpiPoints + 20;
        g->playerAssets[g->whoseTurn].kpiPoints =
        g->playerAssets[g->whoseTurn].kpiPoints - 10;
        //Removes the campus which is being converted
        g->playerAssets[g->whoseTurn].playerLandmarks.camps--;

    }

    //Obtain ARC grant
    /*if (a.actionCode == OBTAIN_ARC){
        g->playerAssets
    }*/


}
void throwDice (Game g, int diceScore) {

     srand (time(NULL));

     int die1 = ((rand()*6) + 1);
     int die2 = ((rand()*6) + 1);

     diceScore = die1 + die2;

     g->turnNumber = (getTurnNumber(g) + 1);
     g->whoseTurn = (getWhoseTurn(g)%3);

     g->diceScore = diceScore;

}

int getKPIpoints (Game g, int player) {
    return g->playerAssets[player].kpiPoints;
}

int getARCs (Game g, int player) {
    return g->playerAssets[player].playerLandmarks.ARCs;
}

int getGO8s (Game g, int player) {
    return g->playerAssets[player].playerLandmarks.GO8s;
}

int getCampuses (Game g, int player) {
    return g->playerAssets[player].playerLandmarks.camps;
}

int getIPs (Game g, int player) {
    return g->playerAssets[player].IPs;
}

int getPublications (Game g, int player) {
    return g->playerAssets[player].publications;
}

int getStudents (Game g, int player, int discipline) {
    return g->playerAssets[player].students[discipline];
}

int getExchangeRate (Game g, int player, int disciplineFrom, int disciplineTo) {
    // not too sure this is right - It should check if a retraining center is nearby
    //return (g->playerAssets[player].students[disciplineFrom] = g.playerAssets[player].students[disciplineTo];
    return 3;
}

//This function only deals with moving, not with paths itself.
//This is actually easier, as it is less complex. The only things which change
//are the x and y coords, which can now be represented in the array.

static Game convertPathToCoordinates(Game g,path move) {

   int rightOrientation = g->playerAssets[getWhoseTurn(g)].playerLocation.rightOrientation;
   int leftOrientation = g->playerAssets[getWhoseTurn(g)].playerLocation.leftOrientation;
   int xCoordinate = g->playerAssets[getWhoseTurn(g)].playerLocation.xCoordinate;
   int yCoordinate = g->playerAssets[getWhoseTurn(g)].playerLocation.yCoordinate;


    rightOrientation = 1;
    leftOrientation = 5;
    xCoordinate = 0;
    yCoordinate = 0;


   int counter = 0;

   while(move[counter] != EOF) {

      if (move[counter] == 'R') {

         if (rightOrientation == 1) {
            yCoordinate--;
         }
         else if (rightOrientation == 2) {
            yCoordinate--;
            xCoordinate--;
         }
         else if (rightOrientation == 3) {
            xCoordinate--;
         }
         else if (rightOrientation == 4) {
            yCoordinate--;
         }
         else if (rightOrientation == 5) {
            yCoordinate++;
            xCoordinate++;
         }
         else if (rightOrientation == 0) {
            xCoordinate++;
         }
      }

      else if (move[counter] == 'L') {

         if (leftOrientation == 1) {
            yCoordinate--;
         }
         else if (leftOrientation == 2) {
            yCoordinate--;
            xCoordinate--;
         }
         else if (leftOrientation == 3) {
            xCoordinate--;
         }
         else if (leftOrientation == 4) {
            yCoordinate--;
         }
         else if (leftOrientation == 5) {
            yCoordinate++;
            xCoordinate++;
         }
         else if (leftOrientation == 0) {
            xCoordinate++;
         }
      }

        else if (move[counter] == 'B') {

            if (rightOrientation == 1) {
                xCoordinate--;
            }

            if (rightOrientation == 2) {
                yCoordinate++;
            }

            if (rightOrientation == 3) {
                xCoordinate++;
                yCoordinate++;
            }

            if (rightOrientation == 4) {
                xCoordinate++;
            }

            if (rightOrientation == 5) {
                yCoordinate--;
            }

            if (rightOrientation == 0) {
                xCoordinate--;
                yCoordinate--;
            }

        }


      if (move[counter] != 'B'){
         leftOrientation = (((leftOrientation - 1)+6)%6);
         rightOrientation = ((rightOrientation + 1)%6);
      }

      else {
        int swap;
        swap = (((leftOrientation - 1)+6)%6);
        leftOrientation = ((rightOrientation + 1)%6);
        rightOrientation = swap;

      }
         counter++;

      }

      g->playerAssets[getWhoseTurn(g)].playerLocation.xCoordinate = xCoordinate;
      g->playerAssets[getWhoseTurn(g)].playerLocation.yCoordinate = yCoordinate;
      g->playerAssets[getWhoseTurn(g)].playerLocation.leftOrientation = leftOrientation;
      g->playerAssets[getWhoseTurn(g)].playerLocation.rightOrientation = rightOrientation;
      g->playerAssets[getWhoseTurn(g)].playerLandmarks.xCoordinate = xCoordinate;
      g->playerAssets[getWhoseTurn(g)].playerLandmarks.yCoordinate = yCoordinate;


      return g;

}
