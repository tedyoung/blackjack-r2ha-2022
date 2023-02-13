package com.r2ha.blackjack.adapter.in.console;

import com.r2ha.blackjack.domain.Hand;

import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

// Translating or Transforming or Mapping DOMAIN -> STRING
public class ConsoleHand {

    // Translate DOMAIN OBJECT (Hand) --> Some String for display purposes
    public static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }

    public static String cardsAsString(Hand hand) {
        return hand.cards()
                   .map(ConsoleCard::display)
                   .collect(Collectors.joining(
                           ansi().cursorUp(6).cursorRight(1).toString()));
    }
}
