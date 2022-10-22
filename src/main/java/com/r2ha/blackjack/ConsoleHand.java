package com.r2ha.blackjack;

public class ConsoleHand {
    // Translate DOMAIN Object (Hand) --> Some String for display purposes
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }
}
