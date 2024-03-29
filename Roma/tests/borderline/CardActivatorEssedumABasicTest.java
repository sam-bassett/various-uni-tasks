package tests.borderline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import framework.Test;
import framework.Rules;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.*;

/**
 *
 * Test the basic functionality of Essedum
 *
 * @author Damon Stacey
 *
 */

public class CardActivatorEssedumABasicTest extends Test {

    @Override
    public String getShortDescription() {
        return "Test the basic functionality of Essedum";
    }

    @Override
    public void run(GameState gameState, MoveMaker move) throws AssertionError,
            UnsupportedOperationException, IllegalArgumentException {
      
      List<Card> deck = new LinkedList<Card>();
      gameState.setDiscard(deck);

      Card[] discs = new Card[Rules.NUM_DICE_DISCS];
      for (int i = 0; i < Rules.NUM_DICE_DISCS; i++) {
         discs[i] = Card.NOT_A_CARD;
      }
      for (int i = 0; i < Rules.NUM_PLAYERS; i++) {
         gameState.setPlayerCardsOnDiscs(i, discs);
      }
      List<Card> discard = new LinkedList<Card>();
      discard.add(Card.AESCULAPINUM);
      discard.add(Card.BASILICA);
      discard.add(Card.CENTURIO);
      discard.add(Card.CONSILIARIUS);
      discard.add(Card.CONSUL);
      discard.add(Card.ESSEDUM);
      discard.add(Card.FORUM);
      discard.add(Card.GLADIATOR);
      discard.add(Card.HARUSPEX);
      discard.add(Card.LEGAT);
      discard.add(Card.LEGIONARIUS);
      gameState.setDiscard(discard);
      discard = gameState.getDiscard();
      
      gameState.setWhoseTurn(0);
      List<Card> hand;
      for (int i = 0; i < Rules.NUM_PLAYERS; i++) {
         gameState.setPlayerSestertii(i, 100);
         gameState.setPlayerVictoryPoints(i, 15);
         hand = new LinkedList<Card>();
         hand.add(Card.CENTURIO);
         hand.add(Card.CENTURIO);
         hand.add(Card.ESSEDUM);
         hand.add(Card.ARCHITECTUS);
         gameState.setPlayerHand(i, hand);
      }
      
      gameState.setActionDice(new int[] {3,2,3});

      move.placeCard(Card.CENTURIO, Rules.DICE_DISC_1);
      move.placeCard(Card.CENTURIO, Rules.DICE_DISC_2);
      move.placeCard(Card.ESSEDUM, Rules.DICE_DISC_3);

      assert(gameState.getPlayerSestertii(0) == 100 - 9*2 - 6);
      assert(gameState.getPlayerSestertii(1) == 100);
      assert(gameState.getPlayerHand(0).size() == 1);
      assert(gameState.getPlayerHand(0).contains(Card.ARCHITECTUS));
      Card[] field;
      field = gameState.getPlayerCardsOnDiscs(0);
      assert(field[0] == Card.CENTURIO);
      assert(field[1] == Card.CENTURIO);
      assert(field[2] == Card.ESSEDUM);
      
      assert(gameState.getPoolVictoryPoints() == 36 - 15*Rules.NUM_PLAYERS);
      assert(!gameState.isGameCompleted());
      
      move.endTurn();
      move.placeCard(Card.ARCHITECTUS, Rules.DICE_DISC_1);
      move.placeCard(Card.CENTURIO, Rules.DICE_DISC_2);
 
      field = gameState.getPlayerCardsOnDiscs(1);
      assert(field[0] == Card.ARCHITECTUS);
      assert(field[1] == Card.CENTURIO);
 
      //Now essedum should not be in effect so a kill with roll 3 (against defense 4 should not work).
      move.endTurn();
      gameState.setActionDice(new int[] {2,3,2});
      
      CenturioActivator activator = (CenturioActivator) move.chooseCardToActivate(2);
      activator.giveAttackDieRoll(3);
      activator.chooseCenturioAddActionDie(false);
      activator.complete();
      field = gameState.getPlayerCardsOnDiscs(1);
      assert(field[0] == Card.ARCHITECTUS);
      assert(field[1] == Card.CENTURIO);
      assert(field[2] == Card.NOT_A_CARD);
      assert(field[3] == Card.NOT_A_CARD);
      assert(field[4] == Card.NOT_A_CARD);
      assert(field[5] == Card.NOT_A_CARD);
      assert(field[6] == Card.NOT_A_CARD);
      assert(!gameState.isGameCompleted());

      EssedumActivator activator1 = (EssedumActivator) move.chooseCardToActivate(3);
      activator1.complete();
      
      activator = (CenturioActivator) move.chooseCardToActivate(2);
      activator.giveAttackDieRoll(3);
      activator.chooseCenturioAddActionDie(false);
      activator.complete();
      field = gameState.getPlayerCardsOnDiscs(1);
      assert(field[0] == Card.ARCHITECTUS);
      assert(field[1] == Card.NOT_A_CARD);
      assert(field[2] == Card.NOT_A_CARD);
      assert(field[3] == Card.NOT_A_CARD);
      assert(field[4] == Card.NOT_A_CARD);
      assert(field[5] == Card.NOT_A_CARD);
      assert(field[6] == Card.NOT_A_CARD);
      assert(!gameState.isGameCompleted());
   }
}
