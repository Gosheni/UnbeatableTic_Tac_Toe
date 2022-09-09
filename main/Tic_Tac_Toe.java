package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.piece_types.pieces;

public class Tic_Tac_Toe {

    static pieces checkWinner(board board) {

        //Check for a winner
		for (int a = 0; a < 8; a++) {
			switch (a) {
			case 0:
                pieces p1 = board.board[0][0];
                if (p1 == pieces.E) break;
				if (p1 == board.board[0][1] && p1 == board.board[0][2]) return p1;
				break;
			case 1:
                pieces p2 = board.board[1][0];
                if (p2 == pieces.E) break;
                if (p2 == board.board[1][1] && p2 == board.board[1][2]) return p2;
                break;
			case 2:
                pieces p3 = board.board[2][0];
                if (p3 == pieces.E) break;
                if (p3 == board.board[2][1] && p3 == board.board[2][2]) return p3;
                break;
			case 3:
                pieces p4 = board.board[0][0];
                if (p4 == pieces.E) break;
                if (p4 == board.board[1][0] && p4 == board.board[2][0]) return p4;
                break;
			case 4:
                pieces p5 = board.board[0][1];
                if (p5 == pieces.E) break;
                if (p5 == board.board[1][1] && p5 == board.board[2][1]) return p5;
                break;
			case 5:
                pieces p6 = board.board[0][2];
                if (p6 == pieces.E) break;
                if (p6 == board.board[1][2] && p6 == board.board[2][2]) return p6;
                break;
			case 6:
                pieces p7 = board.board[0][0];
                if (p7 == pieces.E) break;
                if (p7 == board.board[1][1] && p7 == board.board[2][2]) return p7;
                break;
			case 7:
                pieces p8 = board.board[2][0];
                if (p8 == pieces.E) break;
                if (p8 == board.board[1][1] && p8 == board.board[0][2]) return p8;
                break;
			}
		}

        //Stay in the game if it isn't over yet
        for (pieces[] line : board.board) {
            for (pieces p : line) {
                if (p == pieces.E) {
                    return null;
                }
            }
        }

		return pieces.E;
	}

    public static void main(String[] args) {
        board board = new board(3, 3);
        player p1 = new player("Player1", pieces.O);
        player p2 = new player("Player2", pieces.X);

        game game = new game(board, p1, p2);

        Scanner in = new Scanner(System.in);

		pieces winner = null;

        System.out.println("Welcome to 3x3 Tic Tac Toe.");
		board.print_board();


        while (winner == null) {
            System.out.println ("Player's turn; enter a slot number (1-9) to place X in:");
			int numInput;
			
		    // Exception handling for any invalid input (Outside of 1-9)
			try {
				numInput = in.nextInt();
				if (!(numInput > 0 && numInput <= 9)) {
					System.out.println("Invalid input; re-enter slot number:");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input; re-enter slot number:");
				continue;
			}
			
			// This game has two player x and O.
			// Here is the logic to decide the turn.
			if (board.board[(numInput-1)/3][(numInput-1)%3].equals(pieces.E)) {
				board.board[(numInput-1)/3][(numInput-1)%3] = pieces.X;

				int[] bestMoves = game.findBestMove();
                if (bestMoves == null) {
                    winner = pieces.E;
                    break;
                }

                board.board[bestMoves[0]][bestMoves[1]] = pieces.O;
				board.print_board();
				winner = checkWinner(board);
            } else {
				System.out.println("Slot already taken; re-enter slot number:");
			}
		}
		
		// Display result if the match is over
		if (winner == pieces.E) {
			System.out.println("It's a draw! Thanks for playing.");
		} else {
			System.out.println("Congratulations! " + winner + " have won! Thanks for playing.");
		}

        in.close();
    }
}