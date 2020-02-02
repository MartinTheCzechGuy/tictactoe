package Player;

import Game.FieldValue;
import Game.Game;

import java.util.Random;

public class EasyAI extends Player {

    Random random = new Random();
    Game game;

    public EasyAI(FieldValue symbol){
        this.symbol = symbol;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Makes a random move.
     */
    @Override
    public void turn() {

        System.out.println("Making move level \"easy\"");

        boolean didTurn = false;

        do {

            int row = random.nextInt(3) + 1;
            int column = random.nextInt(3);

            if (this.game.makeTurn(this.symbol, row, column)){
                didTurn = true;
            }

        } while (!didTurn);

    }
}
