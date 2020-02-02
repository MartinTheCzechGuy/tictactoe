package Player;

import Game.FieldValue;
import Game.Game;

import java.util.*;

public class MediumAI extends Player {

    Random random = new Random();
    Game game;

    public MediumAI(FieldValue symbol){
        this.symbol = symbol;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * If it can win in one move (if it has two in a row), it places a third to get three in a row and win.
     * If the opponent can win in one move, it plays the third itself to block the opponent to win.
     * Otherwise, it makes a random move.
     */
    @Override
    public void turn() {

        System.out.println("Making move level \"medium\"");

        int fieldSize = this.game.getFieldRow(1).size();

        Integer row = null;
        Integer col = null;

        // If it can win in one move (if it has two in a row), it places a third to get three in a row and win.
        // search rows
        for (int r = fieldSize; r > 0; r--){

            row = r;
            col = searchRow(this.game.getFieldRow(r), this.symbol);

            if (col != null){
                this.game.makeTurn(this.symbol, row, col);
                return;
            }
        }
        // search columns
        for (int c = 0; c < fieldSize; c++){

            col = c;
            row = searchColumn(c, fieldSize, this.symbol);

            if (row != null){
                this.game.makeTurn(this.symbol, row, col);
                return;
            }
        }
        // search diagonal
        Map<String, Integer> coordinates = searchDiagonal(fieldSize, this.symbol);
        if (coordinates != null){
            row = coordinates.get("row");
            col = coordinates.get("col");
            this.game.makeTurn(this.symbol, row, col);
            return;
        }

        coordinates = searchAntiDiagonal(fieldSize, this.symbol);
        if (coordinates != null){
            row = coordinates.get("row");
            col = coordinates.get("col");
            this.game.makeTurn(this.symbol, row, col);
            return;
        }

        // If the opponent can win in one move, it plays the third itself to block the opponent to win.
        FieldValue opponentsSymbol = symbol.equals(FieldValue.X) ? FieldValue.O : FieldValue.X;

        // search rows
        for (int r = fieldSize; r > 0; r--){

            row = r;
            col = searchRow(this.game.getFieldRow(r), opponentsSymbol);

            if (col != null){
                this.game.makeTurn(this.symbol, row, col);
                return;
            }
        }
        // search columns
        for (int c = 0; c < fieldSize; c++){

            col = c;
            row = searchColumn(c, fieldSize, opponentsSymbol);

            if (row != null){
                this.game.makeTurn(this.symbol, row, col);
                return;
            }
        }
        // search diagonal
        coordinates = searchDiagonal(fieldSize, opponentsSymbol);
        if (coordinates != null){
            row = coordinates.get("row");
            col = coordinates.get("col");
            this.game.makeTurn(this.symbol, row, col);
            return;
        }

        coordinates = searchAntiDiagonal(fieldSize, opponentsSymbol);
        if (coordinates != null){
            row = coordinates.get("row");
            col = coordinates.get("col");
            this.game.makeTurn(this.symbol, row, col);
            return;
        }

        // Otherwise, it makes a random move.
        boolean didTurn = false;

        do {

            row = random.nextInt(3) + 1;
            col = random.nextInt(3);

            if (this.game.makeTurn(this.symbol, row, col)){
                didTurn = true;
            }

        } while (!didTurn);

    }

    private FieldValue getValue(int row, int col) {
        return this.game.getFieldRow(row).get(col - 1);
    }

    /**
     * Search each row for any posibility to win in next turn of searchedSymbol
     * -> if there are only searchedSymbol and one empty space in the row
     * @param row
     * @param searchedSymbol
     * @return
     */
    private Integer searchRow(List<FieldValue> row, FieldValue searchedSymbol){

        Integer emptyFieldIndex = null;
        FieldValue oponent = searchedSymbol == FieldValue.X ? FieldValue.O : FieldValue.X;

        for (int i = 0; i < row.size(); i++){
            FieldValue currentField = row.get(i);

            if (currentField == FieldValue.EMPTY){
                if (emptyFieldIndex != null){
                    emptyFieldIndex = null;
                    break;
                }
                emptyFieldIndex = i;
            }
            if (currentField == oponent){
                emptyFieldIndex = null;
                break;
            }
        }
        return emptyFieldIndex;
    }

    /**
     * Search column for any opportunity to win in next turn.
     * @param column
     * @param fieldSize
     * @param searchedSymbol
     * @return
     */
    private Integer searchColumn(int column, int fieldSize, FieldValue searchedSymbol) {

        Integer emptyFieldIndex = null;
        FieldValue oponent = searchedSymbol == FieldValue.X ? FieldValue.O : FieldValue.X;

        for (int r = fieldSize; r > 0; r--){
            FieldValue currentField = this.game.getFieldRow(r).get(column);

            // TOTO JE BAD -> z jine metody
            if (currentField == FieldValue.EMPTY){
                if (emptyFieldIndex != null){
                    emptyFieldIndex = null;
                    break;
                }
                emptyFieldIndex = r;
            }
            if (currentField == oponent){
                emptyFieldIndex = null;
                break;
            }
        }
        return emptyFieldIndex;
    }

    /**
     * Search column for any opportunity to win in next turn.
     * @param fieldSize
     * @param symbol
     * @return
     */
    private Map<String, Integer> searchDiagonal(int fieldSize, FieldValue symbol) {
        int currentCol = 0;
        int currentRow = 1;
        List<FieldValue> diagonalValues = new ArrayList<>();
        Map<String, Integer> coordinates = new HashMap<>();
        FieldValue oponent = symbol == FieldValue.X ? FieldValue.O : FieldValue.X;

        // collect all values on the diagonal
        for (int i = 0; i < fieldSize; i++){
            FieldValue currentField = this.game.getFieldRow(currentRow).get(currentCol);

            if (currentField == FieldValue.EMPTY){
                if (!coordinates.isEmpty()){
                    return null;
                }
                coordinates.put("row", currentRow);
                coordinates.put("col", currentCol);

            }
            if (currentField == oponent){
                return null;
            }

            currentCol++;
            currentRow++;
        }


        return coordinates;
    }

    private Map<String, Integer> searchAntiDiagonal(int fieldSize, FieldValue symbol) {
        int currentCol = 0;
        int currentRow = 3;
        List<FieldValue> diagonalValues = new ArrayList<>();
        Map<String, Integer> coordinates = new HashMap<>();
        FieldValue oponent = symbol == FieldValue.X ? FieldValue.O : FieldValue.X;

        // collect all values on the diagonal
        for (int i = 0; i < fieldSize; i++){

            FieldValue currentField = this.game.getFieldRow(currentRow).get(currentCol);

            if (currentField == FieldValue.EMPTY){
                if (!coordinates.isEmpty()){
                    return null;
                }
                coordinates.put("row", currentRow);
                coordinates.put("col", currentCol);

            }
            if (currentField == oponent){
                return null;
            }

            diagonalValues.add(this.game.getFieldRow(currentRow).get(currentCol));
            currentCol++;
            currentRow--;
        }

        return coordinates;
    }
}
