package com.r2ha.blackjack;

import com.r2ha.blackjack.adapter.in.console.ConsoleGame;
import com.r2ha.blackjack.domain.Game;

// this is the COMPOSITION ROOT
// also called the CONFIGURATOR or BOOTSTRAP ASSEMBLER
// it is TRANSIENT, once everything has been created and configured, its job is done
public class Blackjack {

    // Create, Assemble, and Configure objects
    public static void main(String[] args) {
        // Game is an Entity-like object (not a true Entity, yet)
        Game game = new Game();

        // NOTE: in general, Entities are not directly passed in to Adapters
        // we'll fix this when we introduce the Application Service Layer
        ConsoleGame consoleGame = new ConsoleGame(game);
        consoleGame.start();
    }

}
