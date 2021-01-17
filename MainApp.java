import java.util.*;

public class MainApp {

    public static void main(String[] args) {

        int maxDepth;

        System.out.println("Who will play first?");
        System.out.println("Press 1 for computer.");
        System.out.println("Press 2 for player.");

        Scanner sc = new Scanner(System.in);
        int answer = sc.nextInt();
        sc.nextLine();

        while (answer != 1 && answer != 2) {
            System.out.println("Wrong input.\n");
            System.out.println("Try again.\n");
            answer = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("Enter the depth for the algorithm:");
        maxDepth = sc.nextInt();
        sc.nextLine();

        int nextPlayer = Board.BLACK;
        int computer = answer == 1 ? Board.BLACK : Board.WHITE;

        Board board = new Board();
        board.print();

        GamePlayer computerPlayer = new GamePlayer(maxDepth, computer);
        while (!board.isTerminal()) {
            switch (nextPlayer) {
                case Board.WHITE:
                    System.out.println("White player plays. (O)");
                    break;
                case Board.BLACK:
                    System.out.println("Black player plays. (X)");
                    break;
            }
            if (!board.hasValidMoves(nextPlayer)) {
                nextPlayer = nextPlayer * -1;
                System.out.println("Turn skipped.There is no valid move.");
                continue;
            }
            if (nextPlayer == computer) {
                Move computerMove = computerPlayer.miniMax(board);
                if (computerMove.getRow() == -1 || computerMove.getCol() == -1) {
                    System.out.println("Invalid move.");
                    continue;
                }
                board.makeMove(computerMove.getRow(), computerMove.getCol(), nextPlayer);
            } else {
                while (true) {
                    System.out.println("Enter the row number: ");
                    int row_ans = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the column number: ");
                    int col_ans = sc.nextInt();
                    sc.nextLine();
                    if (!board.isValidMove(row_ans, col_ans, nextPlayer)) {
                        System.out.println("Invalid move.");
                    } else {
                        board.makeMove(row_ans, col_ans, nextPlayer);
                        break;
                    }
                }
            }
            nextPlayer = nextPlayer * -1;
            board.print();
        }
		
		if (board.Winner() == 1){
			System.out.println("Black wins!");
		}else if (board.Winner() == -1){
			System.out.println("White wins!");
		}else{
			System.out.println("Draw!");
		}
		sc.close();
    }

}
