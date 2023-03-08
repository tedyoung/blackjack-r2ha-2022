package com.r2ha.blackjack.domain;

import com.r2ha.blackjack.adapter.in.console.ConsoleHand;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    public static void main(String[] args) {
        displayWelcomeScreen();
        waitForEnterFromUser();

        playGame();

        resetScreen();
    }

    private static void println(Object toPrint) {
        System.out.println(toPrint);
    }

    private static void print(Object toPrint) {
        System.out.print(toPrint);
    }

    private void println() {
        System.out.println();
    }

    private static void resetScreen() {
        println(ansi().reset());
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    private static void waitForEnterFromUser() {
        println(ansi()
                        .cursor(3, 1)
                        .fgBrightBlack().a("Hit [ENTER] to start..."));

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        println(ansi()
                        .bgBright(Ansi.Color.WHITE)
                        .eraseScreen()
                        .cursor(1, 1)
                        .fgGreen().a("Welcome to")
                        .fgRed().a(" JitterTed's")
                        .fgBlack().a(" Blackjack game"));
    }

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    public void play() {
        playerTurn();

        dealerTurn();

        displayFinalGameState();

        determineOutcome();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    private void determineOutcome() {
        if (playerHand.isBusted()) {
            println("You Busted, so you lose.  💸");
        } else if (dealerHand.isBusted()) {
            println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (playerHand.beats(dealerHand)) {
            println("You beat the Dealer! 💵");
        } else if (playerHand.pushes(dealerHand)) {
            println("Push: Nobody wins, we'll call it even.");
        } else {
            println("You lost to the Dealer. 💸");
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    private void playerTurn() {
        // get Player's decision: hit until they stand, then they're done (or they go bust)

        while (!playerHand.isBusted()) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                playerHand.drawFrom(deck);
                if (playerHand.isBusted()) {
                    return;
                }
            } else {
                println("You need to [H]it or [S]tand");
            }
        }
    }

    private String inputFromPlayer() {
        println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        print(ansi().eraseScreen().cursor(1, 1));
        println("Dealer has: ");
        println(ConsoleHand.displayFaceUpCard(dealerHand));

        // second card is the hole card, which is hidden, or "face down"
        displayBackOfCard();

        println();
        println("Player has: ");
        println(ConsoleHand.cardsAsString(playerHand));
        println(" (" + playerHand.value() + ")");
    }

    private void displayBackOfCard() {
        print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

    private void displayFinalGameState() {
        print(ansi().eraseScreen().cursor(1, 1));
        println("Dealer has: ");
        println(ConsoleHand.cardsAsString(dealerHand));
        println(" (" + dealerHand.value() + ")");

        println();
        println("Player has: ");
        println(ConsoleHand.cardsAsString(playerHand));
        println(" (" + playerHand.value() + ")");
    }

}
