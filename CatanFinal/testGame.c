#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
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

#define CYAN STUDENT_BQN
#define PURP STUDENT_MMONEY
#define YELL STUDENT_MJ
#define RED STUDENT_BPS
#define GREE STUDENT_MTV
#define BLUE STUDENT_THD

#define DEFAULT_DISCIPLINES {CYAN,PURP,YELL,PURP,YELL,RED ,GREE,GREE, RED,GREE,CYAN,YELL,CYAN,BLUE,YELL,PURP,GREE,CYAN,RED }
#define DEFAULT_DICE {9,10,8,12,6,5,3,11,3,11,4,6,4,9,9,2,8,10,5}


typedef struct _landmarks {
   int xCoordinate;
   int yCoordinate;
   int ARCs;
   int Campuses;
   int GO8s;
} Landmarks;

typedef struct _location {
   int xCoordinate;
   int yCoordinate;
   int orientation;
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
} game;


void testStartingConditions(Game g);
void testStartingStudentCount(Game g);
void testStartingOwnership(Game g);
void testStartingLocation(Game g);
void testSampleGame(void);
void testPrestige(void); // all credit to Rowan for this

//int main (int argc, char *argv[]) {
//   testSampleGame();
//   testPrestige();
//   return EXIT_SUCCESS;
//}

void testStartingConditions(Game g) {

   // This subprogram tests to see whether the
   // starting conditions of the game are correct.

   printf("TESTING STARTING CONDITIONS....");

   assert(getTurnNumber(g)==-1);
   // check that the starting turn is -1; no dice have been rolled yet
   assert(getWhoseTurn(g)==NO_ONE);
   // check that it is no one's turn - no one has rolled the dice yet

   testStartingStudentCount(g);
   testStartingOwnership(g);
   testStartingLocation(g);

   printf("TEST FOR STARTING CONDITIONS PASSED!");
}

void testStartingStudentCount(Game g) {

   // Test that all players have no students to begin with
   // in any of their disciplines. Kept seperate to
   // testStartingOwnership() as getStudents() takes an extra
   // argument, discipline.


   int playerCounter = UNI_A;
   int disciplineCounter = STUDENT_THD;

   while (playerCounter < (NUM_UNIS+1)) {
      while (disciplineCounter < NUM_DISCIPLINES) {
         assert(getStudents(g, playerCounter, disciplineCounter) == 0);
         disciplineCounter++;
      }
      disciplineCounter = STUDENT_THD;
      playerCounter++;
   }
}

void testStartingOwnership(Game g) {

   // Tests that the player starts with the correct number of;
   // KPI points, ARC grants, go8 campuses, normal campuses,
   // IP patents, and publications.


   int playerCounter = UNI_A;

   while (playerCounter < (NUM_UNIS+1)) {

      assert(getKPIpoints(g, playerCounter) == STARTING_POINTS);
      assert(getARCs(g, playerCounter) == STARTING_ARC_GRANTS);
      assert(getGO8s(g, playerCounter) == 0);
      assert(getCampuses(g, playerCounter) == STARTING_CAMPUSES);
      assert(getIPs(g, playerCounter) == 0);
      assert(getPublications(g, playerCounter) == 0);

      playerCounter++;

   }

}

void testStartingLocation(Game g) {

   // Tests whether the player's starting campuses and arc grant
   // are positioned correctly

   // will contain asserts for getCampus() and getArc()

   // need to be confirmed since I'm not sure what is stored in the Game struct.

   // Check that the campuses are placed in the correct starting locations

   assert(getCampus(g," ")==CAMPUS_A);
   assert(getCampus(g,"RLRLRLRLRLL")==CAMPUS_A);
   assert(getCampus(g,"LLLLL")==CAMPUS_B);
   assert(getCampus(g,"RLRLRLRRLR")==CAMPUS_B);
   assert(getCampus(g,"RRRRR")==CAMPUS_C);
   assert(getCampus(g,"LLLLLLRLRL")==CAMPUS_C);

   // Check that the arc garnts are placed in the correct starting locations
   // should be the same as the campuses

   assert(getARC(g," ")==ARC_A);
   assert(getARC(g,"RLRLRLRLRLL")==ARC_A);
   assert(getARC(g,"LLLLL")==ARC_B);
   assert(getARC(g,"RLRLRLRRLR")==ARC_B);
   assert(getARC(g,"RRRRR")==ARC_C);
   assert(getARC(g,"LLLLLLRLRL")==ARC_C);

}

