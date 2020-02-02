package Player;

import Game.FieldValue;
import Game.Game;

import java.util.*;

public class HardAI extends Player {

    public HardAI(FieldValue fieldValue){
        this.symbol = fieldValue;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void turn() {
        Map<Integer, List<FieldValue>> board = getFieldDeepCopy();

        Move bestMove = minimax(board, this.symbol);

        if (this.game.makeTurn(this.symbol, bestMove.getRow(), bestMove.getColumn())){
            System.out.println("Making move level \"hard\"");
        }
    }

    /**
     * Minimax algorithm keeps playing the game from any given state to an end (terminal state), resulting into win, loss or tie.
     * Each reached terminal state is evaluated (based on its state and number of steps) and the best value is chosen.
     */
    private Move minimax(Map<Integer, List<FieldValue>> field, FieldValue player) {

        FieldValue opponent = player.equals(FieldValue.O) ? FieldValue.X : FieldValue.O;
        List<List<Integer>> availableSpots = emptyIndexies(field);

        // checks for the terminal states such as win, lose, and tie and returning a value accordingly
        if (winning(field, this.symbol)){
            return new Move(10, 0, 0);
        } else if (winning(field, opponent)){
            return new Move(-10, 0, 0);
        } else if (availableSpots.isEmpty()){
            return new Move(0, 0, 0);
        }

        // all possible moves
        List<Move> moves = new ArrayList<>();

        for (List<Integer> availableSpot : availableSpots){
            // new possible move with row and column coordinates from current availableSpot
            Move move = new Move(0, availableSpot.get(0), availableSpot.get(1));

            field.get(move.getRow()).set(move.getColumn(), player);

            if (player == this.symbol){
                int moveScore = minimax(field, opponent).getScore();
                move.setScore(moveScore);
            } else {
                int moveScore = minimax(field, this.symbol).getScore();
                move.setScore(moveScore);
            }

            // reset the move
            field.get(move.getRow()).set(move.getColumn(), FieldValue.EMPTY);

            moves.add(move);
        }

        Move bestMove = null;

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        // else loop over the moves and choose the move with the lowest score
        if (player == this.symbol){
            bestMove = moves.stream().max(Comparator.comparing(Move::getScore)).orElseThrow(NullPointerException::new);
        } else {
            bestMove = moves.stream().min(Comparator.comparing(Move::getScore)).orElseThrow(NullPointerException::new);
        }

        return bestMove;
    }


    /**
     * returns the available/empty spots on the field
     * @param field
     * @return [[row, col], [row, col],...]
     */
    private List<List<Integer>> emptyIndexies(Map<Integer, List<FieldValue>> field){
        List<List<Integer>> indexies = new ArrayList<>();

        for (int r = field.size(); r > 0; r--){
            for (int c = 0; c < field.size(); c++){
                if (field.get(r).get(c).equals(FieldValue.EMPTY)){
                    indexies.add(new ArrayList<>(Arrays.asList(r, c)));
                }
            }
        }

        return indexies;
    }

    /**
     * search for a winning combinations on the given board. Return true if a winning combinations is found
     * @param field
     * @param symbol
     * @return
     */
    private boolean winning(Map<Integer, List<FieldValue>> field, FieldValue symbol){
        int fieldSize = field.size();

        // search each row for a winner
        for (int r = fieldSize; r > 0; r--){

            Set<FieldValue> fieldValues = new HashSet<>(field.get(r));

            if (fieldValues.size() == 1 && fieldValues.contains(symbol)){
                return true;
            }
        }

        // search each column for a winner
        for (int c = 0; c < fieldSize; c++){

            Set<FieldValue> columnValues = new HashSet<>();

            for (int r = fieldSize; r > 0; r--){
                columnValues.add(field.get(r).get(c));
            }

            if (columnValues.size() == 1 && columnValues.contains(symbol)){
                return true;
            }
        }

        // search diagonal for a winner
        int currentCol = 0;
        int currentRow = 1;
        Set<FieldValue> diagonalValues = new HashSet<>();

        for (int i = 0; i < fieldSize; i++){
            diagonalValues.add(field.get(currentRow).get(currentCol));
            currentCol++;
            currentRow++;
        }

        if (diagonalValues.size() == 1 && diagonalValues.contains(symbol)){
            return true;
        }

        currentCol = 0;
        currentRow = fieldSize;
        Set<FieldValue> antiDiagonalValues = new HashSet<>();

        for (int i = 0; i < fieldSize; i++){
            antiDiagonalValues.add(field.get(currentRow).get(currentCol));
            currentCol++;
            currentRow--;
        }

        if (antiDiagonalValues.size() == 1 && antiDiagonalValues.contains(symbol)){
            return true;
        }

        return false;
    }

    /**
     * Createa a deep copy of current gaming field
     * @return
     */
    private Map<Integer, List<FieldValue>> getFieldDeepCopy() {
        Map<Integer, List<FieldValue>> board = new HashMap<>();

        for (int i = this.game.getField().size(); i > 0; i--){
            List<FieldValue> row = new ArrayList<>();
            for (FieldValue fv : this.game.getFieldRow(i)){
                switch (fv){
                    case EMPTY:
                        row.add(FieldValue.EMPTY);
                        break;
                    case X:
                        row.add(FieldValue.X);
                        break;
                    case O:
                        row.add(FieldValue.O);
                        break;
                }
            }
            board.put(i, row);
        }
        return board;
    }

    private class Move{
        private int score;
        private int row;
        private int column;

        public Move(int score, Integer row, Integer col) {
            this.score = score;
            this.row = row;
            this.column = col;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
