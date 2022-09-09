package main;

import main.piece_types.pieces;

public class game {
    public board board;
    public player player1, player2;
    private boolean P1_turn;

    public game(board b, player p1, player p2) {
        board = b;
        player1 = p1;
        player2 = p2;
        P1_turn = true;
    }

    public boolean moveIsLeft() {
        for (pieces[] line : board.board) {
            for (pieces p : line) {
                if (p == pieces.E) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean get_P1_turn() {
        return P1_turn;
    }

    public void set_P1_turn(boolean p) {
        P1_turn = p;
    }

    public int find_score(int turns) {
        pieces type = pieces.E;
        int row = 0, col = 0;

        //Iterate through rows to see if there's victory
        for (row = 0; row < board.get_num_rows(); row++) {
            type = board.board[row][0];
            if (type == pieces.E) continue;
            for (col = 0; col < board.get_num_cols(); col++) {
                if(type != board.board[row][col]) break;
            }
            if (col == board.get_num_cols()) {
                if (type == player1.get_type()) return 10 - turns;
                else if (type == player2.get_type()) return turns - 10;
            } 
        }

        //Iterate through cols to see if there's victory
        for (col = 0; col < board.get_num_cols(); col++) {
            type = board.board[0][col];
            if (type == pieces.E) continue;
            for(row = 0;row < board.get_num_rows(); row++) {
                if(type != board.board[row][col]) break;
            }
            if(row == board.get_num_rows()) {
                if(type == player1.get_type()) return 10 - turns;
                else if(type == player2.get_type()) return turns - 10;
            }
        }

        //Iterate through diagonals to see if there's victory
        type = board.board[0][0];
        if (type != pieces.E) {
            for(row = 0; row < board.get_num_rows(); row++) {
                if(type != board.board[row][row]) break;
            }
            if(row == board.get_num_rows()) {
                if(type == player1.get_type()) return 10 - turns;
                else if(type == player2.get_type()) return turns - 10;
            }
        }
        row = 0;
        col = board.get_num_cols()-1;
        type = board.board[row][col];
        if (type != pieces.E) {
            while (row < board.get_num_rows()) {
                if (type != board.board[row][col]) break;
                row++;
                col--;
            }
            if(row == board.get_num_rows()) {
                if(type == player1.get_type()) return 10 - turns;
                else if(type == player2.get_type()) return turns - 10;
            }
        }
        return 0;
    }

    public int minimax(boolean isMax, int turns) {
        int score = find_score(turns+1);

        //If someone (minimizer or maximizer) has won, return their score
        if (score != 0) return score;

        //If it's a tie with no moves left, return 0
        if (!moveIsLeft()) return 0;

        //For maximizer's move
        if (isMax) {
            int best = Integer.MIN_VALUE;

            //Traverse through the board
            for (int i = 0; i < board.get_num_rows(); i++) {
                for (int j = 0; j< board.get_num_cols(); j++) {
                    //If cell is empty, make the move
                    if (board.board[i][j] == pieces.E) {
                        board.board[i][j] = player1.get_type();

                        //Call minimax recursively and choose the max score
                        best = Math.max(best, minimax(!isMax, turns+1));

                        //Reset
                        board.board[i][j] = pieces.E;
                    }
                }
            }
            return best;
        } 

        //For Minimizer's move
        else {
            int best = Integer.MAX_VALUE;

            //Traverse through the board
            for (int i = 0; i < board.get_num_rows(); i++) {
                for (int j = 0; j< board.get_num_cols(); j++) {
                    //If cell is empty, make the move
                    if (board.board[i][j] == pieces.E) {
                        board.board[i][j] = player2.get_type();

                        //Call minimax recursively and choose the max score
                        best = Math.min(best, minimax(!isMax, turns+1));

                        //Reset
                        board.board[i][j] = pieces.E;
                    }
                }
            }
            return best;
        }
    }

    public int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1, bestScore};

        // Go through all cells, evalutae minimax function on all empty cells.
        // Return the cell with the best score
        for (int i = 0; i < board.get_num_rows(); i++) {
            for (int j = 0; j < board.get_num_cols(); j++) {
                if (board.board[i][j] == pieces.E) {
                    board.board[i][j] = player1.get_type();

                    //Find the score of current move
                    int moveVal = minimax(false,0);
                    //Reset
                    board.board[i][j] = pieces.E;

                    // If the current move's score is bigger than the best score, modify the values
                    if (moveVal > bestScore) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestMove[2] = moveVal;
                        bestScore = moveVal;
                    }
                }
            }
        }
        System.out.println("Best move is " + bestMove[0] + "," +bestMove[1] + " with a score of " + bestScore);
        return bestMove;
    }
}