void testSampleGame(void) {

    testPrestige();

   int dice[] = DEFAULT_DICE;
   int disciplines[] = DEFAULT_DISCIPLINES;
   int diceScore = -1;
   action a;

   Game g = newGame(disciplines,dice);

   testStartingConditions(g);

   printf ("NOW TESTING A SAMPLE GAME");

   assert (getTurnNumber(g) == -1);
   a.actionCode = PASS;

   throwDice(g,diceScore);
   assert (getTurnNumber(g) == 0);

   // check that the player can pass,
   // and make UNI A pass

   assert(isLegalAction(g,a) == TRUE);
   makeAction(g,a);

   throwDice(g,diceScore);

   // check that the turn has cycled
   assert(getTurnNumber(g)==1);
   assert(getWhoseTurn(g)==UNI_B);

   // give UNI B 3 students, and check that they
   // got them correctly

   g->playerAssets[UNI_B].students[STUDENT_BQN] = 3;
   assert(getStudents(g,UNI_B,STUDENT_BQN) == 3);

   // check that UNI B can retrain 3 students
   // from bqn to get 1 student from bps

   a.disciplineFrom = STUDENT_BQN;
   a.disciplineTo = STUDENT_BPS;
   a.actionCode = RETRAIN_STUDENTS;

   assert(isLegalAction(g,a) == TRUE);
   makeAction(g,a);

   assert(getStudents(g,UNI_B,STUDENT_BQN) == 0);
   assert(getStudents(g,UNI_B,STUDENT_BPS) == 1);

   a.actionCode = PASS;
   makeAction(g,a);

   throwDice(g,diceScore);
   assert (getTurnNumber(g) == 2);

   // give UNI C the students required for a spinoff,
   // and then test that the spinoff works (i.e. it gives
   // a publication or an IP

   g->playerAssets[UNI_C].students[STUDENT_MJ] = 1;
   g->playerAssets[UNI_C].students[STUDENT_MTV] = 1;
   g->playerAssets[UNI_C].students[STUDENT_MMONEY] = 1;

   a.actionCode = START_SPINOFF;

   assert(isLegalAction(g,a) == TRUE);
   makeAction(g,a);

   // the player should now have an IP or publication, and have
   // 10 points for having the most publications or 10 points per
   // IP

   assert(getIPs (g, UNI_C) == 1 || getPublications(g, UNI_C) == 1);
   assert(getKPIpoints(g,UNI_C) == (STARTING_POINTS+10));

   a.actionCode = PASS;
   makeAction(g,a);

   throwDice(g,diceScore);

   // check that it is UNI A's turn once again

   assert(getTurnNumber(g)==3);
   assert(getWhoseTurn(g)==UNI_A);

   printf("SAMPLE GAME TEST PASSED!");
}

/***********************************************/
/***********************************************/
/***********************************************/
/************** ROWAN'S TESTS ******************/
/**********************************************/
/**********************************************/
/**********************************************/
/**********************************************/


