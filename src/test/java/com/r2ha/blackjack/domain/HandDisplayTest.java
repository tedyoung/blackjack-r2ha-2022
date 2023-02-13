package com.r2ha.blackjack.domain;

import com.r2ha.blackjack.adapter.in.console.ConsoleHand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandDisplayTest {

    @Test
    void displayFaceUpCardReturnsAsCorrectString() throws Exception {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.JACK));
        Hand hand = new Hand(cards);

        Assertions.assertThat(ConsoleHand.displayFaceUpCard(hand))
                  .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘");
    }


    @Test
    public void cardsAsStringTranslatesCardsToString() throws Exception {
        Hand hand = new Hand(List.of(new Card(Suit.CLUBS, Rank.QUEEN),
                                     new Card(Suit.HEARTS, Rank.KING)));

        assertThat(ConsoleHand.cardsAsString(hand))
                .isEqualTo("[30m┌─────────┐[1B[11D│Q        │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│        Q│[1B[11D└─────────┘[6A[1C[31m┌─────────┐[1B[11D│K        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        K│[1B[11D└─────────┘");
    }
}
