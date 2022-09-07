package main;

import main.piece_types.pieces;

public class board {
    private final int rows;
    private final int cols;
    public pieces [][] board;

    public board(int x, int y) {
        rows = x;
        cols = y;
        board = new pieces[x][y];
        reset();
    }

    public void reset() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = pieces.Empty;
            }
        }
    }

    public int get_num_rows() {
        return rows;
    }

    public int get_num_cols() {
        return cols;
    }

    public void print_board() {
        for (pieces[] line : board) {
            for (pieces p : line) {
                System.out.print(p + " ");
            }
            System.out.println();
        }
    }
}