void testPrestige(void) {
    printf("Testing that prestige points for ARC grants "
           "are awarded correctly...\n");
    // make everyone get students when a 2 is rolled
    int dice[NUM_REGIONS] = {[0 ... (NUM_REGIONS - 1)] = 2};

    // make A & C's starting positions generate BPS and BQN students
    // so they can obtain arcs... B just gets stuck with
    // THDs
    int disciplines[NUM_REGIONS]
            = {[0 ... (NUM_REGIONS - 1)] = STUDENT_THD,
                [11] = STUDENT_BPS, [16] = STUDENT_BPS,
                [2] = STUDENT_BQN,  [7] = STUDENT_BQN};

    printf("Initialising game with tiles all having dice number "
           "of 2 and unis A & C both near tiles which generate BPS and "
           "BQN students...\n");

    Game game = newGame(disciplines, dice);
    printf("Testing that everyone initially has 24 KPI points (2 campuses + 2 ARCs)... ");
    assert(getKPIpoints (game, UNI_A) == 24);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 24);
    printf("passed!\n");

    printf("rolling the dice initially, as dice needs to "
           "be rolled before uni A's turn (see http://openlearning.cse.unsw.edu.au/Comp1917/2011s1/Project/Posts/FirstTurnConfusionNewgameCurre)... \n");

    // initial roll before it's uni A's turn, thanks to Joel for pointing
    // this out. no one gets any students.
    throwDice(game, 9);

    // UNI_A passes
    printf("Testing that uni A can pass on the first turn... ");
    action a;
    a.actionCode = PASS;
    // this should be legal
    assert(isLegalAction(game, a));
    makeAction(game, a);
    printf("passed!\n");

    // Everyone should still have 24 KPI points
    printf("Testing that everyone still has 24 KPI points... ");
    assert(getKPIpoints (game, UNI_A) == 24);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 24);
    printf("passed!\n");

    // Roll a 2 so now A & C both have 1 BQN and 1 BPS
    printf("Rolling a 2, testing that students have been generated "
           "accordingly for unis A and C... ");
    throwDice(game, 2);
    assert(getStudents(game, UNI_A, STUDENT_BQN) == 1);
    assert(getStudents(game, UNI_A, STUDENT_BPS) == 1);
    assert(getStudents(game, UNI_C, STUDENT_BQN) == 1);
    assert(getStudents(game, UNI_C, STUDENT_BPS) == 1);
    printf("passed!\n");

    printf("Testing that it's uni B's turn... ");
    // UNI_B passes
    assert(getWhoseTurn(game) == UNI_B);
    printf("passed!\n");
    printf("Testing that uni B can perform a 'PASS' action... ");
    a.actionCode = PASS;
    // this should be legal
    assert(isLegalAction(game, a));
    printf("passed!\n");
    makeAction(game, a);

    // Roll a 2 so now A & C both have 2 BQN and 2 BPS
    printf("Rolling a 2, testing that students have been generated "
           "accordingly for unis A and C... ");
    throwDice(game, 2);
    assert(getStudents(game, UNI_A, STUDENT_BQN) == 2);
    assert(getStudents(game, UNI_A, STUDENT_BPS) == 2);
    assert(getStudents(game, UNI_C, STUDENT_BQN) == 2);
    assert(getStudents(game, UNI_C, STUDENT_BPS) == 2);
    printf("passed!\n");

    // Everyone should still have 24 KPI points
    printf("Testing that everyone still has 24 KPI points... ");
    assert(getKPIpoints (game, UNI_A) == 24);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 24);
    printf("passed!\n");

    // C now builds an arc attached to the one
    // they have on their rightmost campus
    printf("Testing that it is now uni C's turn... ");
    assert(getWhoseTurn(game) == UNI_C);
    printf("passed!\n");
    printf("Testing that uni C can build an ARC at destination LRLR... ");
    a.actionCode = OBTAIN_ARC;
    strcpy(a.destination, "LRLR");
    // this should be legal
    assert(isLegalAction(game, a));
    printf("passed!\n");
    makeAction(game, a);

    // they should be deducted students
    printf("Testing that uni C has been deducted students for building "
           "their ARC... ");
    assert(getStudents(game, UNI_C, STUDENT_BQN) == 1);
    assert(getStudents(game, UNI_C, STUDENT_BPS) == 1);
    printf("passed!\n");

    // they should be awarded KPI points
    // 2 for obtaining an arc, 10 for having the most arcs
    printf("Testing that uni C has been awarded 2 points for obtaining "
           "an ARC, and 10 points for having the most ARCs... ");
    assert(getKPIpoints (game, UNI_C) == 36);
    printf("passed!\n");
    printf("Testing that C can now perform a 'PASS' action... ");
    // C passes
    a.actionCode = PASS;
    // this should be legal
    assert(isLegalAction(game, a));
    printf("passed!\n");
    makeAction(game, a);

    // game progresses to next turn. no-one gets any students.
    printf("Rolling a 9, moving to the next turn...\n");
    throwDice(game, 9);

    // Everyone should still have 24 KPI points,
    // except for C who has 36
    printf("Testing that A & B both have 24 KPI points, "
           "and C has 36 = 24 + 2 ARC points + 10 for most ARCs... ");
    assert(getKPIpoints (game, UNI_A) == 24);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 36);
    printf("passed!\n");

    // A now builds an arc to the left of their topmost campus
    printf("Testing that uni A can build an ARC at "
           "destination \"L\"...");
    assert(getWhoseTurn(game) == UNI_A);
    a.actionCode = OBTAIN_ARC;
    strcpy(a.destination, "L");
    // this should be legal
    assert(isLegalAction(game, a));
    printf("passed!\n");
    makeAction(game, a);

    // C and A are now tied for most arcs - the points don't get
    // awarded until A overtakes C
    // A gets two more KPI points for building an arc
    printf("Testing that A has been awarded points for building "
           "an ARC, but C still has the points for the most ARCs "
           " (as C and A are now both tied on 1 ARC each)... ");
    assert(getKPIpoints (game, UNI_A) == 26);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 36);
    printf("passed!\n");

    // A now builds an arc attached to the one they just built
    printf("Testing that A can build an ARC at destination \"LR\"... ");
    assert(getWhoseTurn(game) == UNI_A);
    a.actionCode = OBTAIN_ARC;
    strcpy(a.destination, "LR");
    // this should be legal
    assert(isLegalAction(game, a));
    printf("passed!\n");
    makeAction(game, a);

    // A has overtaken C for the most arcs. So they should get
    // 2 points for getting an arc plus 10 for having the most.
    // C loses 10 points because they no longer have the most.
    printf("Testing that A has now overtaken C and been awarded the "
           "points for having the most ARCs, plus 2 points for "
           "building another ARC... ");
    assert(getKPIpoints (game, UNI_A) == 38);
    assert(getKPIpoints (game, UNI_B) == 24);
    assert(getKPIpoints (game, UNI_C) == 26);
    printf("passed!\n");

    disposeGame(game);
}
