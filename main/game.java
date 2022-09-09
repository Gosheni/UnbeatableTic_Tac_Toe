package main;

import main.piece_types.pieces;

public class game {
    public board board;
    private int row,col;
    public player player1, player2;

    public game(board b, player p1, player p2) {
        board = b;
        player1 = p1;
        player2 = p2;
    }

    public boolean moveIsLeft() {
        for (pieces[] line : board.board) {
            for (pieces p : line) {
                if (p == pieces.E) return true;
            }
        }
        return false;
    }

    public int evaluate_score(int depth) {
        
        //Traverse through rows to see if there's a win
        pieces type = pieces.E;
        for (row = 0; row < board.get_num_rows(); row++) {
            type = board.board[row][0];
            if (type == pieces.E) continue;

            for(col = 0; col < board.get_num_cols(); col++) {
                if(board.board[row][col] != type) break;
            }
            if(col == board.get_num_cols()) {
                if(type == player1.get_type()) 
                    return 10 - depth;
                else if(type == player2.get_type()) 
                    return depth - 10;
            }
        }

        //Traverse through cols to see if there's a win
        for (col = 0; col < board.get_num_cols(); col++) {
            type = board.board[0][col];
            if (type == pieces.E) continue;

            for (row = 0; row < board.get_num_rows(); row++) {
                if (board.board[row][col] != type) break;
            }
            if (row == board.get_num_rows()) {          
                if (type == player1.get_type()) 
                    return 10 - depth;
                else if(type == player2.get_type()) 
                    return depth - 10;
            }
        }

        //Traverse through diagonals to see if there's a win
        type = board.board[0][0];
        for (row = 0; row < board.get_num_rows(); row++){
            if(board.board[row][row] != type) break;
        }
        if (row == board.get_num_rows()) {
            if(type == player1.get_type())
                return 10 - depth;
            else if(type == player2.get_type())
                return depth - 10;
        }

        row = 0;
        col = board.get_num_cols()-1;
        type = board.board[row][col];
        while (col >= 0) {
            if(board.board[row][col] != type) break;
            row++;
            col--;
        }
        if (col < 0) {
            if (type == player1.get_type())
                return 10 - depth;
            else if (type == player2.get_type())
                return depth - 10;
        }

        //If none of them won yet, return 0
        return 0;     
    }
    
    public int minimax(boolean isMax, int depth) {
        
        int score = evaluate_score(depth+1);

        //If the maximizer or the minimizer won, return their analyzed score
        if (score != 0) return score;

        //If it's a tie with no moves left, return 0
        if (!moveIsLeft()) return 0;

        //For maximizer's move
        if (isMax) {
            int best = -1000;

            //Traverse all cells
            for (int i = 0; i < board.get_num_rows(); i++) {
                for (int j = 0; j< board.get_num_cols(); j++) {
                    //Check if cell is empty and make move if so
                    if (board.board[i][j] == pieces.E) {
                        board.board[i][j] = player1.get_type();

                        //Call minimax recursively and find the max value
                        best = Math.max(best , minimax(!isMax,depth+1));

                        //Reset
                        board.board[i][j] = pieces.E;
                    }
                }
            }
            return best;
        }

        //For minimizer's move
        else {
            int best = 1000;

            //Traverse all cells
            for (int i = 0; i < board.get_num_rows(); i++) {
                for (int j = 0; j < board.get_num_cols(); j++) {
                    //Check if cell is empty and make move if so
                    if (board.board[i][j] == pieces.E) {
                        board.board[i][j] = player2.get_type();

                        //Call minimax recursively and find the min value
                        best = Math.min(best , minimax(!isMax,depth+1));

                        //Reset
                        board.board[i][j] = pieces.E;
                    }
                }
            }
            return best;
        }
    }
    
    public  int[] findBestMove() {
        //If a move cannot be made, return null
        if (!moveIsLeft()) return null;
        
        int bestVal = -1000;
        int[] bestMove = new int[]{-1, -1, -1};

        // Traverse all cells and evaluate their scores using minimax function for all empty spots
        for (int i = 0; i < board.get_num_rows(); i++) {
            for (int j = 0; j < board.get_num_cols(); j++) {
                // Check if cell is empty and make move if so
                if (board.board[i][j] == pieces.E) {
                    board.board[i][j] = player1.get_type();

                    //Analayze the move using minimax
                    int moveVal = minimax(false,0);

                    //Reset
                    board.board[i][j] = pieces.E;

                    //If the score of current move is higher than the best value, update
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestMove[2] = moveVal;
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.println("AI's best possible move is " + bestMove[0] + "," +bestMove[1] + " with score " + bestVal);
        return bestMove;
    }
}