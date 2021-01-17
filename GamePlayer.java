import java.util.*;

public class GamePlayer {

    private int maxDepth;
    private int playerColour;

    public GamePlayer(int maxDepth, int playerColour) {
        this.maxDepth = maxDepth;
        this.playerColour = playerColour;
    }

    public Move miniMax(Board board) {
        if (playerColour == Board.BLACK) {
            return max(new Board(board), 0);
        } else {
            return min(new Board(board), 0);//if the computer's colour is white then the best value is the lowest (from evaluate)
        }
    }

    private Random random = new Random();

    public Move max(Board board, int depth) {
        if (board.isTerminal() || depth == maxDepth) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(playerColour));
        Move maxMove = new Move(Integer.MIN_VALUE);

        for (Board child : children) {
            Move move = min(child, depth + 1);
            if (move.getValue() >= maxMove.getValue()) {
                if (move.getValue() == maxMove.getValue()) {
                    if (random.nextInt(2) == 0) {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }
        }

        return maxMove;
    }

    public Move min(Board board, int depth) {
        if (board.isTerminal() || depth == maxDepth) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }

        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(playerColour));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children) {
            Move move = max(child, depth + 1);
            if (move.getValue() <= minMove.getValue()) {
                if (move.getValue() == minMove.getValue()) {
                    if (random.nextInt(2) == 0) {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                } else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }
}
