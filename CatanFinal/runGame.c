#include <stdio.h>
#include <stdlib.h>
#include "Game.h"

#define NUM_UNIS 3
#define NUM_DISCIPLINES 6

#define MAX_KPI_POINTS 150
#define STARTING_CAMPUSES 2
#define STARTING_ARC_GRANTS 2
#define POINTS_PER_ARC_GRANT 2
#define POINTS_PER_CAMPUS 10
#define STARTING_POINTS ((STARTING_CAMPUSES*POINTS_PER_CAMPUS)+(STARTING_ARC_GRANTS*POINTS_PER_ARC_GRANT))

#define TRUE 1
#define FALSE 0

#define NOT_PASS -1

int main (int argc, char *argv[]) {

    //testSampleGame();
    //printf("All tests passed!! Starting the game!!\n");

    Game g;
    action playerMove;
    int diceScore = -1;

    while ((getKPIpoints(g,UNI_A) < MAX_KPI_POINTS)&&
           (getKPIpoints(g,UNI_B) < MAX_KPI_POINTS)&&
           (getKPIpoints(g,UNI_C) < MAX_KPI_POINTS)) {

       throwDice(g,diceScore);  // roll the dice to start the turn

       playerMove.actionCode = NOT_PASS; // as to enter the next loop

       while (playerMove.actionCode  != PASS) {

          printf ("*************************************\n");
          printf ("*************************************\n");
          printf ("** Enter 0 to end your turn        **\n");
          printf ("** Enter 1 to build a campus       **\n");
          printf ("** Enter 2 to upgrade your campus  **\n");
          printf ("** Enter 3 to obtain an ARC_grant  **\n");
          printf ("** Enter 4 to start a spinoff      **\n");
          printf ("** Enter 7 to retrain your students**\n");
          printf ("*************************************\n");
          printf ("*************************************\n");

          printf("Waiting for player %d to enter an action\n",getWhoseTurn(g));
          scanf("%d",&playerMove.actionCode);

          if ((playerMove.actionCode == BUILD_CAMPUS) || (playerMove.actionCode == BUILD_GO8) || (playerMove.actionCode == OBTAIN_ARC)) {
             printf("Enter a path destination\n");
             scanf("%c",playerMove.destination);
          }

          if (playerMove.actionCode == RETRAIN_STUDENTS) {

             printf("Enter a discipline to transfer students from\n");
             scanf("%d",&playerMove.disciplineFrom);

             printf("Enter a discipline to transfer students to\n");
             scanf("%d",&playerMove.disciplineTo);

          }

          // check legality

          if (isLegalAction (g, playerMove) == TRUE) {
             makeAction(g, playerMove);
             printf ("Player %d has made action %d\n",getWhoseTurn(g),playerMove.actionCode);
          }
          else {
             printf("Player %d has attempted to make an illegal move. Move not run.\n",getWhoseTurn(g));
          }
       }

       // note: turn and whoseturn now modified in throwdice in game.c as
       // is correct
    }


   if (getKPIpoints(g,UNI_A) >= MAX_KPI_POINTS) {
      printf ("Congratulations! Player A wins!\n");
   }
   else if (getKPIpoints(g,UNI_B) >= MAX_KPI_POINTS) {
      printf ("Congratulations! Player B wins!\n");
   }
   else {
      printf ("Congratulations! Player C wins!\n");
   }

   disposeGame(g);

   return EXIT_SUCCESS;

}
