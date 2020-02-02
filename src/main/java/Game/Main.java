package Game;

import Player.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        Game game = null;

        do {

            System.out.print("Input command: ");
            String input = scanner.nextLine();

            if (input.matches("^start (user|easy|medium|hard) (user|easy|medium|hard)$")){

                validInput = true;
                String[] commands = input.split(" ");

                Player playerOne = null;
                Player playerTwo = null;

                // TODO factory
                switch (commands[1]){
                    case "user":
                        playerOne = new HumanPlayer(FieldValue.X);
                        break;
                    case "easy":
                        playerOne = new EasyAI(FieldValue.X);
                        break;
                    case "medium":
                        playerOne = new MediumAI(FieldValue.X);
                        break;
                    case "hard":
                        playerOne = new HardAI(FieldValue.X);
                }

                switch (commands[2]){
                    case "user":
                        playerTwo = new HumanPlayer(FieldValue.O);
                        break;
                    case "easy":
                        playerTwo = new EasyAI(FieldValue.O);
                        break;
                    case "medium":
                        playerTwo = new MediumAI(FieldValue.O);
                        break;
                    case "hard":
                        playerTwo = new HardAI(FieldValue.O);
                }

                game = new Game(playerOne, playerTwo);

            } else if (input.matches("^exit$")){
                System.exit(0);
            } else {
                System.out.println("Bad parameters!");
            }

        } while (!validInput);

        do {
            game.showField();
            game.evaluate();
        } while (!game.isGameOver());
    }
}
