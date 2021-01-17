import java.util.*;

public class Board {

	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int EMPTY = 0;

	//Immediate move that lead to this board
	private Move lastMove;

	private int[][] gameBoard;

	public Board() //constructor of an empty board
	{
		lastMove = new Move();
		gameBoard = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				gameBoard[i][j] = EMPTY;
			}
		}
		gameBoard[3][3] = WHITE;
		gameBoard[3][4] = BLACK;
		gameBoard[4][4] = WHITE;
		gameBoard[4][3] = BLACK;
	}

	public Board(Board board) {
		lastMove = board.lastMove;
		gameBoard = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}

	public Move getLastMove() {
		return lastMove;
	}

	public void makeMove(int row, int col, int colour) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0) continue;
				replacePieces(row, col, colour, i, j);
			}
		}
		gameBoard[row][col] = colour;
		lastMove = new Move(row, col);
	}

	public boolean checkDirection(int row, int col, int colour, int drow, int dcol) {
		int steps = 0;
		while (true) {
			row += drow;
			col += dcol;
			if (row > 7 || row < 0 || col > 7 || col < 0 || gameBoard[row][col] == EMPTY) {
				return false;
			}
			if (gameBoard[row][col] == colour) {
				return steps > 0;
			}
			steps++;
		}
	}

	public void replacePieces(int row, int col, int colour, int drow, int dcol) {
		while (true) {
			row += drow;
			col += dcol;
			if (row > 7 || row < 0 || col > 7 || col < 0 || gameBoard[row][col] == EMPTY) {
				return;
			}
			if (gameBoard[row][col] == colour) {
				return;
			}
			gameBoard[row][col] = colour;
		}
	}


	public boolean isValidMove(int row, int col, int colour) {
		if ((row < 0) || (col < 0) || (row > 7) || (col > 7)) {
			return false;
		}
		if (gameBoard[row][col] != EMPTY) {
			return false;
		}
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0) continue;
				if (checkDirection(row, col, colour, i, j)) return true;
			}
		}
		return false;
	}

	public boolean hasValidMoves(int colour) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (isValidMove(row, col, colour)) {
					return true;
				}
			}
		}
		return false;
	}


	public ArrayList<Board> getChildren(int colour) {
		ArrayList<Board> children = new ArrayList<Board>();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (isValidMove(row, col, colour)) {
					Board child = new Board(this);
					child.makeMove(row, col, colour);
					children.add(child);
				}
			}
		}
		return children;
	}
	
	private boolean isCorner(int row, int col) {
        return row == 0 && col == 0 || row == 0 && col == 7 || row == 7 && col == 0 || row == 7 && col == 7;
    }

    private boolean isCloseToCorner(int row, int col) {
        return !isCorner(row, col) && (row <= 1 && col <= 1 || row <= 1 && col >= 6 || row >= 6 && col <= 1 || row >= 6 && col >= 6);
    }
    
    private boolean isEdge(int row, int col) {
        return !isCorner(row, col) && !isCloseToCorner(row, col) && (row == 0 || col == 0 || row == 7 || col == 7);
    }
	
	public int evaluate() {
        int sum = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] == EMPTY) continue;
                int value;
                if (isCorner(i , j)) {
                    value = 5;
                } else if (isCloseToCorner(i, j)) {
                    value = 1;
                } else if (isEdge(i, j)) {
                    value = 2;
                } else { //simple puck
                    value = 4;
                }
                sum = sum + value*gameBoard[i][j];
            }
        }

        return sum;
    }


	public boolean isTerminal() {
		return !hasValidMoves(BLACK) && !hasValidMoves(WHITE);
	}
	
	public int Winner(){
		int sum_white = 0;
		int sum_black = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard[i][j] == BLACK) sum_black = sum_black + 1;
				if (gameBoard[i][j] == WHITE) sum_white = sum_white + 1;
			}
		}
		if (sum_black > sum_white){
			return BLACK;
		}else if (sum_black < sum_white){
			return WHITE;
		}else{
			return EMPTY;
		}
	}

	public void print()
	{
		System.out.println("*********");

		System.out.println("  0 1 2 3 4 5 6 7");
		for(int row=0; row<8; row++)
		{
			System.out.print(row+ " ");

			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case BLACK:
						System.out.print("X ");
						break;
					case WHITE:
						System.out.print("O ");
						break;
					case EMPTY:
						System.out.print("- ");
						break;
					default:
						break;
				}
			}
			System.out.println("*");
		}
		System.out.println("*********");
	}
}