package Player;


import Game.FieldValue;
import Game.Game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    Scanner scanner;

    public HumanPlayer(FieldValue symbol){
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void turn() {
        boolean invalidInput = true;

        do {
            System.out.print("Enter the coordinates: ");
            String input = scanner.nextLine();
            if (input.matches("^[1-3] [1-3]$")){
                String[] coordinates = input.split(" ");

                if (this.game.makeTurn(this.symbol, Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]) - 1)){
                    invalidInput = false;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }

            } else if (input.matches("^exit$")){
                System.exit(0);
            } else {
                System.out.println("Coordinates should be from 1 to 3!");
            }

        } while (invalidInput);
    }
}
