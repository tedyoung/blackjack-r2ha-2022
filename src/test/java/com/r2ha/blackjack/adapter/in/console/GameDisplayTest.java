package com.r2ha.blackjack.adapter.in.console;

import com.r2ha.blackjack.Blackjack;
import com.r2ha.blackjack.domain.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class GameDisplayTest {

    private final InputStream originalSystemIn = System.in;

    private void provideInput(String input) {
        byte[] inputBytes = input.getBytes();
        ByteArrayInputStream testIn = new ByteArrayInputStream(inputBytes);
        System.setIn(testIn); // reading from System.in will consume our input
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(originalSystemIn);
    }

    @Test
    void gamePlays() {
        provideInput("\nS\n");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        Game.directOutputTo(printStream);
        // Starts the game with an empty String array for the arguments.
        Blackjack.main(new String[0]);

        List<String> cleanedOutput = baos.toString()
                                         .replaceAll("\u001B\\[[\\d;]*[^\\d;]", "\n")
                                         .lines()
                                         .map(String::strip)
                                         .toList();
        assertThat(cleanedOutput)
                .contains(
                        "Welcome to",
                        "JitterTed's",
                        "Blackjack game",
                        "Hit [ENTER] to start...",
                        "Dealer has:",
                        "Player has:",
                        "[H]it or [S]tand?");

        // count card tops, 1 for each card displayed
        long cardTops = cleanedOutput.stream()
                                     .filter(s -> s.equals("┌─────────┐")).count();
        assertThat(cardTops)
                .describedAs("At least 8 cards should have been displayed, could be more depending on Dealer's turn")
                .isGreaterThanOrEqualTo(8);

        // count card bottoms, must match the number of card tops
        long cardBottoms = cleanedOutput.stream()
                                        .filter(s -> s.equals("└─────────┘")).count();
        assertThat(cardBottoms)
                .describedAs("Number of Card Tops must match number of Card Bottoms")
                .isEqualTo(cardTops);

        // count card "middles" (blank card outline)
        long cardMiddles = cleanedOutput.stream()
                                        .filter(s -> s.equals("│         │")
                                                // capture the back of a card, too!
                                                || s.equals("│░░░░░░░░░│"))
                                        .count();
        assertThat(cardMiddles)
                .describedAs("Number of Card Middles must match Cards * 2")
                .isEqualTo(cardTops * 2);

    }

}
