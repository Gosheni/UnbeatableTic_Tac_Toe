package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.piece_types.pieces;

public class Tic_Tac_Toe {

    static pieces turn;
    static board board;

    static pieces checkWinner(game g) {
        //Stay in the game if it isn't over yet
        if (g.moveIsLeft()) {
            System.out.println ("Player's turn; enter a slot number to place X in:");
            return null;
        }

        //Check for a winner
		for (int a = 0; a < 8; a++) {
			switch (a) {
			case 0:
				if (board.board[0][0].equals(board.board[0][1]) && (board.board[0][0].equals(board.board[0][2]))) {
                    return board.board[0][0];
                }
				break;
			case 1:
                if (board.board[1][0].equals(board.board[1][1]) && (board.board[1][0].equals(board.board[1][2]))) {
                    return board.board[1][0];
                }
				break;
			case 2:
                if (board.board[2][0].equals(board.board[2][1]) && (board.board[2][0].equals(board.board[2][2]))) {
                    return board.board[2][0];
                }
				break;
			case 3:
                if (board.board[0][0].equals(board.board[1][0]) && (board.board[0][0].equals(board.board[2][0]))) {
                    return board.board[0][0];
                }
				break;
			case 4:
                if (board.board[1][0].equals(board.board[1][1]) && (board.board[1][0].equals(board.board[1][2]))) {
                    return board.board[1][0];
                }
				break;
			case 5:
                if (board.board[2][0].equals(board.board[2][1]) && (board.board[2][0].equals(board.board[2][2]))) {
                    return board.board[2][0];
                }
				break;
			case 6:
                if (board.board[2][0].equals(board.board[1][1]) && (board.board[2][0].equals(board.board[0][2]))) {
                    return board.board[2][0];
                }
				break;
			case 7:
                if (board.board[0][0].equals(board.board[1][1]) && (board.board[0][0].equals(board.board[2][2]))) {
                    return board.board[0][0];
                }
				break;
			}
		}
		return pieces.E;
	}
    public static void main(String[] args) {
        board = new board(3, 3);
        player p1 = new player("Player1", pieces.O);
        player p2 = new player("Player2", pieces.X);

        game game = new game(board, p1, p2);

        Scanner in = new Scanner(System.in);
		turn = pieces.X;
		pieces winner = null;

        System.out.println("Welcome to 3x3 Tic Tac Toe.");
		board.print_board();

		System.out.println("X will play first. Enter a slot number (1-9) to place X in:");

        while (winner == null) {
			int numInput;
			
		// Exception handling.
		// numInput will take input from user like from 1 to 9.
		// If it is not in range from 1 to 9.
		// then it will show you an error "Invalid input."
			try {
				numInput = in.nextInt();
				if (!(numInput > 0 && numInput <= 9)) {
					System.out.println("Invalid input; re-enter slot number:");
					continue;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input; re-enter slot number:");
				continue;
			}
			
			// This game has two player x and O.
			// Here is the logic to decide the turn.
			if (board.board[(numInput-1)/3][(numInput-1)%3].equals(pieces.E)) {
				board.board[(numInput-1)/3][(numInput-1)%3] = pieces.X;

				int[] bestMoves = game.findBestMove();

                if (bestMoves[2] == -1) {
                    System.out.println("The best result is a draw");
                } else {
                    board.board[bestMoves[0]][bestMoves[1]] = pieces.O;
                } 
				board.print_board();
				winner = checkWinner(game);
            } else {
				System.out.println("Slot already taken; re-enter slot number:");
			}
		}
		
		// Display result if the match is over
		if (winner.equals(pieces.E)) {
			System.out.println("It's a draw! Thanks for playing.");
		} else {
			System.out.println("Congratulations! " + winner + " have won! Thanks for playing.");
		}
    }
}