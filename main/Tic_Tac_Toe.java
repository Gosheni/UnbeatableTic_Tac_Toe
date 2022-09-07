package main;

import main.piece_types.pieces;

public class Tic_Tac_Toe {
    public static void main(String[] args) {
        board board = new board(3, 3);
        player p1 = new player("Player1", pieces.O);
        player p2 = new player("Player2", pieces.X);
    }
}
