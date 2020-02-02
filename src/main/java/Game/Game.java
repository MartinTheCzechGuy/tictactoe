package Game;

import Player.Player;

import java.util.*;

public class Game {

    private Map<Integer, List<FieldValue>> field;

    private boolean gameOver;
    private int numOfO;
    private int numOfX;

    private Player playerOne;
    private Player playerTwo;

    public Game(Player playerOne, Player playerTwo){
        this.numOfO = 0;
        this.numOfX = 0;
        this.field = new HashMap<>();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        playerOne.setGame(this);
        playerTwo.setGame(this);

        // tvorim jednotlive radky hraciho pole
        for (int r = 3; r > 0; r--){
            // novy radek
            List<FieldValue> row = new ArrayList<>();
            for (int c = 0; c < 3; c++){
                row.add(FieldValue.EMPTY);
            }
            this.field.put(r, row);
        }
    }

    // GETTERS / SETTERS

    public boolean isGameOver() {
        return gameOver;
    }

    public List<FieldValue> getFieldRow(Integer row){
        return this.field.get(row);
    }

    public Map<Integer, List<FieldValue>> getField() {
        return field;
    }

    // Public methods

    /**
     * Tiskne pole do konzole
     */
    public void showField() {
        System.out.println("---------");

        for (int r = 3; r > 0; r--){
            StringBuilder row = new StringBuilder();
            row.append("| ");
            for (int c = 0; c < 3; c++){
                switch (this.field.get(r).get(c)){
                    case O:
                        row.append("O");
                        break;
                    case X:
                        row.append("X");
                        break;
                    case EMPTY:
                        row.append(" ");
                }
                row.append(" ");
            }
            row.append("|");

            System.out.println(row);
        }

        System.out.println("---------");
    }

    /**
     * Check field status to decide about next move
     * @return
     */
    public void evaluate() {

        int fieldSize = this.field.size();

        // search each row for a winner
        for (int r = fieldSize; r > 0; r--){

            Set<FieldValue> fieldValues = new HashSet<>(this.field.get(r));

            if (fieldValues.size() == 1 && !fieldValues.contains(FieldValue.EMPTY)){
                this.gameOver = true;
                System.out.println(fieldValues.toArray()[0] + " wins");
                return;
            }
        }

        // search each column for a winner
        for (int c = 0; c < fieldSize; c++){

            Set<FieldValue> columnValues = new HashSet<>();

            for (int r = fieldSize; r > 0; r--){
                columnValues.add(getFieldValue(r, c));
            }

            if (columnValues.size() == 1 && !columnValues.contains(FieldValue.EMPTY)){
                System.out.println(columnValues.toArray()[0] + " wins");
                this.gameOver = true;
                return;
            }
        }

        // search diagonal for a winner
        int currentCol = 0;
        int currentRow = 1;
        Set<FieldValue> diagonalValues = new HashSet<>();

        for (int i = 0; i < fieldSize; i++){
            diagonalValues.add(this.field.get(currentRow).get(currentCol));
            currentCol++;
            currentRow++;
        }

        if (diagonalValues.size() == 1 && !diagonalValues.contains(FieldValue.EMPTY)){
            this.gameOver = true;
            System.out.println(diagonalValues.toArray()[0] + " wins");
            return;
        }

        currentCol = 0;
        currentRow = fieldSize;
        Set<FieldValue> antiDiagonalValues = new HashSet<>();

        for (int i = 0; i < fieldSize; i++){
            antiDiagonalValues.add(this.field.get(currentRow).get(currentCol));
            currentCol++;
            currentRow--;
        }

        if (antiDiagonalValues.size() == 1 && !antiDiagonalValues.contains(FieldValue.EMPTY)){
            this.gameOver = true;
            System.out.println(antiDiagonalValues.toArray()[0] + " wins");
            return;
        }

        // if no winner was found, and all the field are taken -> Draw
        if (this.numOfX + this.numOfO == fieldSize*fieldSize){
            System.out.println("Draw");
            this.gameOver = true;
            return;
        }

        // Otherwise, determine whose turn is next (X starts -> equal num of X and O means its Xs turn)
        if (this.numOfO == this.numOfX){
            playerOne.turn();
        } else {
            playerTwo.turn();
        }
    }

    public boolean makeTurn(FieldValue player, int row, int column) {

        boolean validTurn = false;

        if (FieldValue.EMPTY.equals(this.field.get(row).get(column))){
            this.field.get(row).set(column, player);
            validTurn = true;

            switch (player){
                case X:
                    numOfX++;
                    break;
                case O:
                    numOfO++;
            }
        }

        return validTurn;
    }

    /**
     * Return coresponding FieldValue
     * @param row - row number, Map key
     * @param column - column index in the array
     * @return
     */
    private FieldValue getFieldValue(int row, int column){
        return this.field.get(row).get(column);
    }
}